package com.nemesis.watchlist.ui.main.fave.movies.item;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nemesis.watchlist.BuildConfig;
import com.nemesis.watchlist.R;
import com.nemesis.watchlist.data.model.Movies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MoviesFaveAdapter extends RecyclerView.Adapter<MoviesFaveAdapter.MoviesFaveHolder> {
    private List<Movies> data;
    private Context context;
    private OnItemClickListener listener;
    public interface OnItemClickListener{
        void OnItemClick(Movies item);
    }

    public MoviesFaveAdapter(Context context, List<Movies> data, OnItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
        Log.e(TAG, "MoviesFaveAdapter: "+data.toString());
    }


    @NonNull
    @Override
    public MoviesFaveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_movies, parent, false);
        return new MoviesFaveHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesFaveHolder holder, int position) {
        Movies item = data.get(position);

        Glide.with(context)
                .load(BuildConfig.urlGambar+"w342/"+item.getPoster_path())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.ivitemposter);

        holder.tvitemtitle.setText(item.getOriginalTitle());
        float rate = Float.parseFloat(String.valueOf(item.getVoteAverage()));
        holder.rbitemrating.setNumStars(10);
        holder.rbitemrating.setStepSize(0.5f);
        holder.rbitemrating.setRating(rate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());

        try {
            Date date = dateFormat.parse(item.getReleaseDate());
            String dateRealease = null;
            if (date != null) {
                dateRealease = context.getString(R.string.text_release_date)+newFormat.format(date);
            }
            holder.tvitemreleasedate.setText(dateRealease);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(view -> listener.OnItemClick(item));

    }


    @Override
    public int getItemCount() {
        return data.size();

    }

    static class MoviesFaveHolder extends RecyclerView.ViewHolder{
        private ImageView ivitemposter;
        private TextView tvitemreleasedate;
        private RatingBar rbitemrating;
        private TextView tvitemtitle;

        MoviesFaveHolder(View itemView) {
            super(itemView);
            ivitemposter = itemView.findViewById(R.id.ivitemposter);
            tvitemtitle = itemView.findViewById(R.id.tvitemtitle);
            rbitemrating = itemView.findViewById(R.id.rbitemrating);
            tvitemreleasedate = itemView.findViewById(R.id.tvitemreleasedate);
        }
    }
}
