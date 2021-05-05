package com.example.pizzaplanetapp;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

    DatabaseReference database,removeCart;

    private static float totalPrice;
    private int itemCount = 0;
    private TextView textTotalPrice;

    ItemTouchHelper itemTouchHelper;// for drag and swipe

    private int girdColumnCount = 1;

    private int dragDirections = 0;
    private int swipeDirection = ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "inside of onCreate");
        setContentView(R.layout.cart_shopping);


        //disable swiping for multiple columns
        if(girdColumnCount > 1){swipeDirection = 0; }


        recyclerView = findViewById(R.id.recyclerCartView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, girdColumnCount));

        textTotalPrice = findViewById(R.id.totalPriceCart);

        //Reference to firebase database starting at pizza node
        database = FirebaseDatabase.getInstance().getReference("cart");

        // initialize the array that will be used to hold the cart items
        cartData = new ArrayList<>();
        shoppingAdapter = new ShoppingCartAdapter(this, cartData);

        recyclerView.setAdapter(shoppingAdapter);


        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(dragDirections,swipeDirection) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int viewPosition = viewHolder.getAdapterPosition();

                itemCount--;//item count decreases due to removing of item

                totalPrice -=  Float.valueOf(cartData.get(viewPosition).getPrice());

                //gets reference to "cart" node in database and deletes the swiped item from database
                removeCart = FirebaseDatabase.getInstance().getReference("cart");
                removeCart.child("item" + cartData.get(viewPosition).getPosition())
                        .removeValue();

                cartData.remove(viewPosition);//removes swiped item from the cart array

                //if all items are removed from cart set its price to zero
                if(cartData.isEmpty()){
                    textTotalPrice.setText("$000");
                }

                shoppingAdapter.notifyItemRemoved(viewPosition);

            }

        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

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
                Menu.resetCounter();//set counter back to zero


                //starts the bill summary aka orderComplete
                Intent completeOrderIntent = new Intent(getApplicationContext(),OrderComplete.class);
                completeOrderIntent.putExtra("item count",itemCount);
                startActivity(completeOrderIntent);
                finish();

                Log.d(TAG, "end of onCreate -> onClick FAB");
            }
        });

        Log.d(TAG, "end of onCreate");

    }

    // initializeData with firebase
    private void loadData() {
        Log.d(TAG, "inside of loadData");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                cartData.clear();//assures no duplicates are introduced
                resetTotalPrice();//reset price to zero

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    itemCount++;//keeps track of the number of items in cart
                    CartItem item = dataSnapshot.getValue(CartItem.class);

                    //keeps track of total price of cart
                    if (item.getPrice() != null) {
                        totalPrice += Float.valueOf(item.getPrice());

                    }
                    //sets the total of the cart as items are added or removed
                    textTotalPrice.setText("$" + String.format("%.2f", totalPrice));
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

    //if cart doesn't exist in database when menu is onPause then set the cart to empty
    @Override
    protected void onPause() {
        super.onPause();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() == false){
                    Menu.resetCart();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void resetTotalPrice(){ totalPrice = 0;}
}
