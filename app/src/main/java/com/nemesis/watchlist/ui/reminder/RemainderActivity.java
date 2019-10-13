package com.nemesis.watchlist.ui.reminder;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.nemesis.watchlist.R;
import com.nemesis.watchlist.data.prefs.DataPrefs;
import com.nemesis.watchlist.service.ReleaseReminder;
import com.nemesis.watchlist.service.Reminder;

public class RemainderActivity extends AppCompatActivity {

    private Reminder reminder;
    private ReleaseReminder reminderRelease;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        Switch swdaily = findViewById(R.id.swdaily);
        Switch swrelease = findViewById(R.id.swrelease);

        reminder = new Reminder();
        reminderRelease = new ReleaseReminder();

        DataPrefs prefs = new DataPrefs(this);

        boolean dailyChecked = prefs.getboolean(DataPrefs.DAILY_CHECK, false);
        boolean releaseChecked = prefs.getboolean(DataPrefs.RELEASE_CHECK, false);

        swdaily.setChecked(dailyChecked);
        swrelease.setChecked(releaseChecked);

        swdaily.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                String message = getString(R.string.text_message_daily_reminder);
                reminder.setRepeatingReminder(this, "07:00", message);
                prefs.editboolean(DataPrefs.DAILY_CHECK, true);
            }else {
                prefs.editboolean(DataPrefs.DAILY_CHECK, false);
                reminder.cancelReminder(this);
            }
        });

        swrelease.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                prefs.editboolean(DataPrefs.RELEASE_CHECK, true);

                reminderRelease.setRepeatingRelease(this, "11:51");
            }else {
                prefs.editboolean(DataPrefs.RELEASE_CHECK, false);
                reminderRelease.cancelRelease(this);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
