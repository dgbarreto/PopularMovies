package com.udacity.danilo.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;

import com.udacity.danilo.popularmovies.adapters.FavoriteMovieAdapter;
import com.udacity.danilo.popularmovies.adapters.FetchMovieTask;
import com.udacity.danilo.popularmovies.adapters.MovieAdapter;
import com.udacity.danilo.popularmovies.bs.Movie;
import com.udacity.danilo.popularmovies.data.MovieContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public static final String EXTRA_MOVIE_DATA = "EXTRA_MOVIE_DATA";
    private GridView mListMovies;
    private MovieAdapter mAdapter;
    private FavoriteMovieAdapter mFavoriteAdapter;
    private final String ORDER_POPULAR = "popular";
    private final String ORDER_TOP_RATED = "top_rated";
    public static final String ORDER_FAVORITE = "favorites";
    private Spinner spOrder;

    public MainActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieData();
        setHasOptionsMenu(true);

        spOrder = (Spinner) getView().findViewById(R.id.spOrder);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getView().getContext(), R.array.options_order, android.R.layout.simple_spinner_dropdown_item);
        spOrder.setAdapter(adapter);
        spOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String valueSelected = (String) adapterView.getItemAtPosition(i);
                String[] values = getResources().getStringArray(R.array.options_order_value);
                updateMovieData(values[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
            case R.id.action_favorite:
                updateMovieData(ORDER_FAVORITE);
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

        //mFavoriteAdapter = new FavoriteMovieAdapter(getActivity(), null, 0);

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
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.putString(getString(R.string.options_order_key), order);
        editor.apply();
        editor.commit();
        FetchMovieTask movieTask = new FetchMovieTask(BuildConfig.MOVIEDBAPIKEY, mAdapter, getActivity());
        movieTask.execute(order);
        /*Cursor cursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                new String[]{MovieContract.MovieEntry.COLUMN_ID, MovieContract.MovieEntry.COLUMN_MOVIE_POSTER},
                null,
                null,
                null);

        mListMovies.setAdapter(mFavoriteAdapter);
        mFavoriteAdapter.swapCursor(cursor);*/
    }
}
