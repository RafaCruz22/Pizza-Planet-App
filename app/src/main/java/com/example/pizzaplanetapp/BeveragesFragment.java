package com.example.pizzaplanetapp;

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
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BeveragesFragment extends Fragment {

    public static final String TAG = "BevFragment";
    private Button btn_water, btn_sprite, btn_coke, btn_tea, btn_pepsi;
    private FloatingActionButton fabButton;
    private RecyclerView mRecyclerView;
    private ArrayList<BeveragesItem> beveragesItem;
    private BeveragesAdapter mAdapter;


    DatabaseReference database;

    public BeveragesFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        View rootView = inflater.inflate(R.layout.beverages,container,false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_beverages);

        //Reference to firebase database starting at pizza node
        database = FirebaseDatabase.getInstance().getReference("beverages");

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),gridColumnCount));

        beveragesItem = new ArrayList<>();

        mAdapter = new BeveragesAdapter(getContext(),beveragesItem);

        mRecyclerView.setAdapter(mAdapter);

        initializeData();

        mAdapter.notifyDataSetChanged();

        return rootView;
    }

    // initializeData locally using strings.
    public void initializeData() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                beveragesItem.clear();//assures no duplicates are introduced
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    BeveragesItem item = dataSnapshot.getValue(BeveragesItem.class);
                    beveragesItem.add(item);
                    Log.d("items", "" + beveragesItem);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
