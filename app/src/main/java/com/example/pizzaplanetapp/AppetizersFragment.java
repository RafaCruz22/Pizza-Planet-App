package com.example.pizzaplanetapp;

import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AppetizersFragment extends Fragment {

    public static final String TAG = "AppetizerFragment";
    private RecyclerView recyclerViewAppetizer;
    private MenuItemAdapter adapter;
    private ArrayList<MenuItem> menuItem;

    DatabaseReference database;

    public AppetizersFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        View rootView = inflater.inflate(R.layout.appetizers,container,false);

        recyclerViewAppetizer = rootView.findViewById(R.id.recyclerview_appetizers);


        //Reference to firebase database starting at pizza node
        database = FirebaseDatabase.getInstance().getReference("pizza");

        recyclerViewAppetizer.setLayoutManager(new GridLayoutManager(getContext(),gridColumnCount));

        menuItem = new ArrayList<>();
        adapter = new MenuItemAdapter(getContext(),menuItem);

        recyclerViewAppetizer.setAdapter(adapter);

        initializeData();

        return rootView;
    }




    // initializeData locally using strings.

    public void initializeData() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    MenuItem item = dataSnapshot.getValue(MenuItem.class);
                    menuItem.add(item);
                    Log.d("items", "" + menuItem);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
