package com.example.pizzaplanetapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class Menu extends AppCompatActivity {

    //change from "PIZZAMENU" to "Menu"
    public static final String TAG = "Menu";
    private TabLayout tabLayout;
    private TabLayout.Tab tab1, tab2, tab3;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Log.d(TAG, "inside of the onCreate Method");

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Pizza"));
        tabLayout.addTab(tabLayout.newTab().setText("Appetizers"));
        tabLayout.addTab(tabLayout.newTab().setText("Beverages"));

        pagerAdapter = new com.example.pizzaplanetapp.PagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int currPos = tab.getPosition();
                Log.d(TAG, "Inside onTabSelection: tab position = "+currPos);
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

        //(RD) added fab to take user to cart when they desire
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "inside of onClick for FAB");
                Intent shoppingIntent = new Intent(getApplicationContext(),ShoppingCart.class);
                startActivity(shoppingIntent);
                Log.d(TAG, "end of onClick for FAB");

            }
        });


    }
}
