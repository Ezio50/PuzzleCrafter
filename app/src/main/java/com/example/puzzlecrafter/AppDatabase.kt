package com.example.puzzlecrafter

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [PuzzleProgress::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun puzzleDao(): PuzzleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "puzzle_db")
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { INSTANCE = it }
            }
        }

        // Миграция с версии 1 на версию 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Cтруктура таблицы puzzle_progress не изменилась,
                // миграция может быть пустой, но Room всё равно требует её
                // для проверки целостности
            }
        }
    }
}