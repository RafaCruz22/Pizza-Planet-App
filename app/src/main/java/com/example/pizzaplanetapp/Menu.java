package com.example.pizzaplanetapp;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    public static final String TAG = "Menu";
    private TabLayout tabLayout;
    private TabLayout.Tab tab1, tab2, tab3;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private FloatingActionButton fabButton;
    private RecyclerView mRecyclerView;
    private ArrayList<MenuItem> MenuItem;
    private MenuItemAdapter mAdapter;
    private static Boolean cart = false;//cart is always empty unless user puts something in cart

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Log.d(TAG, "inside of the onCreate Method");

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);
        fabButton = findViewById(R.id.floatingActionButton);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cart == false){
                    Log.d(TAG, "inside of onClick for empty cart FAB");
                    Intent shoppingIntent = new Intent(Menu.this, EmptyCart.class);
                    startActivity(shoppingIntent);
                    Log.d(TAG, "end of onClick for empty cart FAB");
                } else {
                    Log.d(TAG, "inside of onClick for full cart FAB");
                    Intent shoppingIntent = new Intent(Menu.this, ShoppingCart.class);
                    startActivity(shoppingIntent);
                    Log.d(TAG, "end of onClick for full cart FAB");

                }
            }
        });


        tabLayout.addTab(tabLayout.newTab().setText("Pizza"));
        tabLayout.addTab(tabLayout.newTab().setText("Appetizers"));
        tabLayout.addTab(tabLayout.newTab().setText("Beverages"));

        pagerAdapter = new com.example.pizzaplanetapp.PagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int currPos = tab.getPosition();
                Log.d(TAG, "Inside onTabSelection: tab position = " + currPos);
                viewPager.setCurrentItem(currPos);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    //resets the cart to empty
    public static void resetCart() {
        cart = false;
    }

    //sets carts to full
    public static void cartFull(){
        cart = true;
    }
}
