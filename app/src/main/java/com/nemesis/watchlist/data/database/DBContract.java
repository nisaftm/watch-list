package com.nemesis.watchlist.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

public final class DBContract {
    public static final String AUTHORITY = "com.nemesis.watchlist";
    private static final String SCHEME = "content";
    public static final String TABLE_NAME = "movies";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    private DBContract(){}

    public static final class MoviesColumns implements BaseColumns{
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String ORIGINALTITLE = "originalTitle";
        public static final String VOTEAVERAGE = "voteAverage";
        public static final String OVERVIEW = "overview";
        public static final String RELEASEDATE = "releaseDate";
        public static final String POSTERPATH = "poster_path";
        public static final String CATEGORY = "category";


    }

}
