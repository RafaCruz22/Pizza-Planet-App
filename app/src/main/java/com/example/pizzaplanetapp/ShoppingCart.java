package com.example.pizzaplanetapp;

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
    private ImageView iv_emptyCart;
    private TextView tv_emptyCart;
    private Button btn_returnToMain;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.cart_shopping_empty);
        Log.d(TAG, "inside of onCreate");

        //if there isn't an order show empty cart layout otherwise show users cart
        if (shoppingCartData == null) {
            Log.d(TAG, "cart is empty");
            setContentView(R.layout.cart_shopping_empty);
            getEmptyCartReference();

        } else {
            Log.d(TAG, "cart has an item");
            setContentView(R.layout.cart_shopping);

            //Fab allows user to complete there order
            FloatingActionButton fab = findViewById(R.id.completeOrderFAB);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "inside of onClick for FAB");
                    Intent completeOrderIntent = new Intent(ShoppingCart.this, OrderComplete.class);
                    startActivity(completeOrderIntent);
                    Toast.makeText(ShoppingCart.this, "You're order is complete.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "end of onClick for FAB");

                }
            });


            Log.d(TAG, "end of onCreate");
        }
    }



    // gets all the reference for the empty cart layout
    private void getEmptyCartReference(){
        Log.d(TAG, "inside of getEmptyCartReference");
        iv_emptyCart = findViewById(R.id.emptyCartLogo);
        tv_emptyCart = findViewById(R.id.tv_emptyCart);
        btn_returnToMain = findViewById(R.id.btn_return_to_menu);
        Log.d(TAG, "end of getEmptyCartReference");
    }

    public void ReturnToMenu(View view) {
        Log.d(TAG, "inside of ReturnToMenu");
        Intent returnToMenuIntent = new Intent(getApplicationContext(),Menu.class);
        startActivity(returnToMenuIntent);
        Log.d(TAG, "end of ReturnToMenu");
    }
}
