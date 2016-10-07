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
import java.text.ParseException;

public class FetchMovieTask extends AsyncTask<String, Void, Movie[]> {
    private String mApiKey;
    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
    private MovieAdapter mAdapter;

    public FetchMovieTask(String apiKey, MovieAdapter adapter){
        mApiKey = apiKey;
        mAdapter = adapter;
    }

    private Movie[] getMovieDataFromJson(String jsonData) throws JSONException, ParseException {
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
    protected Movie[] doInBackground(String... params) {
        if(params.length < 1)
            return null;

        String orderEP = params[0];

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(orderEP)
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

            return getMovieDataFromJson(rawData.toString());
        } catch (java.io.IOException e) {
            Log.e(LOG_TAG, "IOException in FetchMovieTask", e);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException in FetchMovieTask", e);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "ParseException in FetchMovieTask", e);
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
