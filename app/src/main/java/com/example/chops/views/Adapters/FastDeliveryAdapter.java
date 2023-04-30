package com.example.chops.views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chops.Domain.RestaurantDomain;
import com.example.chops.R;

import java.util.ArrayList;


public class FastDeliveryAdapter extends  RecyclerView.Adapter<FastDeliveryAdapter.Viewholder>{

    ArrayList<RestaurantDomain> restaurantDomains;

    public FastDeliveryAdapter(ArrayList<RestaurantDomain> restaurantDomains){
        this.restaurantDomains = restaurantDomains;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_fast_delivery,parent,false);

        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.time.setText(restaurantDomains.get(position).getTime()+" min");
        holder.title.setText(restaurantDomains.get(position).getTitle());
        holder.star.setText(String.valueOf(restaurantDomains.get(position).getStar()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(restaurantDomains.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

    } 

    @Override
    public int getItemCount() {
        return restaurantDomains.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView title,time,star;
        ImageView pic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            pic = itemView.findViewById(R.id.pic);
            star= itemView.findViewById(R.id.star);
            time = itemView.findViewById(R.id.time);
        }
    }
}
