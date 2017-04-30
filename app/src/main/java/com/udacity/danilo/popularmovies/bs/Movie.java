package com.udacity.danilo.popularmovies.bs;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PrivateKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Movie implements Parcelable {
    private int mID;
    private String mTitle;
    private String mMoviePoster;
    private String mPlot;
    private double mVoteAverage;
    private Date mReleaseDate;
    private boolean mHasTrailer;

    private final String mBaseUrl = "https://image.tmdb.org/t/p/w500";
    private final String OWS_TITLE = "original_title";
    private final String OWS_MOVIE_POSTER = "poster_path";
    private final String OWS_SYNOPSIS = "overview";
    private final String OWS_VOTE_AVERAGE = "vote_average";
    private final String OWS_RELEASE_DATE = "release_date";
    private final String OWS_ID = "id";
    private final String OWS_TRAILER = "video";

    public Movie(JSONObject data) throws JSONException, ParseException {
        mID = data.getInt(OWS_ID);
        mHasTrailer = data.getBoolean(OWS_TRAILER);
        mTitle = data.getString(OWS_TITLE);
        mMoviePoster = data.getString(OWS_MOVIE_POSTER);
        mPlot = data.getString(OWS_SYNOPSIS);
        mVoteAverage = data.getDouble(OWS_VOTE_AVERAGE);
        String releaseDate = data.getString(OWS_RELEASE_DATE);

        if(!releaseDate.isEmpty()){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            mReleaseDate = dateFormat.parse(releaseDate);
        }
    }

    private Movie(Parcel parcel){
        mID = parcel.readInt();
        mTitle = parcel.readString();
        mMoviePoster = parcel.readString();
        mPlot = parcel.readString();
        mVoteAverage = parcel.readDouble();
        mReleaseDate = new Date(parcel.readLong());
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

    public int getID(){ return mID; }
    public boolean getHasTrailer() { return mHasTrailer; }
    public String getTitle(){
        return mTitle;
    }
    public void setTitle(String title){
        mTitle = title;
    }
    public String getPlot(){ return mPlot; }
    public void setPlot(String plot){ mPlot = plot; }
    public double getVoteAverage(){ return mVoteAverage; }
    public void setVoteAverage(double vote){ mVoteAverage = vote; }
    public Date getReleaseDate(){ return mReleaseDate; }
    public void setReleaseDate(Date date){ mReleaseDate = date; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mID);
        parcel.writeString(mTitle);
        parcel.writeString(mMoviePoster);
        parcel.writeString(mPlot);
        parcel.writeDouble(mVoteAverage);
        parcel.writeLong(mReleaseDate.getTime());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Movie createFromParcel(Parcel parcel){
            return new Movie(parcel);
        }
        public Movie[] newArray(int size){
            return new Movie[size];
        }
    };
}