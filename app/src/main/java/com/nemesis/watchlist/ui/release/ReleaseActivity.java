package com.nemesis.watchlist.ui.release;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nemesis.watchlist.R;
import com.nemesis.watchlist.data.model.ResultsMovies;
import com.nemesis.watchlist.ui.detail.DetailMovieActivity;
import com.nemesis.watchlist.ui.main.watch.movies.item.MoviesAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.nemesis.watchlist.ui.detail.DetailMovieActivity.EXTRA_CATEGORY;
import static com.nemesis.watchlist.ui.detail.DetailMovieActivity.EXTRA_MOVIES;

public class ReleaseActivity extends AppCompatActivity {

    private ProgressBar pbmovies;
    private RecyclerView rvmovies;
    private MoviesReleaseView moviesReleaseVM;
    private ArrayList<ResultsMovies> arrayList;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        rvmovies = findViewById(R.id.rvmovies);
        pbmovies = findViewById(R.id.pbmovies);

        LinearLayoutManager ilayout = new LinearLayoutManager(this);
        rvmovies.setLayoutManager(ilayout);
        rvmovies.setHasFixedSize(true);
        rvmovies.setNestedScrollingEnabled(true);

        moviesReleaseVM = ViewModelProviders.of(this).get(MoviesReleaseView.class);

        getRelease();
    }

    private void getRelease() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String tanggal = sdf.format(calendar.getTime());

        moviesReleaseVM.releaseLiveData.observe(this, res -> {
            pbmovies.setVisibility(View.GONE);

            arrayList = new ArrayList<>();
            arrayList.addAll(res);
            showData();
        });
        moviesReleaseVM.responError.observe(this, responseError -> {
            pbmovies.setVisibility(View.GONE);
            Toast.makeText(activity, responseError.getStatusMessage(), Toast.LENGTH_SHORT).show();
        });
        moviesReleaseVM.getRelease(tanggal);
    }

    private void showData() {
        rvmovies.removeAllViews();
        MoviesAdapter adapter = new MoviesAdapter(this, arrayList, item -> {
            Intent intent = new Intent(this, DetailMovieActivity.class);
            intent.putExtra(EXTRA_MOVIES, item);
            intent.putExtra(EXTRA_CATEGORY, 0);
            startActivity(intent);
        });
        rvmovies.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
