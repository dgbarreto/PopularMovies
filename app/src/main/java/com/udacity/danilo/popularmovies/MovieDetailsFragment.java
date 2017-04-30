package com.udacity.danilo.popularmovies;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.danilo.popularmovies.adapters.FetchMovieTrailerTask;
import com.udacity.danilo.popularmovies.bs.Movie;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {
    ImageButton ibTrailer;
    private String mMovieID;

    public MovieDetailsFragment() {
    }

    TextView mMovieTitle, mReleaseDate, mSynopsis, mRatingAverage;
    ImageView mMoviePoster;
    public static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View output = inflater.inflate(R.layout.fragment_movie_details, container, false);
        setHasOptionsMenu(true);

        //Movie movie = (Movie) getActivity().getIntent().getSerializableExtra(MainActivityFragment.EXTRA_MOVIE_DATA);
        final Movie movie = getActivity().getIntent().getParcelableExtra(MainActivityFragment.EXTRA_MOVIE_DATA);

        mMovieTitle = (TextView) output.findViewById(R.id.tv_movie_title);
        mMoviePoster = (ImageView) output.findViewById(R.id.iv_movie_poster);
        mReleaseDate = (TextView) output.findViewById(R.id.tv_movie_release_date);
        mSynopsis = (TextView) output.findViewById(R.id.tv_plot_synopsis);
        mRatingAverage = (TextView) output.findViewById(R.id.tv_rating_average);
        ibTrailer = (ImageButton) output.findViewById(R.id.btTrailer);

        mMovieID = String.valueOf(movie.getID());

        mMovieTitle.setText(movie.getTitle());
        Picasso.with(getContext()).load(movie.getMoviePosterFullUrl()).into(mMoviePoster);
        mSynopsis.setText(movie.getPlot());
        mRatingAverage.setText(String.valueOf(movie.getVoteAverage()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        mReleaseDate.setText(dateFormat.format(movie.getReleaseDate()));

        //ibTrailer.setVisibility(View.INVISIBLE);

        ibTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Trailer", Toast.LENGTH_LONG).show();
                FetchMovieTrailerTask fetchMovieTrailerTask = new FetchMovieTrailerTask(getString(R.string.movie_db_api_key), getContext());
                fetchMovieTrailerTask.execute(String.valueOf(movie.getID()));
            }
        });

        return output;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_reviews:
                Intent reviewIntent = new Intent(getContext(), ReviewActivity.class);
                reviewIntent.putExtra(MovieDetailsFragment.EXTRA_MOVIE_ID, mMovieID);
                startActivity(reviewIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
