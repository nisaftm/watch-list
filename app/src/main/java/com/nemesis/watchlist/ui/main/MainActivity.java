package com.nemesis.watchlist.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nemesis.watchlist.R;
import com.nemesis.watchlist.ui.main.fave.FaveFragment;
import com.nemesis.watchlist.ui.main.watch.WatchFragment;
import com.nemesis.watchlist.ui.reminder.RemainderActivity;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment1 = new WatchFragment();
    private Fragment fragment2 = new FaveFragment();
    private Fragment fragment;
    private final FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.title_watch);
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            fragment = fragment1;
            fragmentManager.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
            fragmentManager.beginTransaction().add(R.id.container, fragment1, "1").commit();
            Log.e("tag", "onNavigationItemSelected: "+fragment);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.navigation_movies:
                            if (getSupportActionBar() != null){
                                getSupportActionBar().setTitle(R.string.title_movies);
                            }
                            if (fragment != null){
                                fragmentManager.beginTransaction().hide(fragment).show(fragment1).commit();
                                fragment = fragment1;
                            }else {
                                fragment1 = fragmentManager.findFragmentByTag("1");
                                fragment2 = fragmentManager.findFragmentByTag("2");
                                fragmentManager.beginTransaction().hide(fragment2).show(fragment1).commit();
                                fragment = fragment1;
                            }
                            Log.e("tag", "onNavigationItemSelected: "+fragment);

                            return true;

                        case R.id.navigation_series:{
                            if (getSupportActionBar() != null){
                                getSupportActionBar().setTitle(R.string.title_likes);
                            }
                            if (fragment != null){
                                fragmentManager.beginTransaction().hide(fragment).show(fragment2).commit();
                                fragment = fragment2;
                            }else {
                                fragment1 = fragmentManager.findFragmentByTag("1");
                                fragment2 = fragmentManager.findFragmentByTag("2");
                                fragmentManager.beginTransaction().hide(fragment1).show(fragment2).commit();
                                fragment = fragment1;
                            }
                            Log.e("tag", "onNavigationItemSelected: "+fragment);
                            return true;
                        }
                    }
                    return false;
                }


            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }else if (id == R.id.action_settings){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);

        }else if (id == R.id.action_reminder){
            Intent intent = new Intent(this, RemainderActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
