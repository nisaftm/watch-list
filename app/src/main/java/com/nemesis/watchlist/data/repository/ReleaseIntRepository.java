package com.nemesis.watchlist.data.repository;

import com.nemesis.watchlist.data.model.ResponseMovies;

import io.reactivex.Observable;
import retrofit2.Response;

interface ReleaseIntRepository {
    Observable<Response<ResponseMovies>> getReleaseMovies(String tanggal);
}
