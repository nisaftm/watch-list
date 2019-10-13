package com.nemesis.watchlist.ui.main.watch.movies.item;

import android.content.Context;
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
import com.nemesis.watchlist.data.model.ResultsMovies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {
    private List<ResultsMovies> data;
    private Context context;
    private final OnItemClickListener listener;

    @NonNull
    @Override
    public MoviesAdapter.MoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_movies, parent, false);
        return new MoviesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MoviesHolder holder, int position) {
        final ResultsMovies item = data.get(position);
        Glide.with(context)
                .load(BuildConfig.urlGambar+"w342/"+item.getPosterPath())
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


    public interface OnItemClickListener{
        void OnItemClick(ResultsMovies item);
    }


    public MoviesAdapter(Context context, List<ResultsMovies> data, OnItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    static class MoviesHolder extends RecyclerView.ViewHolder{
        private ImageView ivitemposter;
        private TextView tvitemreleasedate;
        private RatingBar rbitemrating;
        private TextView tvitemtitle;

        MoviesHolder(View itemView) {
            super(itemView);
            ivitemposter = itemView.findViewById(R.id.ivitemposter);
            tvitemtitle = itemView.findViewById(R.id.tvitemtitle);
            rbitemrating = itemView.findViewById(R.id.rbitemrating);
            tvitemreleasedate = itemView.findViewById(R.id.tvitemreleasedate);
        }
    }

}
