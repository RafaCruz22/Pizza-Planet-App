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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ShoppingCart extends AppCompatActivity {

    private static final String TAG = "ShoppingCart";

    private RecyclerView recyclerView;
    private ArrayList<CartItem> cartData;
    private ShoppingCartAdapter shoppingAdapter;
    private FloatingActionButton completeOrder;

    DatabaseReference database;

    private int totalPrice;
    private float itemCount = 0;

    ItemTouchHelper itemTouchHelper;// for drag and swipe

    private int girdColumnCount = 1;

    private int dragDirections = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
    private int swipeDirection = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "inside of onCreate");
        setContentView(R.layout.cart_shopping);


        //disable swiping for multiple columns
        if(girdColumnCount > 1){swipeDirection = 0; }


        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(dragDirections,swipeDirection) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(cartData,from,to);
                shoppingAdapter.notifyItemMoved(from,to);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d("before remove swip", " item: " + (cartData.get(viewHolder.getAdapterPosition())).getTitle());
                cartData.remove(viewHolder.getAdapterPosition());//using the view holder you can get it's position
                Log.d("after remove swip", " "+ cartData);

                shoppingAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        });

        recyclerView = findViewById(R.id.recyclerCartView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, girdColumnCount));

        itemTouchHelper.attachToRecyclerView(recyclerView);

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
