package com.example.pizzaplanetapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
