package com.example.chops.views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chops.Config.SetUpConfig;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.models.Food;
import com.example.chops.views.Menu;
import com.google.protobuf.StringValue;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.Viewholder> {
    public ArrayList<Food>  foods;
    public ICallback callback;

    public MenuAdapter(ArrayList<Food> foods){
        this.foods = foods;
    }
    public MenuAdapter(ArrayList<Food> foods,ICallback callback){
        this.foods = foods;
        this.callback = callback;
    }
    public void updateFoods(ArrayList<Food> foods){
        this.foods.clear();
        this.foods.addAll(foods);
        notifyDataSetChanged();
    }
    public  void  addFood(Food food){
        int insertAt = Math.max(0,foods.size()-1);
        foods.add(insertAt,food);
        notifyItemChanged(insertAt);
    }
    public void removeFood(int position){
        foods.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public MenuAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_menu_item,parent,false);

        return new MenuAdapter.Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.Viewholder holder, int position) {
        holder.checkBox.setText(foods.get(position).getName());
        holder.foodPrice.setText("$"+foods.get(position).getPrice());
        holder.food = foods.get(position);
        if(foods.get(position).getImage()!=null){
            int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foods.get(position).getImage(),"drawable",holder.itemView.getContext().getPackageName());
            Glide.with(holder.itemView.getContext())
                    .load(drawableResourceId)
                    .into(holder.menuFoodImage);
        }
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView menuFoodImage;
        CheckBox checkBox;
        TextView foodPrice;
        Food food;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v->{
                callback.execute(food);
            });
            if(SetUpConfig.ISADMIN ){
                itemView.setOnLongClickListener(v->{

                    return true;
                });
            }
            menuFoodImage = itemView.findViewById(R.id.menuFoodImage);
            checkBox = itemView.findViewById(R.id.menuFoodItem);
            foodPrice = itemView.findViewById(R.id.foodPrice);
        }
    }
}
