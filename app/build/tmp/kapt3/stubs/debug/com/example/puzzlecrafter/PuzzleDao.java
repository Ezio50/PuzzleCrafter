package com.example.puzzlecrafter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\'J\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003H\'J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\'\u00a8\u0006\r"}, d2 = {"Lcom/example/puzzlecrafter/PuzzleDao;", "", "getAll", "", "Lcom/example/puzzlecrafter/PuzzleProgress;", "getByImageUri", "imageUri", "", "getImageStats", "Lcom/example/puzzlecrafter/ImageStats;", "insert", "", "progress", "app_debug"})
@androidx.room.Dao()
public abstract interface PuzzleDao {
    
    @androidx.room.Insert()
    public abstract void insert(@org.jetbrains.annotations.NotNull()
    com.example.puzzlecrafter.PuzzleProgress progress);
    
    @androidx.room.Query(value = "SELECT * FROM puzzle_progress")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.example.puzzlecrafter.PuzzleProgress> getAll();
    
    @androidx.room.Query(value = "SELECT * FROM puzzle_progress WHERE imageUri = :imageUri")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.example.puzzlecrafter.PuzzleProgress> getByImageUri(@org.jetbrains.annotations.NotNull()
    java.lang.String imageUri);
    
    @androidx.room.Query(value = "\n        SELECT imageUri, GROUP_CONCAT(difficulty) AS diffs\n        FROM puzzle_progress\n        WHERE completed = 1\n        GROUP BY imageUri\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<com.example.puzzlecrafter.ImageStats> getImageStats();
}