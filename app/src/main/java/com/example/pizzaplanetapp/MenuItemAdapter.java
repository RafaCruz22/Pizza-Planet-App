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

import java.util.ArrayList;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemHolder> {

    public static ArrayList<MenuItem> mMenuItem;
    public static Context mContext;

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
        //add item to cart?

        }

        public void bindTo(MenuItem currentMeal) {
            mTitleText.setText(currentMeal.getTitle());
            mMealButton.setText(currentMeal.getDetail());
            mDescriptionText.setText(currentMeal.getDescription());
            Glide.with(mContext).load(currentMeal.getImageResource()).into(mMealImage);
            ///Add to cart meal button
            mMealButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //log below is just to see something
                    Log.d("added to cart", "title: " + mTitleText.getText() + "\tImage: " + mMealImage.getDrawable());
                    Toast.makeText(mContext, "Add to cart:"+ mTitleText.getText(), Toast.LENGTH_SHORT).show();

                }
            });

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
