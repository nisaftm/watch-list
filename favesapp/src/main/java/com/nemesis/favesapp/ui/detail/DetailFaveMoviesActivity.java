package com.nemesis.favesapp.ui.detail;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.nemesis.favesapp.R;
import com.nemesis.favesapp.data.model.Movies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.nemesis.favesapp.BuildConfig.urlGambar;

public class DetailFaveMoviesActivity extends AppCompatActivity {
    public final static String EXTRA_MOVIES = "movies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fave_movies);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        ImageView ivdetposter = findViewById(R.id.ivdetposter);
        TextView tvdettitle = findViewById(R.id.tvdettitle);
        TextView tvdetdate = findViewById(R.id.tvdetdate);
        TextView tvdetoverview = findViewById(R.id.tvdetoverview);
        TextView tvdetrate = findViewById(R.id.tvdetrate);
        TextView textView3 = findViewById(R.id.textView3);
        RatingBar rbdet = findViewById(R.id.rbdet);


        Movies items = getIntent().getParcelableExtra(EXTRA_MOVIES);




        if (items != null) {
            String poster = items.getPoster_path();
            String oriTitle = items.getOriginalTitle();
            String releaseDate = items.getReleaseDate();
            String overview = items.getOverview();
            String voteAve = items.getVoteAverage();

            int category = items.getCategory();

            if (category == 1) {
                textView3.setText(getString(R.string.text_first_air));
            }

            Glide.with(this)
                    .load(urlGambar + "w342/" + poster)
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
            tvdetrate.setText(voteAve);

        }
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
