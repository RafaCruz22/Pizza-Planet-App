package com.example.pizzaplanetapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class AppetizerAdapter extends RecyclerView.Adapter<AppetizerAdapter.AppetizerAdapterHolder> {

    private  Context context;
    private static ArrayList<MenuItem> menuItems;

    AppetizerAdapter(Context context, ArrayList<MenuItem> MenuItem){
        this.menuItems = MenuItem;
        this.context = context;

    }


    @NonNull
    @Override
    public AppetizerAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppetizerAdapterHolder(LayoutInflater.from(context).
                inflate(R.layout.menu_item_cards,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AppetizerAdapterHolder holder, int position) {

        MenuItem item = menuItems.get(position);
        holder.bindTo(item);

    }



    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    protected class AppetizerAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView titleTextApp;
        private ImageView imageApp;
        private Button buttonApp;

        public AppetizerAdapterHolder(@NonNull View itemView) {
            super(itemView);

            titleTextApp = itemView.findViewById(R.id.title);
            imageApp = itemView.findViewById(R.id.mealImage);
            buttonApp = itemView.findViewById(R.id.meal);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

        }

        public void bindTo(MenuItem item) {

            titleTextApp.setText(item.getTitle());
            buttonApp.setText(item.getDetail());
            Glide.with(context).load(item.getImageResource()).into(imageApp);

        }
    }
}
