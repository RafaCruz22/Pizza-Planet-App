package com.example.pizzaplanetapp;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public PagerAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PizzaFragment();
            case 1:
                return new AppetizersFragment();
            case 2:
                return new BeveragesFragment();
            default:
            return null;
        }
    }

    @Override
    public int getCount() {
        return this.numOfTabs;
    }
}
