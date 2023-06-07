package com.bilan.postsApp.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bilan.postsApp.DaybooksItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "note.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPostsTableQuery = "CREATE TABLE Note (id INTEGER PRIMARY KEY, title TEXT, description TEXT)";
        db.execSQL(createPostsTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropNoteTableQuery = "DROP TABLE IF EXISTS Note";
        db.execSQL(dropNoteTableQuery);
        onCreate(db);
    }

    public List<DaybooksItem> loadAllNote() {
        List<DaybooksItem> daybook = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT Note.id, Note.title, Note.description " +
                "FROM Note ";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                DaybooksItem item = new DaybooksItem(id, title, description);
                daybook.add(item);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return daybook;
    }

    public void deleteArticleById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete("Note", selection, selectionArgs);
    }

    public void addNote(String title, String description) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", description);
        db.insert("Note", null, values);
    }

    public List<DaybooksItem> getNoteByTitle(String searchTitle) {
        List<DaybooksItem> daybookList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query =
                "SELECT Note.id, Note.title, Posts.description" +
                "FROM Posts " +
                "WHERE Note.title LIKE ?";
        String[] selectionArgs = {"%" + searchTitle + "%"};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                DaybooksItem daybooksItem = new DaybooksItem(id, title, description);
                daybookList.add(daybooksItem);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return daybookList;
    }
}