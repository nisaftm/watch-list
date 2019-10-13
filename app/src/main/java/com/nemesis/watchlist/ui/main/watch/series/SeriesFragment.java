package com.nemesis.watchlist.ui.main.watch.series;


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
import com.nemesis.watchlist.data.model.ResultsSeries;
import com.nemesis.watchlist.ui.detail.DetailSeriesActivity;
import com.nemesis.watchlist.ui.main.watch.series.item.SeriesAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.nemesis.watchlist.ui.detail.DetailMovieActivity.EXTRA_CATEGORY;
import static com.nemesis.watchlist.ui.detail.DetailSeriesActivity.EXTRA_SERIES;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFragment extends Fragment {
    private RecyclerView rvmovies;
    private ProgressBar pbmovies;
    private SeriesViewModel seriesVm;
    private ArrayList<ResultsSeries> arrayList;
    private Observer<List<ResultsSeries>> observerData = resultsSeries -> {
        pbmovies.setVisibility(View.GONE);
        arrayList = new ArrayList<>();
        arrayList.addAll(resultsSeries);

        showData();
    };

    private EditText etmovies;
    private ImageView ivrefresh;
    private SeriesSearchViewModel seriesSearchVM;

    private Observer<? super ResponseError> observerSearchError = new Observer<ResponseError>() {
        @Override
        public void onChanged(ResponseError responseError) {
            pbmovies.setVisibility(View.GONE);
            Toast.makeText(getActivity(), responseError.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    private Observer<? super List<ResultsSeries>> observerSearchData = new Observer<List<ResultsSeries>>() {
        @Override
        public void onChanged(List<ResultsSeries> resultsSeries) {
            pbmovies.setVisibility(View.GONE);
            arrayList = new ArrayList<>();
            arrayList.addAll(resultsSeries);

            showData();
        }
    };

    private void showData() {
        rvmovies.removeAllViews();
        SeriesAdapter adapter = new SeriesAdapter(getActivity(), arrayList, item -> {
            Intent intent = new Intent(getActivity(), DetailSeriesActivity.class);
            intent.putExtra(EXTRA_SERIES, item);
            intent.putExtra(EXTRA_CATEGORY, 1);
            startActivity(intent);
        });
        rvmovies.setAdapter(adapter);
    }

    private Observer<? super ResponseError> observerError = new Observer<ResponseError>() {
        @Override
        public void onChanged(ResponseError responseError) {
            pbmovies.setVisibility(View.GONE);
            Toast.makeText(getActivity(), responseError.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    public SeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        seriesVm = ViewModelProviders.of(this).get(SeriesViewModel.class);
        seriesSearchVM = ViewModelProviders.of(this).get(SeriesSearchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvmovies = view.findViewById(R.id.rvmovies);
        pbmovies = view.findViewById(R.id.pbmovies);
        etmovies = view.findViewById(R.id.etmovies);
        ivrefresh = view.findViewById(R.id.ivrefresh);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        seriesVm.seriesLiveData.observe(this, observerData);
        seriesVm.seriesError.observe(this, observerError);

        seriesSearchVM.seriesLiveData.observe(this, observerSearchData);
        seriesSearchVM.seriesError.observe(this, observerSearchError);

        getSeries();

        rvmovies.setLayoutManager(new LinearLayoutManager(getActivity()));
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

        ivrefresh.setOnClickListener(view1 -> getSeries());
    }

    private void getCari(String query) {
        pbmovies.setVisibility(View.VISIBLE);
        String language = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).toLanguageTags();
        seriesSearchVM.getSeriesSearch(language, query);
    }

    private void getSeries() {
        pbmovies.setVisibility(View.VISIBLE);
        String language = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).toLanguageTags();
        seriesVm.getSeries(language, getActivity());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
