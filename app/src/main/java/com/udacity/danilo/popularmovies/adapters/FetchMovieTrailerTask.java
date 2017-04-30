package com.udacity.danilo.popularmovies.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchMovieTrailerTask extends AsyncTask<String, Void, String> {
    private String mApiKey;
    private final String LOG_TAG = FetchMovieTrailerTask.class.getSimpleName();
    private Context mContext;

    public FetchMovieTrailerTask(String apiKey, Context context){
        mApiKey = apiKey;
        mContext = context;
    }

    @Override
    protected String doInBackground(String... params) {
        if(params.length < 1)
            return null;

        String movieID = params[0];

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(movieID)
                .appendPath("videos")
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
            StringBuilder rawData = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null)
                rawData.append(line);

            JSONObject jsonObject = new JSONObject(rawData.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            if(jsonArray.length() <= 0)
                return null;

            JSONObject jsonTrailer = jsonArray.getJSONObject(0);
            return jsonTrailer.getString("key");
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "MalformedURLException exception", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException exception", e);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException exception", e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s != null){
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + s));
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + s));

            try{
                mContext.startActivity(appIntent);
            }
            catch (ActivityNotFoundException e){
                mContext.startActivity(webIntent);
            }
        }
    }
}
