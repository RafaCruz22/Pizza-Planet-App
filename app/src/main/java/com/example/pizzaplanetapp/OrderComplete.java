package com.example.pizzaplanetapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderComplete extends AppCompatActivity {

    //gives user an overview of their placed order (items, price, tax, total price) along with
    // the ability to call store or text driver

    private static final String TAG = "OrderComplete";

    private RecyclerView recyclerView;
    private ArrayList<OrderItem> orderData;
    private OrderAdapter orderAdapter;
    private Object itemCount,totalPrice,orderID;
    private TextView textTotalPrice, textTotalItems;

    DatabaseReference database;
    int girdColumnCount = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_order);

        Intent intent = getIntent();
        totalPrice = intent.getExtras().get("total price");
        itemCount = intent.getExtras().get("item count");
        orderID = intent.getExtras().get("order id");

        recyclerView = findViewById(R.id.recyclerCartView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, girdColumnCount));

        textTotalPrice = findViewById(R.id.totalAmountSummary_TextView);
        textTotalItems = findViewById(R.id.totItemAmountSummary_TextView);

        textTotalPrice.setText("$" + String.format("%.2f", totalPrice));
        textTotalItems.setText(itemCount.toString());

        //Reference to firebase database starting at pizza node
        database = FirebaseDatabase.getInstance().getReference("orders");

        // initialize the array that will be used to hold the cart items
        orderData = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderData);

        recyclerView.setAdapter(orderAdapter);

        loadData();
    }

    // initializeData with firebase
    private void loadData() {
        Log.d(TAG, "inside of loadData");
//        database = database.child("order" + orderID);

        database.child("order" + orderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                orderData.clear();//assures no duplicates are introduced

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OrderItem item = dataSnapshot.getValue(OrderItem.class);
                    orderData.add(item);
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d(TAG, "end of loadData");
    }

    @Override
    protected void onDestroy() {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("orders").child("order" + orderID).removeValue();
        super.onDestroy();
    }

    //shows an alert displaying the information like store address
    public void btn_moreInfo(View view) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String address = sharedPref.getString("address","Pixar Animation Studios \n1200 Park Avenue \nEmeryville, California 94608");

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Store Information");
        alertBuilder.setMessage(address);
        alertBuilder.show();

    }

    //allows user to make a phone call to store
    public void btn_callStore(View view) {

        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(OrderComplete.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String phoneNum = sharedPref.getString("phone","800-123-4567");


                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNum));
                startActivity(intent);

            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "800-987-6543"));
                startActivity(intent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
