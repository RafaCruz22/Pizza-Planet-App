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
    private AppetizerAdapter adapter;
    private ArrayList<AppetizerItem> appetizerItem;

    DatabaseReference database;

    public AppetizersFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        View rootView = inflater.inflate(R.layout.appetizers,container,false);

        recyclerViewAppetizer = (RecyclerView) rootView.findViewById(R.id.recyclerview_appetizers);


        //Reference to firebase database starting at pizza node
        database = FirebaseDatabase.getInstance().getReference("appetizers");

        recyclerViewAppetizer.setLayoutManager(new GridLayoutManager(getContext(),gridColumnCount));

        appetizerItem = new ArrayList<>();

        adapter = new AppetizerAdapter(getContext(),appetizerItem);

        recyclerViewAppetizer.setAdapter(adapter);

        initializeData();

        adapter.notifyDataSetChanged();

        return rootView;
    }

    // initializeData locally using strings.
    public void initializeData() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                appetizerItem.clear();//assures no duplicates are introduced
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    AppetizerItem item = dataSnapshot.getValue(AppetizerItem.class);
                    appetizerItem.add(item);
                    Log.d("items", "" + appetizerItem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
