package com.udacity.danilo.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

public class MovieProvider extends ContentProvider {
    private MovieDBHelper mMovieHelper;
    static final int MOVIES = 100;
    private static final UriMatcher mUriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        mMovieHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursorOutput;

        switch (mUriMatcher.match(uri)){
            case MOVIES:
                cursorOutput = mMovieHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursorOutput.setNotificationUri(getContext().getContentResolver(), uri);
        return cursorOutput;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (mUriMatcher.match(uri)){
            case MOVIES:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mMovieHelper.getWritableDatabase();
        Uri uriOutput;

        switch (mUriMatcher.match(uri)){
            case MOVIES:
                long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
                if(_id > 0)
                    uriOutput = MovieContract.MovieEntry.buildMovieUri(_id);
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return uriOutput;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db= mMovieHelper.getWritableDatabase();
        int rowsDeleted;

        if(selection == null)
            selection = "1";

        switch (mUriMatcher.match(uri)){
            case MOVIES:
                rowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsDeleted > 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mMovieHelper.getWritableDatabase();
        int rowsUpdated;

        switch (mUriMatcher.match(uri)){
            case MOVIES:
                rowsUpdated = db.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rowsUpdated > 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIES, MOVIES);
        return matcher;
    }
}
