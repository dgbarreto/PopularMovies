package com.udacity.danilo.popularmovies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.udacity.danilo.popularmovies.adapters.FetchMovieTask;
import com.udacity.danilo.popularmovies.adapters.MovieAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

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

        return view;
    }

    private void updateMovieData(){
        FetchMovieTask movieTask = new FetchMovieTask(getString(R.string.movie_db_api_key), mAdapter);
        movieTask.execute();
    }


}
