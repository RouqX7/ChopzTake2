package com.example.chops.views.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chops.Config.SetUpConfig;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.models.Restaurant;

import java.util.ArrayList;


public class RestaurantAdapter extends  RecyclerView.Adapter<RestaurantAdapter.Viewholder>{

    public ArrayList<Restaurant> restaurants;
    public ICallback callback;

    public RestaurantAdapter(ArrayList<Restaurant> Restaurants){
        this.restaurants = Restaurants;
    }
    public RestaurantAdapter(ArrayList<Restaurant> Restaurants, ICallback callback){
        this.restaurants = Restaurants;
        this.callback = callback;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_fast_delivery,parent,false);

        return new Viewholder(inflate);
    }
    public void updateRestaurants(ArrayList<Restaurant> restaurants){
        this.restaurants.clear();
        this.restaurants.addAll(restaurants);
        notifyDataSetChanged();
    }

    public void addRestaurant(Restaurant restaurant){
        int insertAt = Math.max(0,restaurants.size()-1);
        restaurants.add(insertAt,restaurant);
        notifyItemChanged(insertAt);

    }
    public void removeRestaurant(int position){
        restaurants.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.avgTime.setText(restaurants.get(position).getAvgTime()+" min");
        holder.name.setText(restaurants.get(position).getName());
        holder.rating.setText(String.valueOf(restaurants.get(position).getRating()));
        holder.restaurant = restaurants.get(position);
        if(restaurants.get(position).getImage()!=null){
            int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(restaurants.get(position).getImage(),"drawable",holder.itemView.getContext().getPackageName());
            Glide.with(holder.itemView.getContext())
                    .load(drawableResourceId)
                    .into(holder.image);

        }

    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView name, avgTime, rating;
        ImageView image;
        Restaurant restaurant;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v->{
                if(callback!=null)
                callback.execute(restaurant);
            });
            if(SetUpConfig.ISADMIN ){
                itemView.setOnLongClickListener(v->{

                    return true;
                });
            }

            name = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.pic);
            rating = itemView.findViewById(R.id.star);
            avgTime = itemView.findViewById(R.id.time);
        }
    }
}
