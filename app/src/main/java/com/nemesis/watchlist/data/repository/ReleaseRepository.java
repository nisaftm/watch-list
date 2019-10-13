package com.nemesis.watchlist.data.repository;

import com.nemesis.watchlist.data.model.ResponseMovies;

import retrofit2.Call;

interface ReleaseRepository {
    Call<ResponseMovies> getMovieRelease(String tanggal);
}
