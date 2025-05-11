package com.example.puzzlecrafter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\b\u0010\u0012\u001a\u00020\u000eH\u0002J\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0007H\u0002J\b\u0010\u0015\u001a\u00020\u000eH\u0002J\u001e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/example/puzzlecrafter/GameActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "difficulty", "", "pieces", "", "Landroid/graphics/Bitmap;", "puzzleGrid", "Landroid/widget/GridLayout;", "puzzlePiecesContainer", "Landroid/widget/LinearLayout;", "shuffledIndices", "checkCompletion", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "saveProgress", "setupPuzzleGrid", "bitmap", "setupPuzzlePieces", "splitImageIntoPuzzlePieces", "PuzzleDragListener", "app_debug"})
public final class GameActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.GridLayout puzzleGrid;
    private android.widget.LinearLayout puzzlePiecesContainer;
    private int difficulty = 0;
    private java.util.List<android.graphics.Bitmap> pieces;
    private java.util.List<java.lang.Integer> shuffledIndices;
    
    public GameActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * Делит bitmap на difficulty × difficulty прямых фрагментов
     */
    private final java.util.List<android.graphics.Bitmap> splitImageIntoPuzzlePieces(android.graphics.Bitmap bitmap, int difficulty) {
        return null;
    }
    
    /**
     * Создаёт ровную сетку ячеек, в которых будут пазлы
     */
    private final void setupPuzzleGrid(android.graphics.Bitmap bitmap) {
    }
    
    /**
     * Раскладывает перемешанные кусочки внизу экрана
     */
    private final void setupPuzzlePieces() {
    }
    
    /**
     * Проверяем, всё ли заполнено
     */
    private final void checkCompletion() {
    }
    
    private final void saveProgress() {
    }
    
    /**
     * Слушатель перетаскивания: сравниваем теги и индексы ячеек
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2 = {"Lcom/example/puzzlecrafter/GameActivity$PuzzleDragListener;", "Landroid/view/View$OnDragListener;", "(Lcom/example/puzzlecrafter/GameActivity;)V", "onDrag", "", "v", "Landroid/view/View;", "event", "Landroid/view/DragEvent;", "app_debug"})
    public final class PuzzleDragListener implements android.view.View.OnDragListener {
        
        public PuzzleDragListener() {
            super();
        }
        
        @java.lang.Override()
        public boolean onDrag(@org.jetbrains.annotations.NotNull()
        android.view.View v, @org.jetbrains.annotations.NotNull()
        android.view.DragEvent event) {
            return false;
        }
    }
}