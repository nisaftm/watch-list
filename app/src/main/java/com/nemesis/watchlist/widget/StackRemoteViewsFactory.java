package com.nemesis.watchlist.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.nemesis.watchlist.BuildConfig;
import com.nemesis.watchlist.R;
import com.nemesis.watchlist.data.database.RealmHelper;
import com.nemesis.watchlist.data.model.Movies;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static androidx.constraintlayout.widget.Constraints.TAG;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context context;
    private List<Bitmap> bitmaps = new ArrayList<>();
    private ArrayList<Movies> movies = new ArrayList<>();

    StackRemoteViewsFactory(Context applicationContext) {
        context = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Realm.init(context);

        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder().build();
        Realm realm = Realm.getInstance(realmConfiguration);
        RealmHelper realmHelper = new RealmHelper(realm);

        movies.addAll(realmHelper.getFaves());

        Log.e(TAG, "onDataSetChanged: "+movies.size());
        for (int i=0; i<movies.size(); i++){
            String path= BuildConfig.urlGambar+"w342/"+movies.get(i).getPoster_path();
            Log.e(TAG, "onDataSetChanged: "+path);
            bitmaps.add(getBitmapfromURL(path));
        }
    }

    private Bitmap getBitmapfromURL(String path) {
        try{
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        }catch (IOException e){
            return null;
        }

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_widget);
        remoteViews.setImageViewBitmap(R.id.imageView, bitmaps.get(i));


        Bundle extras = new Bundle();
        extras.putInt(ImagePosterWidget.EXTRA_ITEM, i);
        Intent intent = new Intent();
        intent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, intent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
