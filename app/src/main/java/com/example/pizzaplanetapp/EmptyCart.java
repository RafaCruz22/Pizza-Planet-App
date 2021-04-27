package com.example.pizzaplanetapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EmptyCart extends AppCompatActivity {

    private ImageView iv_emptyCart;
    private TextView tv_emptyCart;
    private Button btn_returnToMain;

    private static final String TAG = "EmptyCart";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_shopping_empty);
        getEmptyCartReference();
    }

    // gets all the reference for the empty cart layout
    private void getEmptyCartReference() {
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
        finish();
        Log.d(TAG, "end of ReturnToMenu");
    }
}
