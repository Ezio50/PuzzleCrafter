package com.example.puzzlecrafter

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PuzzleDao {
    @Insert
    fun insert(progress: PuzzleProgress)

    @Query("SELECT * FROM puzzle_progress")
    fun getAll(): List<PuzzleProgress>

    @Query("SELECT * FROM puzzle_progress WHERE imageUri = :imageUri")
    fun getByImageUri(imageUri: String): List<PuzzleProgress>

    @Query("""
        SELECT imageUri, GROUP_CONCAT(difficulty) AS diffs
        FROM puzzle_progress
        WHERE completed = 1
        GROUP BY imageUri
    """)
    fun getImageStats(): List<ImageStats>
}