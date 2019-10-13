package com.nemesis.watchlist.data.repository;

import com.nemesis.watchlist.api.ApiRepository;
import com.nemesis.watchlist.data.model.ResponseSeries;

import io.reactivex.Observable;
import retrofit2.Response;

import static com.nemesis.watchlist.BuildConfig.apiKey;
import static com.nemesis.watchlist.BuildConfig.urlBase;

public class SeriesCariRepositoryImpl implements SeriesCariRepository{
    @Override
    public Observable<Response<ResponseSeries>> getSeries(String language, String query) {
        return ApiRepository.doRequest(urlBase).getSeriessCari(apiKey, language, query);
    }
    /*@Override
    public Observable<Response<ResponseMovies>> getSeries(String language, String query) {
        return ApiRepository.doRequest(urlBase).getSeriessCari(apiKey, language, query);
    }*/
}
