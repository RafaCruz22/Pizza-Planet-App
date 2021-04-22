package com.example.pizzaplanetapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PizzaFragment extends Fragment {

    public static final String TAG = "PizzaFragment";
    protected static final String PIZZA_KEY_NUM ="pizza_key";
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
    private RecyclerView mRecyclerView;

    public PizzaFragment(){
        //required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);
        // Inflate the layout for this fragment
        if(savedInstanceState !=null){
         //   myNum = savedInstanceState.getInt(KEY_TAB1_NUM,99);
        }
        return inflater.inflate(R.layout.pizza_fragment, container, false);

/*        View rootView = inflater.inflate(R.layout.pizza_fragment, container,false);

        // Initialize the RecyclerView.
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        // Initialize the ArrayList that will contain the data.
        //MealItem = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new MealItemAdapter(this, MealItem);
        mRecyclerView.setAdapter(mAdapter);*/

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //gain references to views
        tv1 = getActivity().findViewById(R.id.pizza_item1);
        tv2 = getActivity().findViewById(R.id.pizza_item2);
        tv3 = getActivity().findViewById(R.id.pizza_item3);
        tv4 = getActivity().findViewById(R.id.pizza_item4);
        tv5 = getActivity().findViewById(R.id.pizza_item5);
        tv6 = getActivity().findViewById(R.id.pizza_item6);
        tv7 = getActivity().findViewById(R.id.pizza_item7);
        tv8 = getActivity().findViewById(R.id.pizza_item8);



    }



}
