package com.nemesis.watchlist.data.repository;

import com.nemesis.watchlist.data.model.ResponseSeries;

import io.reactivex.Observable;
import retrofit2.Response;

interface SeriesRepository {
    Observable<Response<ResponseSeries>> getSeries(String language);
}
