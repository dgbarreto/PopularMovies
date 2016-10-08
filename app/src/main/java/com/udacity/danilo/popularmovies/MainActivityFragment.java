package com.udacity.danilo.popularmovies;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.udacity.danilo.popularmovies.adapters.FetchMovieTask;
import com.udacity.danilo.popularmovies.adapters.MovieAdapter;
import com.udacity.danilo.popularmovies.bs.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public static final String EXTRA_MOVIE_DATA = "EXTRA_MOVIE_DATA";
    private GridView mListMovies;
    private MovieAdapter mAdapter;
    private final String ORDER_POPULAR = "popular";
    private final String ORDER_TOP_RATED = "top_rated";

    public MainActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieData();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_popular:
                updateMovieData(ORDER_POPULAR);
                return true;
            case R.id.action_top_rated:
                updateMovieData(ORDER_TOP_RATED);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mAdapter = new MovieAdapter(getContext(), R.layout.list_item_movie);
        mListMovies = (GridView) view.findViewById(R.id.listview_movies);
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
        String order = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(
                getString(R.string.options_order_key),
                getString(R.string.options_order_default_value));
        updateMovieData(order);
    }
    private  void updateMovieData(String order){
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString(getString(R.string.options_order_key), order).apply();
        FetchMovieTask movieTask = new FetchMovieTask(getString(R.string.movie_db_api_key), mAdapter);
        movieTask.execute(order);
    }
}
