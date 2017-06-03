package com.udacity.danilo.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "movies.db";

    public MovieDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + "(" +
                MovieContract.MovieEntry.COLUMN_ID + " INTEGER PRIMARY KEY," +
                MovieContract.MovieEntry.COLUMN_MOVIE_POSTER + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL," +
                MovieContract.MovieEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
