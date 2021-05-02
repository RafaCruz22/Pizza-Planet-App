package com.example.pizzaplanetapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BeveragesFragment extends Fragment {

    public static final String TAG = "BevFragment";
    private TextView tv_water, tv_sprite, tv_coke, tv_pepsi, tv_icedTea;
    private FloatingActionButton fabButton;
    private RecyclerView mRecyclerView;
    private ArrayList<MenuItem> MenuItem;
    private MenuItemAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.beverages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        //potentially add long click listeners to add to cart, conferring with team
//        tv_water = getActivity().findViewById(R.id.bev_water);
    }
}
