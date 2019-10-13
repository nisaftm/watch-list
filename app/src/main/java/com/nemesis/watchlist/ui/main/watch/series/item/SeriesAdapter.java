package com.nemesis.watchlist.ui.main.watch.series.item;

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
import com.nemesis.watchlist.data.model.ResultsSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesHolder> {

    private List<ResultsSeries> data;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener{
        void OnItemClick(ResultsSeries item);
    }

    public SeriesAdapter(Context context, List<ResultsSeries> data, OnItemClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_movies, parent, false);
        return new SeriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesHolder holder, int position) {
        final ResultsSeries item = data.get(position);
        Glide.with(context)
                .load(BuildConfig.urlGambar+"w342/"+item.getPosterPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.ivitemposter);

        holder.tvitemtitle.setText(item.getName());
        float rate = Float.parseFloat(String.valueOf(item.getVoteAverage()));
        holder.rbitemrating.setNumStars(10);
        holder.rbitemrating.setStepSize(0.5f);
        holder.rbitemrating.setRating(rate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());

        try {
            String firstAir =  item.getFirstAirDate();
            if (firstAir != null){
                Date date = dateFormat.parse(firstAir);
                String firstAirDate = "";
                if (date != null) {
                    firstAirDate = context.getString(R.string.text_first_air)+newFormat.format(date);
                }
                holder.tvitemreleasedate.setText(firstAirDate);
            }else {
                holder.tvitemreleasedate.setText("-");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(view -> listener.OnItemClick(item));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SeriesHolder extends RecyclerView.ViewHolder {
        private ImageView ivitemposter;
        private TextView tvitemreleasedate;
        private RatingBar rbitemrating;
        private TextView tvitemtitle;
        SeriesHolder(@NonNull View itemView) {
            super(itemView);

            ivitemposter = itemView.findViewById(R.id.ivitemposter);
            tvitemtitle = itemView.findViewById(R.id.tvitemtitle);
            rbitemrating = itemView.findViewById(R.id.rbitemrating);
            tvitemreleasedate = itemView.findViewById(R.id.tvitemreleasedate);
        }
    }
}
