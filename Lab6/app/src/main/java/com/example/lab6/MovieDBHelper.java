package com.example.lab6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDBHelper extends SQLiteOpenHelper {

    //Specify DB Name and it is static because of the super class in the constructor
    private static String databaseName = "movieDatabase";

    //Create Object from SQliteDB
    SQLiteDatabase movieDatabase;

    // Cursor factory null to use default cursor behavior in the SQLite database
    public MovieDBHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table movie(id integer primary key, name text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists movie");
        onCreate(db);
    }

    public void createNew(String name) {
        ContentValues row = new ContentValues();
        row.put("name", name);
        movieDatabase = getWritableDatabase();
        // NullColumnHack here means that if the ContentValues is empty, the database will insert a row with NULL values.
        movieDatabase.insert("movie", null, row);
        movieDatabase.close();
    }

    public Cursor fetchAll() {
        movieDatabase = getReadableDatabase();
        String[] rowDetails = {"name", "id"};
        Cursor cursor = movieDatabase.query("movie", rowDetails, null,
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        movieDatabase.close();
        return cursor;
    }

    public void updateOne(int movieId, String newMovieName) {
        movieDatabase = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("name", newMovieName);
        movieDatabase.update("movie", row, "id='" + movieId + "'", null);
        movieDatabase.close();
    }

    public void deleteOne(int movieId) {
        movieDatabase = getWritableDatabase();
        movieDatabase.delete("movie", "id='" + movieId + "'", null);
        movieDatabase.close();
    }
}
