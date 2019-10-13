package com.nemesis.watchlist.data.repository;

import com.nemesis.watchlist.data.model.ResponseSeries;

import io.reactivex.Observable;
import retrofit2.Response;

interface SeriesCariRepository {
    Observable<Response<ResponseSeries>> getSeries(String language, String query);
}
