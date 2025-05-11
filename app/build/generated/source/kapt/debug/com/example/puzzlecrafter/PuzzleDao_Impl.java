package com.example.puzzlecrafter;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PuzzleDao_Impl implements PuzzleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PuzzleProgress> __insertionAdapterOfPuzzleProgress;

  public PuzzleDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPuzzleProgress = new EntityInsertionAdapter<PuzzleProgress>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `puzzle_progress` (`id`,`imageUri`,`difficulty`,`completed`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PuzzleProgress value) {
        stmt.bindLong(1, value.getId());
        if (value.getImageUri() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getImageUri());
        }
        stmt.bindLong(3, value.getDifficulty());
        final int _tmp = value.getCompleted() ? 1 : 0;
        stmt.bindLong(4, _tmp);
      }
    };
  }

  @Override
  public void insert(final PuzzleProgress progress) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPuzzleProgress.insert(progress);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<PuzzleProgress> getAll() {
    final String _sql = "SELECT * FROM puzzle_progress";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "completed");
      final List<PuzzleProgress> _result = new ArrayList<PuzzleProgress>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PuzzleProgress _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpImageUri;
        if (_cursor.isNull(_cursorIndexOfImageUri)) {
          _tmpImageUri = null;
        } else {
          _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
        }
        final int _tmpDifficulty;
        _tmpDifficulty = _cursor.getInt(_cursorIndexOfDifficulty);
        final boolean _tmpCompleted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCompleted);
        _tmpCompleted = _tmp != 0;
        _item = new PuzzleProgress(_tmpId,_tmpImageUri,_tmpDifficulty,_tmpCompleted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<PuzzleProgress> getByImageUri(final String imageUri) {
    final String _sql = "SELECT * FROM puzzle_progress WHERE imageUri = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (imageUri == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, imageUri);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfImageUri = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUri");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "completed");
      final List<PuzzleProgress> _result = new ArrayList<PuzzleProgress>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PuzzleProgress _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpImageUri;
        if (_cursor.isNull(_cursorIndexOfImageUri)) {
          _tmpImageUri = null;
        } else {
          _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
        }
        final int _tmpDifficulty;
        _tmpDifficulty = _cursor.getInt(_cursorIndexOfDifficulty);
        final boolean _tmpCompleted;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCompleted);
        _tmpCompleted = _tmp != 0;
        _item = new PuzzleProgress(_tmpId,_tmpImageUri,_tmpDifficulty,_tmpCompleted);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ImageStats> getImageStats() {
    final String _sql = "\n"
            + "        SELECT imageUri, GROUP_CONCAT(difficulty) AS diffs\n"
            + "        FROM puzzle_progress\n"
            + "        WHERE completed = 1\n"
            + "        GROUP BY imageUri\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfImageUri = 0;
      final int _cursorIndexOfDiffs = 1;
      final List<ImageStats> _result = new ArrayList<ImageStats>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ImageStats _item;
        final String _tmpImageUri;
        if (_cursor.isNull(_cursorIndexOfImageUri)) {
          _tmpImageUri = null;
        } else {
          _tmpImageUri = _cursor.getString(_cursorIndexOfImageUri);
        }
        final String _tmpDiffs;
        if (_cursor.isNull(_cursorIndexOfDiffs)) {
          _tmpDiffs = null;
        } else {
          _tmpDiffs = _cursor.getString(_cursorIndexOfDiffs);
        }
        _item = new ImageStats(_tmpImageUri,_tmpDiffs);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
