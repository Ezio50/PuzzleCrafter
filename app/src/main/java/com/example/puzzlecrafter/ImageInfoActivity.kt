package com.example.puzzlecrafter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import coil.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_info)

        val imageUri = intent.getStringExtra("imageUri") ?: return
        val db = AppDatabase.getDatabase(this)
        val dao = db.puzzleDao()

        val imgPreview = findViewById<ImageView>(R.id.imgPreview)
        imgPreview.load(imageUri)

        // Клик по изображению открывает DifficultyActivity
        imgPreview.setOnClickListener {
            startActivity(Intent(this, DifficultyActivity::class.java).apply {
                putExtra("imageUri", imageUri)
            })
        }

        val container = findViewById<LinearLayout>(R.id.levelsContainer)

        // Загружаем список сложностей из базы
        CoroutineScope(Dispatchers.Main).launch {
            val stats = withContext(Dispatchers.IO) {
                dao.getByImageUri(imageUri).filter { it.completed }.map { it.difficulty }.distinct()
            }
            stats.forEach { level ->
                val btn = Button(this@ImageInfoActivity).apply {
                    text = "${level}×${level}"
                    setOnClickListener {
                        startActivity(Intent(this@ImageInfoActivity, GameActivity::class.java).apply {
                            putExtra("imageUri", imageUri)
                            putExtra("difficulty", level)
                        })
                    }
                }
                container.addView(btn)
            }
        }
    }
}