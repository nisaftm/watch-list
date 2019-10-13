package com.nemesis.watchlist.ui.main.watch.movies;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.ConfigurationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nemesis.watchlist.R;
import com.nemesis.watchlist.data.model.ResponseError;
import com.nemesis.watchlist.data.model.ResultsMovies;
import com.nemesis.watchlist.ui.detail.DetailMovieActivity;
import com.nemesis.watchlist.ui.main.watch.movies.item.MoviesAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.nemesis.watchlist.ui.detail.DetailMovieActivity.EXTRA_CATEGORY;
import static com.nemesis.watchlist.ui.detail.DetailMovieActivity.EXTRA_MOVIES;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private RecyclerView rvmovies;
    private ProgressBar pbmovies;
    private MoviesViewModel moviesVM;
    private ArrayList<ResultsMovies> arrayList;
    private Observer<List<ResultsMovies>> observerData = resultsMovies -> {
        pbmovies.setVisibility(View.GONE);

        arrayList = new ArrayList<>();
        arrayList.addAll(resultsMovies);

        showData();
    };

    private void showData() {
        rvmovies.removeAllViews();
        MoviesAdapter adapter = new MoviesAdapter(getActivity(), arrayList, item -> {
            Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
            intent.putExtra(EXTRA_MOVIES, item);
            intent.putExtra(EXTRA_CATEGORY, 0);
            startActivity(intent);
        });
        rvmovies.setAdapter(adapter);
    }

    private Observer<ResponseError> observerError = responseError -> {
        pbmovies.setVisibility(View.GONE);
        Toast.makeText(getActivity(), responseError.getStatusMessage(), Toast.LENGTH_SHORT).show();
    };
    private EditText etmovies;
    private ImageView ivrefresh;
    private MoviesSearchViewModel moviesSearchVM;
    private Observer<? super List<ResultsMovies>> observerSearchData = new Observer<List<ResultsMovies>>() {
        @Override
        public void onChanged(List<ResultsMovies> results) {
            pbmovies.setVisibility(View.GONE);

            arrayList = new ArrayList<>();
            arrayList.addAll(results);
            showData();
        }
    };
    private Observer<? super ResponseError> observerSearchError = new Observer<ResponseError>() {
        @Override
        public void onChanged(ResponseError responseError) {
            pbmovies.setVisibility(View.GONE);
            Toast.makeText(getActivity(), responseError.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    };


    public MoviesFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moviesVM = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesSearchVM = ViewModelProviders.of(this).get(MoviesSearchViewModel.class);
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

        LinearLayoutManager ilayout = new LinearLayoutManager(getActivity());
        rvmovies.setLayoutManager(ilayout);
        rvmovies.setHasFixedSize(true);
        rvmovies.setNestedScrollingEnabled(true);

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

    private void getCari(String query) {
        pbmovies.setVisibility(View.VISIBLE);
        String language = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).toLanguageTags();

        moviesSearchVM.moviesLiveData.observe(this, observerSearchData);
        moviesSearchVM.moviesError.observe(this, observerSearchError);
        moviesSearchVM.getMovies(language, query);
    }

    private void getMovies() {
        pbmovies.setVisibility(View.VISIBLE);
        String language = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).toLanguageTags();

        moviesVM.moviesLiveData.observe(this, observerData);
        moviesVM.moviesError.observe(this, observerError);
        moviesVM.getMovies(language, getActivity());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
