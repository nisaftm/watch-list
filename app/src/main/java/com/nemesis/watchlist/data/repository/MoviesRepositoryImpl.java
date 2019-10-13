package com.nemesis.watchlist.data.repository;

import com.nemesis.watchlist.api.ApiRepository;
import com.nemesis.watchlist.data.model.ResponseMovies;

import io.reactivex.Observable;
import retrofit2.Response;

import static com.nemesis.watchlist.BuildConfig.urlBase;

public class MoviesRepositoryImpl implements MoviesRepository{
    @Override
    public Observable<Response<ResponseMovies>> getMovies(String apiKey, String language) {

        return ApiRepository.doRequest(urlBase).getMovies(apiKey, language);
    }
}
