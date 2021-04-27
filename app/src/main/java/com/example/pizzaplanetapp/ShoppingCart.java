package com.example.pizzaplanetapp;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShoppingCart extends AppCompatActivity {

    private static final String TAG = "ShoppingCart";

    private ArrayList<ShoppingCart> shoppingCartData; //might hold the items sent to cart
    private FloatingActionButton completeOrder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "inside of onCreate");
        setContentView(R.layout.cart_shopping);


        completeOrder = findViewById(R.id.completeOrderFAB);
        completeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "inside of ReturnToMenu");
                Intent completeOrderIntent = new Intent(getApplicationContext(),OrderComplete.class);
                startActivity(completeOrderIntent);
                finish();
                Log.d(TAG, "end of ReturnToMenu");
            }
        });



        Log.d(TAG, "end of onCreate");

    }




}
