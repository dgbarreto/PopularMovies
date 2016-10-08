package com.udacity.danilo.popularmovies;

import android.app.VoiceInteractor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.danilo.popularmovies.bs.Movie;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    public MovieDetailsFragment() {
    }

    TextView mMovieTitle, mReleaseDate, mSynopsis, mRatingAverage;
    ImageView mMoviePoster;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View output = inflater.inflate(R.layout.fragment_movie_details, container, false);

        //Movie movie = (Movie) getActivity().getIntent().getSerializableExtra(MainActivityFragment.EXTRA_MOVIE_DATA);
        Movie movie = getActivity().getIntent().getParcelableExtra(MainActivityFragment.EXTRA_MOVIE_DATA);

        mMovieTitle = (TextView) output.findViewById(R.id.tv_movie_title);
        mMoviePoster = (ImageView) output.findViewById(R.id.iv_movie_poster);
        mReleaseDate = (TextView) output.findViewById(R.id.tv_movie_release_date);
        mSynopsis = (TextView) output.findViewById(R.id.tv_plot_synopsis);
        mRatingAverage = (TextView) output.findViewById(R.id.tv_rating_average);

        mMovieTitle.setText(movie.getTitle());
        Picasso.with(getContext()).load(movie.getMoviePosterFullUrl()).into(mMoviePoster);
        mSynopsis.setText(movie.getPlot());
        mRatingAverage.setText(String.valueOf(movie.getVoteAverage()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        mReleaseDate.setText(dateFormat.format(movie.getReleaseDate()));

        return output;
    }
}
