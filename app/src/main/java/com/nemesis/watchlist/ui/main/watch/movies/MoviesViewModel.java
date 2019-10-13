package com.nemesis.watchlist.ui.main.watch.movies;


import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.nemesis.watchlist.R;
import com.nemesis.watchlist.data.model.ResponseError;
import com.nemesis.watchlist.data.model.ResponseMovies;
import com.nemesis.watchlist.data.model.ResultsMovies;
import com.nemesis.watchlist.data.repository.MoviesRepositoryImpl;

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
import static com.nemesis.watchlist.BuildConfig.apiKey;

public class MoviesViewModel extends ViewModel {

    MutableLiveData<List<ResultsMovies>> moviesLiveData = new MutableLiveData<>();
    MutableLiveData<ResponseError> moviesError = new MutableLiveData<>();
    private MoviesRepositoryImpl moviesRepoImpl;

    public MoviesViewModel(){
        moviesRepoImpl = new MoviesRepositoryImpl();
    }

    void getMovies(String language, Activity activity) {
        moviesRepoImpl.getMovies(apiKey, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseMovies>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseMovies> response) {
                        if (response.body() != null && response.isSuccessful()){
                            moviesLiveData.postValue(response.body().getResults());

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
                                    moviesError.postValue(resError);
                                }

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ResponseError resError = new ResponseError();
                        resError.setSuccess(false);
                        resError.setStatusMessage(activity.getString(R.string.koneksi_failed));
                        resError.setStatusCode(0);
                        moviesError.postValue(resError);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
