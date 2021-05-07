package com.example.pizzaplanetapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private static final String TAG = "ShoppingCartAdapter";

    private ArrayList<CartItem> cartData;
    private Context context;


    public ShoppingCartAdapter(Context context, ArrayList<CartItem> CartData){
        this.context = context;
        this.cartData = CartData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((LayoutInflater.from(context)
                .inflate(R.layout.cart_item,parent,false)));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem currItem = cartData.get(position);
        holder.bindItem(currItem);

    }

    @Override
    public int getItemCount() {
        return cartData.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textName, textPrice;
        private ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "inside of ViewHolder");


            textName = itemView.findViewById(R.id.cartMealName);
            textPrice = itemView.findViewById(R.id.cartMealPrice);
            imageView = itemView.findViewById(R.id.mealItemImage);

            Log.d(TAG, "end of of ViewHolder");
        }

        public void bindItem(CartItem currItem) {
            Log.d(TAG, "inside of bindItem");

            String price = " Price: $" + currItem.getPrice();
            textName.setText(currItem.getTitle());
            textPrice.setText(price);
            Glide.with(context).load(currItem.getImageResource()).into(imageView);

            Log.d(TAG, "end of bindItem");
        }

    }

}
