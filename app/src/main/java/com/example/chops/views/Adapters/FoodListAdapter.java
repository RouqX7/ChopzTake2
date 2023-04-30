package com.example.chops.views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chops.Config.SetUpConfig;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.models.Food;

import java.util.ArrayList;


public class FoodListAdapter extends  RecyclerView.Adapter<FoodListAdapter.Viewholder> {

    ArrayList<Food> foods;
    ArrayList<String> selectedFoods = new ArrayList<>();
    ICallback callback;
    public FoodListAdapter(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public FoodListAdapter(ArrayList<Food> foods, ICallback callback) {
        this.foods = foods;
        this.callback = callback;
    }


    public FoodListAdapter(ArrayList<Food> foods,ArrayList<String> selectedFoods, ICallback callback) {
        this.foods = foods;
        this.callback = callback;
        this.selectedFoods = selectedFoods;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_food_item, parent, false);

        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.foodItem.setText(foods.get(position).getName());
        holder.food = foods.get(position);
        if(selectedFoods.contains(holder.food.getId())){
            holder.foodItem.setChecked(true);
        }
        if(foods.get(position).getImage()!=null) {
            int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foods.get(position).getImage(), "drawable", holder.itemView.getContext().getPackageName());
            Glide.with(holder.itemView.getContext())
                    .load(drawableResourceId)
                    .into(holder.foodImage);
        }

    }
    public void updatefoods(ArrayList<Food> foods){
        this.foods.clear();
        this.foods.addAll(foods);
        notifyDataSetChanged();
    }

    public void addFood(Food Food){
        int insertAt = Math.max(0,foods.size()-1);
        foods.add(insertAt,Food);
        notifyItemChanged(insertAt);

    }
    public void removeFood(int position){
        foods.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        CheckBox foodItem;
        ImageView foodImage;
        Food food = new Food();
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            foodItem = itemView.findViewById(R.id.foodItem);
            foodImage = itemView.findViewById(R.id.foodImage);
           // System.out.println("--> "+selectedFoods);

            foodItem.setOnCheckedChangeListener((v, n)->{
                callback.execute(food,foodItem.isChecked());
            });
            if(SetUpConfig.ISADMIN ){
                itemView.setOnLongClickListener(v->{

                    return true;
                });
            }


        }
    }
}
