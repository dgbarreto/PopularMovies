package com.udacity.danilo.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.danilo.popularmovies.R;
import com.udacity.danilo.popularmovies.bs.Movie;

import java.util.List;

/**
 * Created by Danilo on 02/10/2016.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    public MovieAdapter(Context context, int resource) {
        super(context, resource);
    }
    public MovieAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie, null);
        }

        Movie movie = getItem(position);

        if(movie != null){
            ImageView imagePoster = (ImageView) view.findViewById(R.id.image_movie_poster);
            Picasso.with(getContext()).load(movie.getMoviePosterFullUrl()).into(imagePoster);
        }

        return view;
    }
}
