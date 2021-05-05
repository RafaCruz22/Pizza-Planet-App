package com.example.pizzaplanetapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AppetizerAdapter extends RecyclerView.Adapter<AppetizerAdapter.AppetizerAdapterHolder> {

    private  Context context;
    private static ArrayList<AppetizerItem> appetizerItems;
    private static int counter = Menu.getCounter(); //used to create a new item in cart

    FirebaseDatabase database;//Firebase variable

    AppetizerAdapter(Context context, ArrayList<AppetizerItem> AppetizerItems){
        this.appetizerItems = AppetizerItems;
        this.context = context;

    }


    @NonNull
    @Override
    public AppetizerAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppetizerAdapterHolder(LayoutInflater.from(context).
                inflate(R.layout.appetizer_item_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AppetizerAdapterHolder holder, int position) {

        AppetizerItem item = appetizerItems.get(position);
        holder.bindTo(item);

    }


    @Override
    public int getItemCount() {
        return appetizerItems.size();
    }

    protected class AppetizerAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView titleTextApp;
        private ImageView imageApp;
        private Button buttonApp;

        public AppetizerAdapterHolder(@NonNull View itemView) {
            super(itemView);

            titleTextApp = itemView.findViewById(R.id.appetizerTitle);
            imageApp = itemView.findViewById(R.id.appetizerImage);
            buttonApp = itemView.findViewById(R.id.appetizer);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

        }

        public void bindTo(AppetizerItem item) {

            titleTextApp.setText(item.getTitle());
            buttonApp.setText(item.getDetail() + "  Price: $" + item.getPrice());
            Glide.with(context).load(item.getImageResource()).into(imageApp);
            ///Add to cart meal button
            buttonApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    writeToDatabaseCart(item);

                    //user is trying to add a item to cart, cart is no longer empty so set it to full
                    Menu.cartFull();

                    //log below is a temp toast
                    Toast.makeText(context, "Add to cart: " + titleTextApp.getText(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    //creates a "cart" node in the firebase database to represent
    // a shopping cart with the items a user selects
    private void writeToDatabaseCart(AppetizerItem currentMeal){

        database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database
                .getReference()
                .child("cart")
                .child("item" + Menu.getCounter());

        mRef.child("title")
                .setValue(currentMeal.getTitle());

        mRef.child("imageResource")
                .setValue(currentMeal.getImageResource());

        mRef.child("price")
                .setValue(currentMeal.getPrice());

        mRef.child("position")
                .setValue(String.valueOf(Menu.getCounter()));

        Menu.increaseCartCount();

        //detail & description of the item isn't needed for cart ebut here just in case
        //mRef.child("detail").setValue(mTitleText.getText());
        //mRef.child("description").setValue(mTitleText.getText());

    }
}
