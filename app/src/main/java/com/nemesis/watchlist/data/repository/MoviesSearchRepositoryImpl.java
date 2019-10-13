package com.nemesis.watchlist.data.repository;

import com.nemesis.watchlist.api.ApiRepository;
import com.nemesis.watchlist.data.model.ResponseMovies;

import io.reactivex.Observable;
import retrofit2.Response;

import static com.nemesis.watchlist.BuildConfig.apiKey;
import static com.nemesis.watchlist.BuildConfig.urlBase;

public class MoviesSearchRepositoryImpl implements MoviesSearchRepository{
    @Override
    public Observable<Response<ResponseMovies>> getMovies(String language, String query) {
        return ApiRepository.doRequest(urlBase).getMoviesSearch(apiKey, language, query);
    }
}
