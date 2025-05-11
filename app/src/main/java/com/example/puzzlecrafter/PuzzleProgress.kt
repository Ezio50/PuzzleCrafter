package com.example.puzzlecrafter

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "puzzle_progress")
data class PuzzleProgress(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUri: String,
    val difficulty: Int,
    val completed: Boolean
)