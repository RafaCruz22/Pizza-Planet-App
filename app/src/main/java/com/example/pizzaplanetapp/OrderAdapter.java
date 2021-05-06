package com.example.pizzaplanetapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private static final String TAG = "OrderAdapter";

    private ArrayList<OrderItem> orderData;
    private Context context;

    public OrderAdapter(Context context, ArrayList<OrderItem> OrderData){
        this.context = context;
        this.orderData = OrderData;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderAdapter.ViewHolder((LayoutInflater.from(context)
                .inflate(R.layout.order_items,parent,false)));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        OrderItem currItem = orderData.get(position);
        holder.bindItem(currItem);

    }

    @Override
    public int getItemCount() {
        return orderData.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textName, textPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "inside of ViewHolder");

            textName = itemView.findViewById(R.id.orderMealName);
            textPrice = itemView.findViewById(R.id.orderMealPrice);

            Log.d(TAG, "end of of ViewHolder");
        }

        public void bindItem(OrderItem currItem) {
            Log.d(TAG, "inside of bindItem");

            textName.setText(currItem.getTitle());
            textPrice.setText("$" + currItem.getPrice());

            Log.d(TAG, "end of bindItem");
        }

    }

}


