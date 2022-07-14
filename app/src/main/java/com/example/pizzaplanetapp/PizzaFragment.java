package com.example.pizzaplanetapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PizzaFragment extends Fragment {

    public static final String TAG = "PizzaFragment";
    protected static final String PIZZA_KEY_NUM = "pizza_key";
    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8;
    private FloatingActionButton fabButton;
    private RecyclerView mRecyclerView;
    private ArrayList<MenuItem> MenuItem;
    private MenuItemAdapter mAdapter;

    DatabaseReference database;


    //variables to hold the pizzas. need an array?


    public PizzaFragment() {
        //required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        // Inflate the layout for this fragment
        if (savedInstanceState != null) {
            //   myNum = savedInstanceState.getInt(KEY_TAB1_NUM,99);
        }


        View rootView = inflater.inflate(R.layout.pizza_fragment, container, false);

        // Initialize the RecyclerView.
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        //Reference to firebase database starting at pizza node
        database = FirebaseDatabase.getInstance().getReference("pizza");

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), gridColumnCount));

        // Initialize the ArrayList that will contain the data.
        MenuItem = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new MenuItemAdapter(getContext(), MenuItem);
        mRecyclerView.setAdapter(mAdapter);

        // initializeData with firebase
        initializeData();

        mAdapter.notifyDataSetChanged();
        return rootView;

    }


    // initializeData with firebase
    public void initializeData() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                MenuItem.clear();//assures no duplicates are introduced
                for (DataSnapshot child : snapshot.getChildren()) {

                    MenuItem item = child.getValue(MenuItem.class);
                    MenuItem.add(item);
                    Log.d("items", "" + MenuItem);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


//    // initializeData locally using strings.
//
//    public void initializeData() {
//        TypedArray mealImageResources = getResources().obtainTypedArray(R.array.meal_images);
//
//        // Get the resources from the XML file.
//        String[] menuList = getResources()
//                .getStringArray(R.array.meal_titles);
//        String[] mealInfo = getResources()
//                .getStringArray(R.array.meal_info);
//        String[] mealDetail = getResources()
//                .getStringArray(R.array.meal_detail);
//
//
//        // Clear the existing data (to avoid duplication).
//        MenuItem.clear();
//
//        // Create the ArrayList of Menu objects with titles and
//        // information about each item.
//        for (int i = 0; i < menuList.length; i++) {
//            MenuItem.add(new MenuItem(menuList[i], mealInfo[i], mealDetail[i],
//                    mealImageResources.getResourceId(i, 0)));
//        }
//        //Clean up the data in the typed array once you have created the MenuItem ArrayList
//        mealImageResources.recycle();
//
//        // Notify the adapter of the change.
//        mAdapter.notifyDataSetChanged();
//
//
//    }

}
