package com.udacity.danilo.popularmovies.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.danilo.popularmovies.R;
import com.udacity.danilo.popularmovies.bs.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private final String LOG_TAG = MovieAdapter.class.getSimpleName();
    public MovieAdapter(Context context, int resource) {
        super(context, resource);
    }
    public MovieAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie, parent, false);
        }

        Movie movie = getItem(position);

        if(movie != null){
            ImageView imagePoster = (ImageView) view.findViewById(R.id.image_movie_poster);
            Picasso.with(getContext()).load(movie.getMoviePosterFullUrl()).into(imagePoster);
            Log.v(LOG_TAG, "Setting image " + movie.getMoviePosterFullUrl());
        }

        return view;
    }
}
