package com.nemesis.watchlist.ui.main.fave;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nemesis.watchlist.R;
import com.nemesis.watchlist.ui.main.fave.movies.MoviesFaveFragment;
import com.nemesis.watchlist.ui.main.fave.series.SeriesFaveFragment;
import com.nemesis.watchlist.ui.main.item.TabWatchAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaveFragment extends Fragment {
    private FragmentManager fragmentManager;


    public FaveFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getChildFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_watch, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tlwatch = view.findViewById(R.id.tlwatch);
        ViewPager vpwatch = view.findViewById(R.id.vpwatch);

        TabWatchAdapter adapter = new TabWatchAdapter(fragmentManager);
        adapter.addFragment(new MoviesFaveFragment(), getString(R.string.title_movies));
        adapter.addFragment(new SeriesFaveFragment(), getString(R.string.title_tv_series));
        vpwatch.setAdapter(adapter);
        tlwatch.setupWithViewPager(vpwatch);
    }
}
