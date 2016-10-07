package com.udacity.danilo.popularmovies;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.udacity.danilo.popularmovies.adapters.FetchMovieTask;
import com.udacity.danilo.popularmovies.adapters.MovieAdapter;
import com.udacity.danilo.popularmovies.bs.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public static final String EXTRA_MOVIE_DATA = "EXTRA_MOVIE_DATA";
    private ListView mListMovies;
    private MovieAdapter mAdapter;

    public MainActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mAdapter = new MovieAdapter(getContext(), R.layout.list_item_movie);
        mListMovies = (ListView) view.findViewById(R.id.listview_movies);
        mListMovies.setAdapter(mAdapter);

        mListMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie = mAdapter.getItem(i);
                Intent intent = new Intent(getContext(), MovieDetails.class);
                intent.putExtra(MainActivityFragment.EXTRA_MOVIE_DATA, movie);
                startActivity(intent);
            }
        });

        return view;
    }

    private void updateMovieData(){
        FetchMovieTask movieTask = new FetchMovieTask(getString(R.string.movie_db_api_key), mAdapter);
        String order = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(
                getString(R.string.options_order_key),
                getString(R.string.options_order_default_value));
        movieTask.execute(order);
    }
}
