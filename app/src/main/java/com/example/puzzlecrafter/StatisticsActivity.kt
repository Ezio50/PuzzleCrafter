package com.example.puzzlecrafter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatisticsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var dao: PuzzleDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        recyclerView = findViewById(R.id.rvStats)
        recyclerView.layoutManager = LinearLayoutManager(this)

        dao = AppDatabase.getDatabase(this).puzzleDao()

        // Загружаем статистику
        CoroutineScope(Dispatchers.Main).launch {
            val stats = withContext(Dispatchers.IO) { dao.getImageStats() }
            recyclerView.adapter = StatsAdapter(stats) { imageUri, diffs ->
                // При клике на изображение переходим в ImageInfoActivity
                startActivity(Intent(this@StatisticsActivity, ImageInfoActivity::class.java).apply {
                    putExtra("imageUri", imageUri)
                })
            }
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}

class StatsAdapter(
    private val items: List<ImageStats>,
    private val onClick: (String, List<Int>) -> Unit
) : RecyclerView.Adapter<StatsAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val img = view.findViewById<ImageView>(R.id.imgStat)
        val levels = view.findViewById<LinearLayout>(R.id.levelsContainer)

        fun bind(stat: ImageStats) {
            img.load(stat.imageUri)
            levels.removeAllViews()
            // Обрабатываем случай, если diffs пустая или содержит некорректные данные
            val difficulties = try {
                stat.diffs.split(",").mapNotNull { it.toIntOrNull() }
            } catch (e: Exception) {
                emptyList<Int>()
            }
            difficulties.forEach { lvl ->
                val btn = Button(itemView.context).apply {
                    text = "${lvl}×${lvl}"
                    setOnClickListener {
                        // Запускаем GameActivity через контекст
                        val intent = Intent(itemView.context, GameActivity::class.java).apply {
                            putExtra("imageUri", stat.imageUri)
                            putExtra("difficulty", lvl)
                        }
                        itemView.context.startActivity(intent)
                    }
                }
                levels.addView(btn)
            }
            // Клик по всему элементу открывает ImageInfoActivity
            itemView.setOnClickListener {
                onClick(stat.imageUri, difficulties)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stat, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}