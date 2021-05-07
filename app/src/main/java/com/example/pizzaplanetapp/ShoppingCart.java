package com.example.pizzaplanetapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
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
import java.util.UUID;

public class ShoppingCart extends AppCompatActivity {

    private static final String TAG = "ShoppingCart";

    private RecyclerView recyclerView;
    private ArrayList<CartItem> cartData;
    private ShoppingCartAdapter shoppingAdapter;
    private FloatingActionButton completeOrder;

    private static float totalPrice;
    private int itemCount = 0;
    private String orderId = UUID.randomUUID().toString();//generates a pseudo random number
    private static Object cartID;
    private TextView textTotalPrice;

    private final int girdColumnCount = 1;
    private final int dragDirections = 0;
    private final int swipeDirection = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

    private NotificationManager notificationManager;
    private shoppingCartReceiver myReceiver;

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String PRIMARY_CHANNEL_NAME = "Order Complete Notification";
    private static final int IMPORTANCE_LEVEL = NotificationManager.IMPORTANCE_DEFAULT;
    private static final int NOTIFICATION_ID_0 = 0;

    String CUSTOM_ACTION = "com.example.pizzaplanetapp.CARTID";

    DatabaseReference database,removeCart;
    FirebaseDatabase fireBase;//Firebase variable
    ItemTouchHelper itemTouchHelper;// for drag and swipe

    public static void resetTotalPrice(){ totalPrice = 0;}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "inside of onCreate");
        setContentView(R.layout.cart_shopping);

        myReceiver = new shoppingCartReceiver();

        IntentFilter filter = new IntentFilter(CUSTOM_ACTION);
        registerReceiver(myReceiver,filter);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        recyclerView = findViewById(R.id.recyclerCartView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, girdColumnCount));

        textTotalPrice = findViewById(R.id.totalPriceCart);

        //Reference to firebase database starting at pizza node
        database = FirebaseDatabase.getInstance().getReference("carts");

        // initialize the array that will be used to hold the cart items
        cartData = new ArrayList<>();
        shoppingAdapter = new ShoppingCartAdapter(this, cartData);

        recyclerView.setAdapter(shoppingAdapter);


        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(dragDirections,swipeDirection) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            //removes an item from the cart by swiping it left or right
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int viewPosition = viewHolder.getAdapterPosition();

                itemCount--;//item count decreases due to removing of item

                totalPrice -=  Float.parseFloat(cartData.get(viewPosition).getPrice());

                //gets reference to "cart" node in database and deletes the swiped item from database
                removeCart = FirebaseDatabase.getInstance().getReference("carts");
                removeCart.child("cart" + cartID).child("item" + cartData.get(viewPosition).getPosition())
                        .removeValue();

                cartData.remove(viewPosition);//removes swiped item from the cart array

                //if all items are removed from cart set its price to zero
                if(cartData.isEmpty()){
                    textTotalPrice.setText(R.string.deafult_priceText);
                }

                shoppingAdapter.notifyItemRemoved(viewPosition);

            }

        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

        //loads the data from firebase
        loadData();

        //takes user to order summary and resets values and cart
        completeOrder = findViewById(R.id.completeOrderFAB);
        completeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "inside of onCreate -> onClick FAB");

                database.child("cart" + cartID).removeValue();//removes cart from database
                Menu.resetCart();//set cart to false **removed above
                MenuItemAdapter.resetCounter();//reset item
                Menu.resetCounter();//set counter back to zero

                //writes the cart to an order node basically for pizza planet
                commitOrderToDatabase();

                pickUpNotification();//sends a notification to user, order ready for pick up

                //starts the bill summary aka orderComplete
                Intent completeOrderIntent = new Intent(getApplicationContext(),OrderComplete.class);
                completeOrderIntent.putExtra("item count",itemCount);
                completeOrderIntent.putExtra("total price",totalPrice);
                completeOrderIntent.putExtra("order id",orderId);
                startActivity(completeOrderIntent);
                finish();

//                orderId++;//creates new orders, allowing multiple users to order at same time

                Log.d(TAG, "end of onCreate -> onClick FAB");
            }
        });

        Log.d(TAG, "end of onCreate");
    }

    //writes the completed cart to order
    private void commitOrderToDatabase() {
        for(int i = 0;i < itemCount;i++) {

            fireBase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = fireBase.getReference().child("orders").child("order" + orderId).child("item" + i);
            mRef.child("title").setValue(cartData.get(i).getTitle());
            mRef.child("price").setValue(cartData.get(i).getPrice());

        }

    }

    // initializeData with firebase
    private void loadData() {
        Log.d(TAG, "inside of loadData");

        database.child("cart" + cartID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                cartData.clear();//assures no duplicates are introduced
                resetTotalPrice();//reset price to zero

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    itemCount++;//keeps track of the number of items in cart
                    CartItem item = dataSnapshot.getValue(CartItem.class);

                    //keeps track of total price of cart

                    if (item.getPrice() != null) {
                        totalPrice += Float.parseFloat(item.getPrice());

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

    //if cart doesn't exist in database when
    //menu is onPause then set the cart to empty
    @Override
    protected void onPause() {
        super.onPause();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    Menu.resetCart();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //activates a notification letting user know order is complete
    //and order will be ready in 20 minutes
    protected void pickUpNotification() {
        Log.d(TAG, "inside sendNotification method");

        NotificationCompat.Builder notificationBuilder;
        Intent intent = new Intent ( this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,NOTIFICATION_ID_0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT >= 26) {
            Log.d(TAG, "inside API >-26 block of code");
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, PRIMARY_CHANNEL_NAME, IMPORTANCE_LEVEL);
            notificationChannel.setDescription("Reminders");
            notificationManager.createNotificationChannel(notificationChannel);

            notificationBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                    .setContentTitle("Order is complete")
                    .setContentText("Ready for pick up in 20 minutes!")
                    .setSmallIcon(R.drawable.ic_action_pizza);

        }else {

            notificationBuilder = new NotificationCompat.Builder(this)
                    .setPriority(IMPORTANCE_LEVEL)
                    .setContentTitle("Order is complete")
                    .setContentText("Ready for pick up in 20 minutes!")
                    .setSmallIcon(R.drawable.ic_action_pizza);

        }

        notificationBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID_0, notificationBuilder.build());

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }

    public static Object getCartID() {
        return cartID;
    }


    static class shoppingCartReceiver extends BroadcastReceiver {
        String CUSTOM_ACTION = "com.example.pizzaplanetapp.CARTID";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action!=null && action.equals(CUSTOM_ACTION)){
                cartID = intent.getExtras().get("cart id");
            }

        }
    }

}
