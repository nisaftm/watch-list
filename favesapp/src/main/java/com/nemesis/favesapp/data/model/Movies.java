package com.nemesis.favesapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {


    private int id;
    private String title;
    private String originalTitle;
    private String voteAverage;
    private String overview;
    private String releaseDate;
    private String poster_path;
    private int category;

    public Movies(int id, String title, String originalTitle, String voteAverage, String overview, String releaseDate, String poster_path, int category) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.poster_path = poster_path;
        this.category = category;
    }

    public Movies() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public static Creator<Movies> getCREATOR() {
        return CREATOR;
    }

    public Movies(Parcel in) {
        id = in.readInt();
        title = in.readString();
        originalTitle = in.readString();
        voteAverage = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        poster_path = in.readString();
        category = in.readInt();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(originalTitle);
        parcel.writeString(voteAverage);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(poster_path);
        parcel.writeInt(category);
    }
}

