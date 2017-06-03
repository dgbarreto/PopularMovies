package com.udacity.danilo.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.danilo.popularmovies.R;
import com.udacity.danilo.popularmovies.data.MovieContract;

public class FavoriteMovieAdapter extends CursorAdapter {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private final Context mContext;

    public FavoriteMovieAdapter(Context context, Cursor c, int flags){
        super(context, c, flags);
        mContext = context;
    }

    public static class ViewHolder{
        public final ImageView imageView;

        public ViewHolder(View view){
            imageView = (ImageView) view.findViewById(R.id.image_movie_poster);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_movie, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        int columnPoster = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER);
        final String posterUrl = cursor.getString(columnPoster);
        Picasso.with(mContext).load(posterUrl).into(viewHolder.imageView);
    }
}
