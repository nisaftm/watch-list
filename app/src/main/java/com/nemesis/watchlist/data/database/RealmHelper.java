package com.nemesis.watchlist.data.database;

import android.widget.Toast;

import com.nemesis.watchlist.WatchList;
import com.nemesis.watchlist.data.model.Movies;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {
    private Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void saveFave(Movies movies) {
        realm.executeTransaction(realm -> {

            Movies item = realm.copyToRealm(movies);
            Toast.makeText(WatchList.getContext(), item.getOriginalTitle() + " ditambahkan", Toast.LENGTH_SHORT).show();

        });

    }

    public List<Movies> getMovies(int category) {
        return realm.where(Movies.class).equalTo("category", category)
                .findAll();
    }

    public void deleteFave(int id) {
        RealmResults<Movies> movies = realm.where(Movies.class).equalTo("id", id).findAll();
        realm.executeTransaction(realm -> movies.deleteFromRealm(0));

    }

    public boolean checkFave(int id) {
        RealmResults<Movies> results = realm.where(Movies.class).equalTo("id", id).findAll();
        return results.size() != 0;
    }

    public List<Movies> getSearchMovies(String query, int category) {
        return realm.where(Movies.class)
                .equalTo("category", category)
                .contains("originalTitle", query, Case.INSENSITIVE)
                .findAll();

    }

    public List<Movies> getFaves() {
        return realm.where(Movies.class).findAll();
    }
}
