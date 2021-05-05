package com.example.pizzaplanetapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class AppetizersFragment extends Fragment {

    public static final String TAG = "AppetizerFragment";
    private RecyclerView recyclerViewAppetizer;
    private MenuItemAdapter adapter;
    private ArrayList<MenuItem> menuItem;


    public AppetizersFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        View rootView = inflater.inflate(R.layout.appetizers,container,false);

        recyclerViewAppetizer = rootView.findViewById(R.id.recyclerview_appetizers);

        recyclerViewAppetizer.setLayoutManager(new GridLayoutManager(getContext(),gridColumnCount));

        menuItem = new ArrayList<>();
        adapter = new MenuItemAdapter(getContext(),menuItem);

        recyclerViewAppetizer.setAdapter(adapter);

        return rootView;
    }
}
