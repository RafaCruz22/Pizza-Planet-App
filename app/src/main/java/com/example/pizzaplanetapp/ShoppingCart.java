package com.example.pizzaplanetapp;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingCart extends AppCompatActivity {

    private static final String TAG = "ShoppingCart";

    private RecyclerView recyclerView;
    private ArrayList<CartItem> cartData;
    private ShoppingCartAdapter shoppingAdapter;
    private FloatingActionButton completeOrder;

    DatabaseReference database;

    private int totalPrice;
    private float itemCount = 0;

    private int grindColumnCount = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "inside of onCreate");
        setContentView(R.layout.cart_shopping);

        recyclerView = findViewById(R.id.recyclerCartView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, grindColumnCount));

        //Reference to firebase database starting at pizza node
        database = FirebaseDatabase.getInstance().getReference("cart");

        // initialize the array that will be used to hold the cart items
        cartData = new ArrayList<>();
        shoppingAdapter = new ShoppingCartAdapter(this, cartData);
        recyclerView.setAdapter(shoppingAdapter);

        loadData();

        //takes user to order summary and resets values and cart
        completeOrder = findViewById(R.id.completeOrderFAB);
        completeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "inside of onCreate -> onClick FAB");

                database.removeValue();//removes cart from database
                Menu.resetCart();//set cart to false **removed above
                MenuItemAdapter.resetCounter();//reset item


                //starts the bill summary aka orderComplete
                Intent completeOrderIntent = new Intent(getApplicationContext(),OrderComplete.class);
                completeOrderIntent.putExtra("item count",itemCount);
                startActivity(completeOrderIntent);
                finish();

                Log.d(TAG, "end of onCreate -> onClick FAB");
            }
        });

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        Log.d(TAG, "end of onCreate");

    }

    // initializeData with firebase
    private void loadData() {
        Log.d(TAG, "inside of loadData");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    itemCount++;//keeps track of the number of items in cart
                    CartItem item = dataSnapshot.getValue(CartItem.class);

                    //keeps track of total price of cart
                    if (item.getPrice() != null) {
                        totalPrice += Float.valueOf(item.getPrice());
                    }
                    cartData.add(item);
                }
                shoppingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d(TAG, "end of loadData");
    }

}
