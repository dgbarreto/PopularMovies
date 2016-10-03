package com.udacity.danilo.popularmovies.bs;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private final String OWS_TITLE = "original_title";
    private final String OWS_MOVIE_POSTER = "poster_path";

    public Movie(JSONObject data) throws JSONException {
        mTitle = data.getString(OWS_TITLE);
        mMoviePoster = data.getString(OWS_MOVIE_POSTER);
    }

    public void setMoviePoster(String moviePoster){
        mMoviePoster = moviePoster;
    }
    public String getmMoviePoster(){
        return mMoviePoster;
    }
    public String getMoviePosterFullUrl(){
        return mBaseUrl + "/" + mMoviePoster;
    }
}