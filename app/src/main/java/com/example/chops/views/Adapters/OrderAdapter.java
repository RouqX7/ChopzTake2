package com.example.chops.views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chops.Config.SetUpConfig;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.models.Order;
import com.example.chops.views.Menu;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Viewholder> {
    public Order order;
    public ICallback callback;

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_orders,parent,false);

            return new OrderAdapter.Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 0;
    }
    public class Viewholder extends RecyclerView.ViewHolder{
        Order order;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v->{
                callback.execute(order);
            });
            if(SetUpConfig.ISADMIN ){
                itemView.setOnLongClickListener(v->{

                    return true;
                });
        }
    }


    }
}
