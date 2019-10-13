package com.nemesis.watchlist.data.repository;

import com.nemesis.watchlist.api.ApiRepository;
import com.nemesis.watchlist.data.model.ResponseMovies;

import io.reactivex.Observable;
import retrofit2.Response;

import static com.nemesis.watchlist.BuildConfig.apiKey;
import static com.nemesis.watchlist.BuildConfig.urlBase;

public class ReleaseRepositoryImpl implements ReleaseIntRepository{
    @Override
    public Observable<Response<ResponseMovies>> getReleaseMovies(String tanggal) {

        return ApiRepository.doRequest(urlBase).getRelease(apiKey, tanggal, tanggal);
    }
}
