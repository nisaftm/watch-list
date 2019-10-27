package com.nemesis.favesapp.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nemesis.favesapp.R;
import com.nemesis.favesapp.data.content.DBContract;
import com.nemesis.favesapp.data.content.MappingHelper;
import com.nemesis.favesapp.data.model.Movies;
import com.nemesis.favesapp.ui.detail.DetailFaveMoviesActivity;
import com.nemesis.favesapp.ui.main.item.MoviesFaveAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.nemesis.favesapp.ui.detail.DetailFaveMoviesActivity.EXTRA_MOVIES;

public class MainActivity extends AppCompatActivity implements LoadMoviesCallback{
    private RecyclerView rvmovies;
    private ProgressBar pbmovies;
    private Activity activity = this;
    private String EXTRAS = "extras";
    private ArrayList<Movies> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvmovies = findViewById(R.id.rvmovies);
        pbmovies = findViewById(R.id.pbmovies);

        HandlerThread handlerThread = new HandlerThread("MoviesObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        MoviesObserver moviesObserver = new MoviesObserver(handler, this);
        getContentResolver().registerContentObserver(DBContract.CONTENT_URI, true, moviesObserver);
        if (savedInstanceState == null){
            new LoadMoviesAsync(activity, this).execute();
        }else {
            arrayList = savedInstanceState.getParcelableArrayList(EXTRAS);
            if (arrayList.size() > 0){
                showData();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRAS, arrayList);
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pbmovies.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadMoviesAsync(activity, this).execute();
    }

    @Override
    public void postExecute(ArrayList<Movies> movies) {
        pbmovies.setVisibility(View.GONE);
        arrayList = new ArrayList<>();
        arrayList.addAll(movies);

        showData();
    }

    private void showData() {
        LinearLayoutManager ilayout = new LinearLayoutManager(this);
        rvmovies.setLayoutManager(ilayout);
        rvmovies.setHasFixedSize(true);
        rvmovies.setNestedScrollingEnabled(true);

        MoviesFaveAdapter adapter = new MoviesFaveAdapter(this, arrayList, new MoviesFaveAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Movies item) {
                Intent intent = new Intent(activity, DetailFaveMoviesActivity.class);
                intent.putExtra(EXTRA_MOVIES, item);
                startActivity(intent);
            }
        });
        rvmovies.setAdapter(adapter);
    }

    public static class MoviesObserver extends ContentObserver {
        private final Context context;

        public MoviesObserver(Handler handler, Context context) {
            super(handler);
            this.context=context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadMoviesAsync(context, (LoadMoviesCallback) context).execute();
        }
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<Movies>> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMoviesCallback> weakCallback;
        private LoadMoviesAsync(Context context, LoadMoviesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback= new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movies> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor cursor = context.getContentResolver().query(DBContract.CONTENT_URI,
                    null, null, null, null, null);
            return MappingHelper.cursorList(cursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }
}
interface LoadMoviesCallback{
    void preExecute();
    void postExecute(ArrayList<Movies> movies);
}
