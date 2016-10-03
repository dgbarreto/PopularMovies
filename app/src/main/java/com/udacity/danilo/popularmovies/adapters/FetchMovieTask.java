package com.udacity.danilo.popularmovies.adapters;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.udacity.danilo.popularmovies.bs.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by danil on 02/10/2016.
 */

public class FetchMovieTask extends AsyncTask<Void, Void, Movie[]> {
    private String mApiKey;
    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
    private MovieAdapter mAdapter;

    public FetchMovieTask(String apiKey, MovieAdapter adapter){
        mApiKey = apiKey;
        mAdapter = adapter;
    }

    private Movie[] getMovieDataFromJson(String jsonData) throws JSONException {
        final String OWS_LIST = "results";

        JSONObject moviesJSON = new JSONObject(jsonData);
        JSONArray moviesArray = moviesJSON.getJSONArray(OWS_LIST);

        Movie[] results = new Movie[moviesArray.length()];
        for(int i = 0; i < moviesArray.length(); i++){
            JSONObject movieOBJ = moviesArray.getJSONObject(i);
            results[i] = new Movie(movieOBJ);
        }

        return  results;
    }

    @Override
    protected Movie[] doInBackground(Void... voids) {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("top_rated")
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

            return getMovieDataFromJson(rawData.toString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        if(movies != null){
            mAdapter.clear();
            mAdapter.addAll(movies);
        }
    }
}
