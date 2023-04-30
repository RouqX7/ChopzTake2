package com.example.chops.views.Adapters;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.Map;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.Viewholder> {
    public ArrayList<Food>  foods;
    public Map<String, Integer> foodQuantity = new HashMap<>();
    public ICallback callback;

    public boolean hideCartModify;

    public MenuAdapter(ArrayList<Food> foods){
        this.foods = foods;
        this.hideCartModify = false;
    }

    private void defaultQuantities(ArrayList<Food> foods) {

        for(Food f : foods){
            foodQuantity.put(f.getId(),0);
        }
    }

    public MenuAdapter(ArrayList<Food> foods,ICallback callback){
        this.foods = foods;
        this.callback = callback;
        this.hideCartModify = false;
    }
    public MenuAdapter(ArrayList<Food> foods,ICallback callback,boolean hideCartModify){
        this.foods = foods;
        this.callback = callback;
        this.hideCartModify = hideCartModify;
    }
    public void updateFoods(ArrayList<Food> foods){
        this.foods.clear();
        this.foods.addAll(foods);
        notifyItemRangeChanged(0,this.foods.size());
    }
    public void updateFoods(ArrayList<Food> foods, Map<String, Integer> foodQuantity){
        this.foods.clear();
        this.foods.addAll(foods);
        this.foodQuantity = foodQuantity;
        System.out.println("UF->"+this.foodQuantity);
        notifyItemRangeChanged(0,this.foods.size());
    }
    public  void  addFood(Food food){
        int insertAt = Math.max(0,foods.size());
        foods.add(insertAt,food);
        System.out.println("ADAPTER FOOD"+ foods);
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
        if(foodQuantity.isEmpty()) {
            defaultQuantities(foods);
        }
        return new MenuAdapter.Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.Viewholder holder, int position) {
        holder.menuItemText.setText(foods.get(position).getName());
        holder.foodPrice.setText("€"+foods.get(position).getPrice());
        holder.food = foods.get(position);
        if(holder.food != null){
            if(foodQuantity.containsKey(holder.food.getId()))
                holder.currentQuantityInCart.setText(foodQuantity.get(holder.food.getId()).toString());

        }

        System.out.println("FoodQ: "+foodQuantity);
        holder.foodQuantity = foodQuantity;
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


    // Set a custom InputFilter to prevent negative values


    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView menuFoodImage;
        CheckBox checkBox;
        TextView foodPrice;
        TextView menuItemText;
        TextView currentQuantityInCart;

        Button removeFromCartBtn;
        Button addToCartBtn;

        Food food;


        Map<String, Integer> foodQuantity = new HashMap<>();
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            if(SetUpConfig.ISADMIN ){
                itemView.setOnLongClickListener(v->{

                    return true;
                });
            }
            menuFoodImage = itemView.findViewById(R.id.menuFoodImage);
            removeFromCartBtn = itemView.findViewById(R.id.removeFromCartBtn);
            addToCartBtn = itemView.findViewById(R.id.addToCartBtn);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            menuItemText = itemView.findViewById(R.id.menuItemText);
            currentQuantityInCart = itemView.findViewById(R.id.currentQuantityInCart);

            addToCartBtn.setVisibility(hideCartModify ? View.GONE : View.VISIBLE);
            removeFromCartBtn.setVisibility(hideCartModify ? View.GONE : View.VISIBLE);
            //currentQuantityInCart.setVisibility(hideCartModify ? View.GONE : View.VISIBLE);

            if(food != null){
                if(foodQuantity.containsKey(food.getId()))
                    currentQuantityInCart.setText(foodQuantity.get(food.getId()).toString());

            }

            addToCartBtn.setOnClickListener((v)->{
                System.out.println("FOOOOOOOD-->"+foodQuantity);
              //false = remove //true = add
                if(foodQuantity.containsKey(food.getId())) {
                    foodQuantity.put(food.getId(), foodQuantity.get(food.getId()) + 1);
                    currentQuantityInCart.setText(foodQuantity.get(food.getId()).toString());
                    callback.execute(food, true, foodQuantity.get(food.getId()));
                }
            });
            removeFromCartBtn.setOnClickListener((v)->{
                System.out.println("FOOOOOOOD-->"+food);
              //false = remove //true = add
                if(foodQuantity.containsKey(food.getId())) {
                    if(foodQuantity.get(food.getId()) > 0) {
                        foodQuantity.put(food.getId(), foodQuantity.get(food.getId()) - 1);
                    }
                    currentQuantityInCart.setText(foodQuantity.get(food.getId()).toString());
                    callback.execute(food, false, foodQuantity.get(food.getId()));
                }
            });
        }
    }
}
