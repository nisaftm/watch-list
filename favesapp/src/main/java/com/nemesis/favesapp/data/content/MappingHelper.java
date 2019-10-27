package com.nemesis.favesapp.data.content;

import android.database.Cursor;

import com.nemesis.favesapp.data.model.Movies;

import java.util.ArrayList;

import static com.nemesis.favesapp.data.content.DBContract.MoviesColumns.CATEGORY;
import static com.nemesis.favesapp.data.content.DBContract.MoviesColumns.ID;
import static com.nemesis.favesapp.data.content.DBContract.MoviesColumns.ORIGINALTITLE;
import static com.nemesis.favesapp.data.content.DBContract.MoviesColumns.OVERVIEW;
import static com.nemesis.favesapp.data.content.DBContract.MoviesColumns.POSTERPATH;
import static com.nemesis.favesapp.data.content.DBContract.MoviesColumns.RELEASEDATE;
import static com.nemesis.favesapp.data.content.DBContract.MoviesColumns.TITLE;
import static com.nemesis.favesapp.data.content.DBContract.MoviesColumns.VOTEAVERAGE;

public class MappingHelper {

    public static ArrayList<Movies> cursorList(Cursor cursor){
        ArrayList<Movies> movies = new ArrayList<>();

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String originalTitle = cursor.getString(cursor.getColumnIndexOrThrow(ORIGINALTITLE));
            String voteAverage = cursor.getString(cursor.getColumnIndexOrThrow(VOTEAVERAGE));
            String overview= cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
            String releaseDate= cursor.getString(cursor.getColumnIndexOrThrow(RELEASEDATE));
            String posterPath= cursor.getString(cursor.getColumnIndexOrThrow(POSTERPATH));
            int category = cursor.getInt(cursor.getColumnIndexOrThrow(CATEGORY));
            movies.add(new Movies(id, title, originalTitle, voteAverage,
                    overview, releaseDate, posterPath, category));
        }
        return movies;
    }

    public static Movies cursorObject(Cursor cursor){
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
        String originalTitle = cursor.getString(cursor.getColumnIndexOrThrow(ORIGINALTITLE));
        String voteAverage = cursor.getString(cursor.getColumnIndexOrThrow(VOTEAVERAGE));
        String overview= cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW));
        String releaseDate= cursor.getString(cursor.getColumnIndexOrThrow(RELEASEDATE));
        String posterPath= cursor.getString(cursor.getColumnIndexOrThrow(POSTERPATH));
        int category = cursor.getInt(cursor.getColumnIndexOrThrow(CATEGORY));
        return new Movies(id, title, originalTitle, voteAverage,
                overview, releaseDate, posterPath, category);

    }
}
