package com.nemesis.watchlist.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.nemesis.watchlist.R;
import com.nemesis.watchlist.WatchList;
import com.nemesis.watchlist.api.ApiRepository;
import com.nemesis.watchlist.data.model.NotificationItem;
import com.nemesis.watchlist.data.model.ResponseError;
import com.nemesis.watchlist.data.model.ResponseMovies;
import com.nemesis.watchlist.ui.release.ReleaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.nemesis.watchlist.BuildConfig.apiKey;
import static com.nemesis.watchlist.BuildConfig.urlBase;

public class ReleaseReminder extends BroadcastReceiver {

    private static int NOTIFICATION_REQUEST_CODE = 200;
    private int ID_REPEATING = 10;
    private int ID_NOTIF = 0;
    private List<NotificationItem> stackNotif = new ArrayList<>();
    private int MAX_NOTIFICATION;
    private String GROUP_KEY_MOVIES = "group_key_movies";

    @Override
    public void onReceive(Context context, Intent intent) {

        getRelease();
    }

    private void getRelease() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String tanggal = sdf.format(calendar.getTime());


        Call<ResponseMovies> call = ApiRepository.doRequest(urlBase).getMovieRelease(apiKey, tanggal, tanggal);
        call.enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
                if (response.isSuccessful() && response.body() != null){
                    String sender = WatchList.getContext().getString(R.string.app_name);
                    int size = response.body().getResults().size();
                    MAX_NOTIFICATION = size;
                    for (int i=0; i<size; i++){
                        String title = response.body().getResults().get(i).getOriginalTitle();
                        stackNotif.add(new NotificationItem(ID_NOTIF, sender, title));
                        showNotif();
                        ID_NOTIF = i;
                    }
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
                            Log.e(TAG, "onResponse: "+resError.getStatusMessage());
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseMovies> call, Throwable t) {

            }
        });

        /*MutableLiveData<List<ResultsMovies>> resMovies = new MutableLiveData<>();

        releaseRepository.getMovieRelease(tanggal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseMovies>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseMovies> response) {
                        if (response.isSuccessful() && response.body() != null){
                            resMovies.postValue(response.body().getResults());


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
                                    releaseError.postValue(resError);
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
                        releaseError.postValue(resError);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        if (resMovies.getValue() != null){
            arrayList.addAll(resMovies.getValue());
            Log.e(TAG, "getRelease");
            setNotif();

        }*/

    }


    private void showNotif() {
        Log.e(TAG, "showNotif");
        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "ReleaseReminder channel";

        NotificationManager notificationManager = (NotificationManager) WatchList.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap bitmap = BitmapFactory.decodeResource(WatchList.getContext().getResources(), R.drawable.ic_email_black_24dp);
        Intent intent = new Intent(WatchList.getContext(), ReleaseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(WatchList.getContext(), NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;

        if (ID_NOTIF < MAX_NOTIFICATION){
            builder = new NotificationCompat.Builder(WatchList.getContext(), CHANNEL_ID)
                    .setContentTitle(stackNotif.get(ID_NOTIF).getSender())
                    .setContentText(stackNotif.get(ID_NOTIF).getMessage())
                    .setSmallIcon(R.drawable.ic_email_black_24dp)
                    .setLargeIcon(bitmap)
                    .setGroup(GROUP_KEY_MOVIES)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        }else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .addLine("New Release from "+stackNotif.get(ID_NOTIF).getSender())
                    .addLine("New Release from "+stackNotif.get(ID_NOTIF-1).getSender())
                    .setBigContentTitle(ID_NOTIF+ " new release")
                    .setSummaryText("mail");
            builder = new NotificationCompat.Builder(WatchList.getContext(), CHANNEL_ID)
                    .setContentTitle(ID_NOTIF+ " new release")
                    .setContentTitle("mail")
                    .setSmallIcon(R.drawable.ic_email_black_24dp)
                    .setGroup(GROUP_KEY_MOVIES)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);

            if (notificationManager != null){
                notificationManager.createNotificationChannel(notificationChannel);
            }

        }

        Notification notification = builder.build();

        if (notificationManager != null){
            notificationManager.notify(ID_NOTIF, notification);
        }

    }


    public void setRepeatingRelease(Context context, String time) {
        if (isDateInvalid(time)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);
        //intent.putExtra(EXTRA_MESSAGE, message);

        String[] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, WatchList.getContext().getString(R.string.text_release_reminder_act), Toast.LENGTH_SHORT).show();
    }

    private boolean isDateInvalid(String date) {
        try{
            DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return false;
        }catch (ParseException e){
            return true;
        }
    }

    public void cancelRelease(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }

        Toast.makeText(context, context.getString(R.string.text_release_deactive), Toast.LENGTH_SHORT).show();
    }
}
