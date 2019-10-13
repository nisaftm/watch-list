package com.nemesis.watchlist.ui.detail;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.nemesis.watchlist.R;
import com.nemesis.watchlist.WatchList;
import com.nemesis.watchlist.data.database.RealmHelper;
import com.nemesis.watchlist.data.model.Movies;
import com.nemesis.watchlist.data.model.ResultsMovies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.nemesis.watchlist.BuildConfig.urlGambar;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIES = "extraMovies";
    public static final String EXTRA_CATEGORY = "category";
    private String poster;
    private String oriTitle;
    private String releaseDate;
    private String overview;
    private String voteAve;
    private String title;
    private int category;
    private int id;
    private RealmHelper realmHelper;
    private Menu menuItem;
    private boolean isFave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_movie);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        Realm.init(DetailMovieActivity.this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm realm = Realm.getInstance(realmConfiguration);
        realmHelper = new RealmHelper(realm);

        category = getIntent().getIntExtra(EXTRA_CATEGORY, 0);

        ImageView ivdetposter = findViewById(R.id.ivdetposter);
        TextView tvdettitle = findViewById(R.id.tvdettitle);
        TextView tvdetdate = findViewById(R.id.tvdetdate);
        TextView tvdetoverview = findViewById(R.id.tvdetoverview);
        TextView tvdetrate = findViewById(R.id.tvdetrate);
        RatingBar rbdet = findViewById(R.id.rbdet);

        ResultsMovies items = getIntent().getParcelableExtra(EXTRA_MOVIES);

        if (items != null) {

            poster = items.getPosterPath();
            oriTitle = items.getOriginalTitle();
            releaseDate = items.getReleaseDate();
            overview = items.getOverview();
            voteAve = items.getVoteAverage();
            title = items.getTitle();
            id = items.getId();

            Glide.with(this)
                    .load(urlGambar+"w342/"+ poster)
                    .placeholder(R.drawable.picture_placeholder)
                    .error(R.drawable.picture_error)
                    .into(ivdetposter);

            tvdettitle.setText(oriTitle);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat newFormat = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());

            try {
                Date date = dateFormat.parse(releaseDate);
                String dateRealease = null;
                if (date != null) {
                    dateRealease = newFormat.format(date);
                }
                tvdetdate.setText(dateRealease);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvdetoverview.setText(overview);
            float rate = Float.parseFloat(voteAve);
            rbdet.setNumStars(10);
            rbdet.setStepSize(0.5f);
            rbdet.setRating(rate);
            tvdetrate.setText(String.valueOf(voteAve));

            checkFave();
        }

    }

    private void checkFave() {
        isFave = realmHelper.checkFave(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_likes, menu);
        menuItem = menu;
        setFave();
        invalidateOptionsMenu();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }else if (id == R.id.action_likes){
            if (isFave) deleteFave();
            else
                addFave();

            isFave = !isFave;
            setFave();
            invalidateOptionsMenu();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteFave() {
        realmHelper.deleteFave(id);
        Toast.makeText(WatchList.getContext(),oriTitle+" dihapus", Toast.LENGTH_SHORT).show();
    }

    private void addFave() {
        Movies movies = new Movies();
        movies.setId(id);
        movies.setCategory(category);
        movies.setOriginalTitle(oriTitle);
        movies.setOverview(overview);
        movies.setPoster_path(poster);
        movies.setReleaseDate(releaseDate);
        movies.setTitle(title);
        movies.setVoteAverage(voteAve);

        realmHelper.saveFave(movies);
    }

    private void setFave(){
        if (isFave){
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_heart_button));
        }else {
            menuItem.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_heart));
        }
    }
}
