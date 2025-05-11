package com.example.puzzlecrafter

import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.DragEvent
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import coil.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Log
import android.widget.FrameLayout

class GameActivity : AppCompatActivity() {
    private lateinit var puzzleGrid: GridLayout
    private lateinit var puzzlePiecesContainer: LinearLayout
    private var difficulty: Int = 0
    private lateinit var pieces: List<Bitmap>
    private lateinit var shuffledIndices: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        puzzleGrid = findViewById(R.id.puzzleGrid)
        puzzlePiecesContainer = findViewById(R.id.puzzlePieces)

        val imageUri = Uri.parse(intent.getStringExtra("imageUri"))
        difficulty = intent.getIntExtra("difficulty", 4)

        // 1) Загружаем и при необходимости масштабируем изображение
        var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        val maxSize = 1600
        val scale = maxSize.toFloat() / maxOf(bitmap.width, bitmap.height)
        if (scale < 1f) {
            bitmap = Bitmap.createScaledBitmap(bitmap,
                (bitmap.width * scale).toInt(),
                (bitmap.height * scale).toInt(), false)
        }

        // 2) Разбиваем изображение на равные прямоугольники
        pieces = splitImageIntoPuzzlePieces(bitmap, difficulty)
        shuffledIndices = pieces.indices.shuffled()

        // 3) Настраиваем GridLayout и область с кусочками
        setupPuzzleGrid(bitmap)
        setupPuzzlePieces()
    }

    /** Делит bitmap на difficulty × difficulty прямых фрагментов */
    private fun splitImageIntoPuzzlePieces(bitmap: Bitmap, difficulty: Int): List<Bitmap> {
        val totalW = bitmap.width
        val totalH = bitmap.height
        val baseW = totalW / difficulty
        val baseH = totalH / difficulty

        return mutableListOf<Bitmap>().apply {
            for (row in 0 until difficulty) {
                for (col in 0 until difficulty) {
                    val x = col * baseW
                    val y = row * baseH
                    val w = if (col == difficulty - 1) totalW - x else baseW
                    val h = if (row == difficulty - 1) totalH - y else baseH

                    add(Bitmap.createBitmap(bitmap, x, y, w, h))
                }
            }
        }
    }

    /** Создаёт ровную сетку ячеек, в которых будут пазлы */
    private fun setupPuzzleGrid(bitmap: Bitmap) {
        puzzleGrid.removeAllViews()
        puzzleGrid.rowCount = difficulty
        puzzleGrid.columnCount = difficulty

        puzzleGrid.post {
            // Получаем размеры родительского FrameLayout
            val parentWidth = (puzzleGrid.parent as FrameLayout).width
            val parentHeight = (puzzleGrid.parent as FrameLayout).height

            // Рассчитываем размер сетки как процент от меньшего размера родителя
            val gridSize = minOf(parentWidth, parentHeight) * 0.95 // Используем 95% от меньшего измерения
            val cellSize = (gridSize / difficulty).toInt()

            // Обновляем параметры расположения GridLayout для центрирования и установки размера
            puzzleGrid.layoutParams = FrameLayout.LayoutParams(gridSize.toInt(), gridSize.toInt()).apply {
                gravity = android.view.Gravity.CENTER
            }

            for (i in 0 until difficulty * difficulty) {
                val row = i / difficulty
                val col = i % difficulty

                val cell = ImageView(this).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = cellSize
                        height = cellSize
                        rowSpec = GridLayout.spec(row)
                        columnSpec = GridLayout.spec(col)
                        setMargins(0, 0, 0, 0)
                    }
                    setBackgroundResource(R.drawable.grid_cell_background)
                    scaleType = ImageView.ScaleType.FIT_XY
                    setOnDragListener(PuzzleDragListener())
                }
                puzzleGrid.addView(cell)
            }
        }
    }

    /** Раскладывает перемешанные кусочки внизу экрана */
    private fun setupPuzzlePieces() {
        puzzlePiecesContainer.removeAllViews()

        // Определяем минимальный размер кусочка (в пикселях)
        val minPieceSize = (resources.displayMetrics.density * 100).toInt() // 100dp в пикселях
        val screenWidth = resources.displayMetrics.widthPixels
        val pieceSize = maxOf(minPieceSize, screenWidth / (difficulty + 2)) // Учитываем difficulty, но не меньше minPieceSize

        shuffledIndices.forEach { originalIndex ->
            val piece = pieces[originalIndex]

            // Масштабируем кусочек, если он слишком маленький
            val scaledPiece = if (piece.width < pieceSize || piece.height < pieceSize) {
                val scale = maxOf(pieceSize.toFloat() / piece.width, pieceSize.toFloat() / piece.height)
                Bitmap.createScaledBitmap(piece, (piece.width * scale).toInt(), (piece.height * scale).toInt(), false)
            } else {
                piece
            }

            val iv = ImageView(this).apply {
                load(scaledPiece)
                tag = originalIndex
                scaleType = ImageView.ScaleType.CENTER_INSIDE // Сохраняем пропорции
                layoutParams = LinearLayout.LayoutParams(pieceSize, pieceSize).apply {
                    setMargins(4, 4, 4, 4)
                }
                setOnLongClickListener { v ->
                    val clip = ClipData.newPlainText("index", originalIndex.toString())
                    v.startDragAndDrop(clip, View.DragShadowBuilder(v), v, 0)
                    true
                }
            }
            puzzlePiecesContainer.addView(iv)
        }
    }

    /** Слушатель перетаскивания: сравниваем теги и индексы ячеек */
    inner class PuzzleDragListener : View.OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    val dragged = event.localState as ImageView
                    val target = v as ImageView
                    val fromIdx = dragged.tag as Int

                    val toIdx = puzzleGrid.indexOfChild(target)
                    if (fromIdx == toIdx) {
                        target.setImageBitmap(pieces[fromIdx])
                        dragged.visibility = View.GONE
                        checkCompletion()
                    }
                }
            }
            return true
        }
    }

    /** Проверяем, всё ли заполнено */
    private fun checkCompletion() {
        val done = (0 until puzzleGrid.childCount).all { idx ->
            (puzzleGrid.getChildAt(idx) as ImageView).drawable != null
        }
        if (done) {
            saveProgress()
            // Переходим на экран информации об изображении
            val imageUri = intent.getStringExtra("imageUri") ?: return
            startActivity(Intent(this, ImageInfoActivity::class.java).apply {
                putExtra("imageUri", imageUri)
            })
            finish()
        }
    }

    private fun saveProgress() {
        val db = AppDatabase.getDatabase(this)
        val dao = db.puzzleDao()
        val uri = intent.getStringExtra("imageUri") ?: ""
        val progress = PuzzleProgress(imageUri = uri, difficulty = difficulty, completed = true)

        // Проверяем, нет ли уже такой записи
        CoroutineScope(Dispatchers.IO).launch {
            val existing = dao.getByImageUri(uri).find { it.difficulty == difficulty && it.completed }
            if (existing == null) {
                dao.insert(progress)
            }
        }
    }
}