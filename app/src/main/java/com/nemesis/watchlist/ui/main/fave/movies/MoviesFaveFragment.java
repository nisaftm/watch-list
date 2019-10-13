package com.nemesis.watchlist.ui.main.fave.movies;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nemesis.watchlist.R;
import com.nemesis.watchlist.data.database.RealmHelper;
import com.nemesis.watchlist.data.model.Movies;
import com.nemesis.watchlist.ui.detail.DetailFaveMovies;
import com.nemesis.watchlist.ui.main.fave.movies.item.MoviesFaveAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.nemesis.watchlist.ui.detail.DetailFaveMovies.EXTRA_MOVIES;
import static com.nemesis.watchlist.ui.detail.DetailMovieActivity.EXTRA_CATEGORY;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFaveFragment extends Fragment {


    private RecyclerView rvmovies;
    private ProgressBar pbmovies;
    private List<Movies> movies;
    private RealmHelper realmHelper;
    private EditText etmovies;
    private ImageView ivrefresh;

    public MoviesFaveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null){
            Realm.init(getActivity());
        }

        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder().build();
        Realm realm = Realm.getInstance(realmConfiguration);
        realmHelper = new RealmHelper(realm);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        rvmovies = view.findViewById(R.id.rvmovies);
        pbmovies = view.findViewById(R.id.pbmovies);
        etmovies = view.findViewById(R.id.etmovies);
        ivrefresh = view.findViewById(R.id.ivrefresh);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMovies();

        etmovies.setOnEditorActionListener((textView, i, keyEvent) -> {
            String query = etmovies.getText().toString();
            if (i == EditorInfo.IME_ACTION_SEARCH){

                getCari(query);
                return true;
            }
            return false;
        });

        ivrefresh.setOnClickListener(view1 -> getMovies());

    }

    private void showData() {
        pbmovies.setVisibility(View.GONE);

        if (movies.size() != 0){
            ivrefresh.setVisibility(View.GONE);
        }else {
            ivrefresh.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager ilayout = new LinearLayoutManager(getActivity());
        rvmovies.setLayoutManager(ilayout);
        rvmovies.setHasFixedSize(true);
        rvmovies.setNestedScrollingEnabled(true);

        MoviesFaveAdapter adapter = new MoviesFaveAdapter(getActivity(), movies, item -> {
            Intent intent = new Intent(getActivity(), DetailFaveMovies.class);
            intent.putExtra(EXTRA_MOVIES, item);
            intent.putExtra(EXTRA_CATEGORY, 0);
            startActivity(intent);
        });
        rvmovies.setAdapter(adapter);
    }

    private void getCari(String query) {
        pbmovies.setVisibility(View.VISIBLE);
        movies = new ArrayList<>();
        movies = realmHelper.getSearchMovies(query, 0);
        showData();
    }

    private void getMovies() {
        pbmovies.setVisibility(View.VISIBLE);
        movies = new ArrayList<>();
        movies = realmHelper.getMovies(0);

        showData();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMovies();
    }
}
