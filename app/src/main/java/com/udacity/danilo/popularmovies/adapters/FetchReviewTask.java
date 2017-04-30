package com.udacity.danilo.popularmovies.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.udacity.danilo.popularmovies.bs.Review;

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

public class FetchReviewTask extends AsyncTask<String, Void, Review[]> {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private String mApiKey;
    private Context mContext;
    private ReviewAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public FetchReviewTask(Context context, String apiKey, ReviewAdapter adapter, RecyclerView recyclerView){
        mContext = context;
        mApiKey = apiKey;
        mAdapter = adapter;
        mRecyclerView = recyclerView;
    }

    @Override
    protected Review[] doInBackground(String... params) {
        if(params.length <= 0)
            return null;

        String movieID = params[0];

        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(movieID)
                .appendPath("reviews")
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

            while((line = bufferedReader.readLine()) != null){
                rawData.append(line);
            }

            JSONObject jsonData = new JSONObject(rawData.toString());
            JSONArray result = jsonData.getJSONArray("results");
            Review[] reviews = new Review[result.length()];

            for(int i = 0; i < result.length(); i++)
                reviews[i] = new Review(result.getJSONObject(i));

            return reviews;
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
    protected void onPostExecute(Review[] reviews) {
        if(reviews != null){
            mAdapter = new ReviewAdapter(reviews);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
