package com.udacity.danilo.popularmovies.bs;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by danil on 02/10/2016.
 */

public class Movie {
    private int mID;
    private String mTitle;
    private String mMoviePoster;
    private String mPlot;
    private double mVoteAverage;
    private Date mReleaseDate;

    private final String mBaseUrl = "https://image.tmdb.org/t/p/w500";

    public void setMoviePoster(String moviePoster){
        mMoviePoster = moviePoster;
    }
    public String getmMoviePoster(){
        return mMoviePoster;
    }
    public String getMoviePosterFullUrl(){
        return mBaseUrl + "/" + mMoviePoster;
    }
    public static List<Movie> getTopRatedMovies(){

    }
}

class FetchMovieTask extends AsyncTask<Void, Void, Void>{
    private String mApiKey;
    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    public FetchMovieTask(String apiKey){
        mApiKey = apiKey;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("toprated")
                .appendQueryParameter("api_key", mApiKey);

        Log.v(LOG_TAG, builder.build().toString());

        try {
            URL url = new URL(builder.build().toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            if(inputStream == null)
                return null;

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer rawData = new StringBuffer();
            String line;
            while((line = bufferedReader.readLine()) != null)
                rawData.append(line);

            return null;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}