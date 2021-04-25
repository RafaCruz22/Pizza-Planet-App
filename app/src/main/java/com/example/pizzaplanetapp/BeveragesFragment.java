package com.example.pizzaplanetapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BeveragesFragment extends Fragment {

    public static final String TAG = "BevFragment";
    private TextView tv_water, tv_sprite, tv_coke, tv_pepsi, tv_icedTea;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.beverages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        //potentially add long click listeners to add to cart, conferring with team
        //tv_water = getActivity().findViewById(R.id.bev_water);
    }
}
