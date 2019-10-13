package com.nemesis.watchlist.api;

import com.nemesis.watchlist.data.model.ResponseMovies;
import com.nemesis.watchlist.data.model.ResponseSeries;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Iface {

    @GET("discover/movie")
    Observable<Response<ResponseMovies>> getMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language);

    @GET("discover/tv")
    Observable<Response<ResponseSeries>> getSeries(
            @Query("api_key") String apiKey,
            @Query("language") String language);


    @GET("search/movie")
    Observable<Response<ResponseMovies>> getMoviesSearch(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query);

    @GET("search/tv")
    Observable<Response<ResponseSeries>> getSeriessCari(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query);

    @GET("discover/movie")
    Call<ResponseMovies> getMovieRelease(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String tanggal,
            @Query("primary_release_date.lte") String tanggal1);

    @GET("discover/movie")
    Observable<Response<ResponseMovies>> getRelease(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String tanggal,
            @Query("primary_release_date.lte") String tanggal1);

}
