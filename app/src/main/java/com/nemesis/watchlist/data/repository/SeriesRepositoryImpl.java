package com.nemesis.watchlist.data.repository;

import com.nemesis.watchlist.api.ApiRepository;
import com.nemesis.watchlist.data.model.ResponseSeries;

import io.reactivex.Observable;
import retrofit2.Response;

import static com.nemesis.watchlist.BuildConfig.apiKey;
import static com.nemesis.watchlist.BuildConfig.urlBase;

public class SeriesRepositoryImpl implements SeriesRepository {

    @Override
    public Observable<Response<ResponseSeries>> getSeries(String language) {
        return ApiRepository.doRequest(urlBase).getSeries(apiKey, language);
    }
}