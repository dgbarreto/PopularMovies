package com.udacity.danilo.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.udacity.danilo.popularmovies.adapters.FetchReviewTask;
import com.udacity.danilo.popularmovies.adapters.ReviewAdapter;

public class ReviewActivity extends AppCompatActivity {
    private RecyclerView mRecyclerReviews;
    private ReviewAdapter mAdapter;
    private String mMovieID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetails);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMovieID = getIntent().getExtras().getString(MovieDetailsFragment.EXTRA_MOVIE_ID);

        mRecyclerReviews = (RecyclerView) findViewById(R.id.rv_reviews);
        mRecyclerReviews.setLayoutManager(new LinearLayoutManager(this));

        FetchReviewTask fetchReviewTask = new FetchReviewTask(this, getString(R.string.movie_db_api_key), mAdapter, mRecyclerReviews);
        fetchReviewTask.execute(mMovieID);
    }
}
