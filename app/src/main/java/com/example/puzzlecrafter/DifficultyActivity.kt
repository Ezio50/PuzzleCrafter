package com.example.puzzlecrafter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DifficultyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)

        val imageUri = intent.getStringExtra("imageUri") ?: ""
        var selectedDifficulty = 0

        val db = AppDatabase.getDatabase(this)
        val dao = db.puzzleDao()

        CoroutineScope(Dispatchers.Main).launch {
            val completedPuzzles = withContext(Dispatchers.IO) {
                dao.getByImageUri(imageUri).map { it.difficulty }.toSet()
            }

            val btnStart = findViewById<Button>(R.id.btnStart)
            val difficultyButtons = mapOf(
                R.id.btn3x3 to 3,
                R.id.btn4x4 to 4,
                R.id.btn5x5 to 5,
                R.id.btn6x6 to 6,
                R.id.btn8x8 to 8,
                R.id.btn10x10 to 10,
                R.id.btn12x12 to 12,
                R.id.btn16x16 to 16
            )

            difficultyButtons.forEach { (id, difficulty) ->
                val button = findViewById<Button>(id)
                if (difficulty in completedPuzzles) {
                    button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0)
                }
                button.setOnClickListener {
                    selectedDifficulty = difficulty
                    btnStart.isEnabled = true
                }
            }

            btnStart.setOnClickListener {
                val intent = Intent(this@DifficultyActivity, GameActivity::class.java)
                intent.putExtra("imageUri", imageUri)
                intent.putExtra("difficulty", selectedDifficulty)
                startActivity(intent)
            }
        }
    }
}
