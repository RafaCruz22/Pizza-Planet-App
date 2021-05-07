package com.example.pizzaplanetapp;

import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.util.Log;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemHolder> {

    private static ArrayList<MenuItem> mMenuItem;
    private static Context mContext;

    FirebaseDatabase database;//Firebase variable
    private static int counter = Menu.getCounter(); //used to create a new item in cart

    MenuItemAdapter(Context context, ArrayList<MenuItem> MenuItem) {
        this.mMenuItem = MenuItem;
        this.mContext = context;


    }

    @NonNull
    @Override
    public MenuItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuItemHolder(LayoutInflater.from(mContext).
                inflate(R.layout.menu_item_cards, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemHolder holder, int position) {

        MenuItem currentMeal = mMenuItem.get(position);
        holder.bindTo(currentMeal);

    }

    @Override
    public int getItemCount() {
        return mMenuItem.size();
    }

    class MenuItemHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private TextView mTitleText;
        private TextView mDescriptionText;
        private ImageView mMealImage;
        private Button mMealButton;


        public MenuItemHolder(View itemView) {
            super(itemView);


            mTitleText = itemView.findViewById(R.id.title);
            mDescriptionText = itemView.findViewById(R.id.description);
            mMealImage = itemView.findViewById(R.id.mealImage);
            mMealButton = itemView.findViewById(R.id.meal);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //using button instead
        }

        public void bindTo(MenuItem currentMeal) {
            mTitleText.setText(currentMeal.getTitle());
            mMealButton.setText(currentMeal.getDetail() + " price: $" + currentMeal.getPrice() );
            mDescriptionText.setText(currentMeal.getDescription());
            Glide.with(mContext).load(currentMeal.getImageResource()).into(mMealImage);
            ///Add to cart meal button
            mMealButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    writeToDatabaseCart(currentMeal);

                    //user is trying to add a item to cart, cart is no longer empty so set it to full
                    Menu.cartFull();

                    //log below is a temp toast
                    Toast.makeText(mContext, "Add to cart: " + mTitleText.getText(), Toast.LENGTH_SHORT).show();

                }
            });

        }

        //creates a "cart" node in the firebase database to represent
        // a shopping cart with the items a user selects
        private void writeToDatabaseCart(MenuItem currentMeal){

            database = FirebaseDatabase.getInstance();
            DatabaseReference mRef = database.getReference().child("cart").child("item" + Menu.getCounter());
            mRef.child("title").setValue(currentMeal.getTitle());
            mRef.child("imageResource").setValue(currentMeal.getImageResource());
            mRef.child("price").setValue(currentMeal.getPrice());
            mRef.child("position").setValue(String.valueOf(Menu.getCounter()));
            Menu.increaseCartCount();

            //detail & description of the item isn't needed for cart but here just in case
            //mRef.child("detail").setValue(mTitleText.getText());
            //mRef.child("description").setValue(mTitleText.getText());

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

    }
    //resets the cart item counter to zero
    public static void resetCounter(){
        counter = 0;
    }

}
