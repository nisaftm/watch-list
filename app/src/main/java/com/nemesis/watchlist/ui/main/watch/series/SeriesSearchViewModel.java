package com.nemesis.watchlist.ui.main.watch.series;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.nemesis.watchlist.R;
import com.nemesis.watchlist.WatchList;
import com.nemesis.watchlist.data.model.ResponseError;
import com.nemesis.watchlist.data.model.ResponseSeries;
import com.nemesis.watchlist.data.model.ResultsSeries;
import com.nemesis.watchlist.data.repository.SeriesCariRepositoryImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SeriesSearchViewModel extends ViewModel {
    private SeriesCariRepositoryImpl seriesCariRepoImpl;
    MutableLiveData<List<ResultsSeries>> seriesLiveData = new MutableLiveData<>();
    MutableLiveData<ResponseError> seriesError = new MutableLiveData<>();

    public SeriesSearchViewModel(){
        seriesCariRepoImpl = new SeriesCariRepositoryImpl();
    }

    void getSeriesSearch(String language, String query) {
        seriesCariRepoImpl.getSeries(language, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseSeries>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseSeries> response) {
                        if (response.body() != null && response.isSuccessful()){
                            seriesLiveData.postValue(response.body().getResults());
                        }else {
                            ResponseBody resBody = response.errorBody();

                            if (resBody != null){
                                Log.e(TAG, "onNext: "+new Gson().toJson(resBody));
                                String sJson = null;
                                try {
                                    sJson = resBody.string();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                JSONObject jsonObject = null;
                                try {
                                    if (sJson != null) {
                                        jsonObject = new JSONObject(sJson);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ResponseError resError;
                                if (jsonObject != null) {
                                    resError = new Gson().fromJson(jsonObject.toString(), ResponseError.class);
                                    seriesError.postValue(resError);
                                }

                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ResponseError resError = new ResponseError();
                        resError.setSuccess(false);
                        resError.setStatusMessage(WatchList.getContext().getString(R.string.koneksi_failed));
                        resError.setStatusCode(0);
                        seriesError.postValue(resError);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /*void getSeriesSearch(String language, String query) {
        seriesCariRepoImpl.getSeries(language, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseMovies>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseMovies> response) {
                        if (response.body() != null && response.isSuccessful()){
                            seriesLiveData.postValue(response.body().getResults());
                        }else {
                            ResponseBody resBody = response.errorBody();

                            if (resBody != null){
                                Log.e(TAG, "onNext: "+new Gson().toJson(resBody));
                                String sJson = null;
                                try {
                                    sJson = resBody.string();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                JSONObject jsonObject = null;
                                try {
                                    if (sJson != null) {
                                        jsonObject = new JSONObject(sJson);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ResponseError resError;
                                if (jsonObject != null) {
                                    resError = new Gson().fromJson(jsonObject.toString(), ResponseError.class);
                                    seriesError.postValue(resError);
                                }

                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ResponseError resError = new ResponseError();
                        resError.setSuccess(false);
                        resError.setStatusMessage(WatchList.getContext().getString(R.string.koneksi_failed));
                        resError.setStatusCode(0);
                        seriesError.postValue(resError);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }*/
}
