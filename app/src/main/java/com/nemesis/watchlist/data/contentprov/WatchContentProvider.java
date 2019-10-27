package com.nemesis.watchlist.data.contentprov;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nemesis.watchlist.WatchList;
import com.nemesis.watchlist.data.model.Movies;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.RealmSchema;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.nemesis.watchlist.data.database.DBContract.AUTHORITY;
import static com.nemesis.watchlist.data.database.DBContract.CONTENT_URI;
import static com.nemesis.watchlist.data.database.DBContract.MoviesColumns.CATEGORY;
import static com.nemesis.watchlist.data.database.DBContract.MoviesColumns.ID;
import static com.nemesis.watchlist.data.database.DBContract.MoviesColumns.ORIGINALTITLE;
import static com.nemesis.watchlist.data.database.DBContract.MoviesColumns.OVERVIEW;
import static com.nemesis.watchlist.data.database.DBContract.MoviesColumns.POSTERPATH;
import static com.nemesis.watchlist.data.database.DBContract.MoviesColumns.RELEASEDATE;
import static com.nemesis.watchlist.data.database.DBContract.MoviesColumns.TITLE;
import static com.nemesis.watchlist.data.database.DBContract.MoviesColumns.VOTEAVERAGE;
import static com.nemesis.watchlist.data.database.DBContract.TABLE_NAME;

public class WatchContentProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID= 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIE);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME+"/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        Realm.init(getContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration((realm, oldVersion, newVersion) -> {
                    RealmSchema realmSchema = realm.getSchema();
                    if (oldVersion != 0){
                        realmSchema.create(TABLE_NAME)
                                .addField(ID, Integer.class)
                                .addField(TITLE, String.class)
                                .addField(ORIGINALTITLE, String.class)
                                .addField(VOTEAVERAGE, String.class)
                                .addField(OVERVIEW, String.class)
                                .addField(RELEASEDATE, String.class)
                                .addField(POSTERPATH, String.class)
                                .addField(CATEGORY, Integer.class);
                        oldVersion++;
                    }
                })
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        int match = uriMatcher.match(uri);

        MatrixCursor cursor = new MatrixCursor(new String[]{
                ID,
                TITLE,
                ORIGINALTITLE,
                VOTEAVERAGE,
                OVERVIEW,
                RELEASEDATE,
                POSTERPATH,
                CATEGORY
        });

        Realm realm = Realm.getDefaultInstance();

        try {
            switch (match){
                case MOVIE:
                    RealmResults<Movies> results = realm.where(Movies.class).findAll();
                    for (Movies movies1 : results){
                        Object[] rowData = new Object[]{
                                movies1.getId(),
                                movies1.getTitle(),
                                movies1.getOriginalTitle(),
                                movies1.getVoteAverage(),
                                movies1.getOverview(),
                                movies1.getReleaseDate(),
                                movies1.getPoster_path(),
                                movies1.getCategory()};
                        cursor.addRow(rowData);
                    }
                    break;
                case MOVIE_ID:
                    int id = Integer.parseInt(uri.getPathSegments().get(1));
                    Movies movies1 = realm.where(Movies.class).equalTo("id", id).findFirst();
                    cursor.addRow(new Object[]{
                            movies1.getId(),
                            movies1.getTitle(),
                            movies1.getOriginalTitle(),
                            movies1.getVoteAverage(),
                            movies1.getOverview(),
                            movies1.getReleaseDate(),
                            movies1.getPoster_path(),
                            movies1.getCategory()});
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri:"+uri);
            }
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }finally {
            realm.close();
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int match = uriMatcher.match(uri);
        Uri returnUri;

        Realm realm = Realm.getDefaultInstance();
        try{
            if (match == MOVIE) {
                Movies movies = new Movies();
                movies.setId((Integer) contentValues.get(ID));
                movies.setPoster_path(contentValues.get(POSTERPATH).toString());
                movies.setReleaseDate(contentValues.get(RELEASEDATE).toString());
                movies.setOverview(contentValues.get(OVERVIEW).toString());
                movies.setVoteAverage(contentValues.get(VOTEAVERAGE).toString());
                movies.setOriginalTitle(contentValues.get(ORIGINALTITLE).toString());
                movies.setTitle(contentValues.get(TITLE).toString());
                movies.setCategory((Integer) contentValues.get(CATEGORY));
                //realmHelper.saveFave(movies);
                realm.executeTransaction(realm1 -> realm1.copyToRealm(movies));
                returnUri = ContentUris.withAppendedId(CONTENT_URI, '1');
            } else {
                throw new UnsupportedOperationException("Unknowen uri: " + uri);
            }
            getContext().getContentResolver().notifyChange(uri, null);
            Toast.makeText(WatchList.getContext(), contentValues.get(ORIGINALTITLE).toString() + " ditambahkan", Toast.LENGTH_SHORT).show();
        }finally {
            realm.close();
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int del = 0;
        Realm realm = Realm.getDefaultInstance();
        try{
            switch (uriMatcher.match(uri)){
                case MOVIE:
                    s = (s == null) ? "1" : s;
                    RealmResults<Movies> results = realm.where(Movies.class)
                            .equalTo(s, Integer.parseInt(strings[0])).findAll();
                    realm.beginTransaction();
                    results.deleteAllFromRealm();
                    del++;
                    realm.commitTransaction();
                    break;

                case MOVIE_ID:
                    Integer id = Integer.parseInt(String.valueOf(ContentUris.parseId(uri)));
                    Log.e(TAG, "delete: "+id);
                    RealmResults<Movies> movies = realm.where(Movies.class).equalTo("id", id).findAll();
                    realm.executeTransaction(realm1 -> movies.deleteAllFromRealm());
                    /*Movies movies = realm.where(Movies.class).equalTo("id", id)
                            .findFirst();


                    realm.beginTransaction();
                    movies.deleteFromRealm();*/
                    del++;
                    //realm.commitTransaction();
                    break;

                    default:
                        throw new IllegalArgumentException("Illegal delete URI");
            }
        }finally {
            realm.close();
        }
        if (del > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return del;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        return 0;
    }

}
