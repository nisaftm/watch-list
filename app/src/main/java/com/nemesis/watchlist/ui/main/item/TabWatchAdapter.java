package com.nemesis.watchlist.ui.main.item;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabWatchAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fraglist = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();


    public TabWatchAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fraglist.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public int getCount() {
        return fraglist.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fraglist.add(fragment);
        titleList.add(title);
    }
}
