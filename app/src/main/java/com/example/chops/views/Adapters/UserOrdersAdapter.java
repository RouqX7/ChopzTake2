package com.example.chops.views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chops.Config.SetUpConfig;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;
import com.example.chops.controllers.CartController;
import com.example.chops.controllers.DBController;
import com.example.chops.controllers.MenuListController;
import com.example.chops.models.Food;
import com.example.chops.models.Order;
import com.example.chops.models.Restaurant;

import java.util.ArrayList;
import java.util.Map;

public class UserOrdersAdapter  extends RecyclerView.Adapter<UserOrdersAdapter.Viewholder>  {
    public ArrayList<Order> order;
    public ICallback callback;

    UserOrdersAdapter(ArrayList<Order> order){
        this.order = order;
    }

    public UserOrdersAdapter(ArrayList<Order> order, ICallback callback){
        this.order = order;
        this.callback = callback;
    }

    @NonNull
    @Override
    public UserOrdersAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_profile_orders,parent,false);

        return new UserOrdersAdapter.Viewholder(inflate);
    }

    public void updateOrders(ArrayList<Order> orders){
        this.order.clear();
        this.order.addAll(orders);
        notifyItemRangeChanged(0,this.order.size());
    }
    @Override
    public void onBindViewHolder(@NonNull UserOrdersAdapter.Viewholder holder, int position) {
        holder.order = order.get(position);
        holder.setupHolder();
    }
    @Override
    public int getItemCount() {
        return 0;
    }
    public class Viewholder extends RecyclerView.ViewHolder{
        TextView resturantName;
        RecyclerView recyclerView;
        MenuAdapter menuAdapter;
        Order order;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            resturantName = itemView.findViewById(R.id.profile_restaurant_name);
            recyclerView = itemView.findViewById(R.id.profile_restaurant_orders);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));


        }

        public void setupHolder(){
            menuAdapter = MenuListController.getModifierMenuAdapter(itemView.getContext(), new ICallback() {
                @Override
                public void execute(Object... args) {
                }
            },order.getRestaurantId(),true);
            recyclerView.setAdapter(menuAdapter);
            DBController.DATABASE.getSingleRestaurant(order.getRestaurantId(), new ICallback() {
                @Override
                public void execute(Object... args) {
                    if(args.length > 0){
                        Restaurant restaurant = args[0] instanceof  Restaurant ? (Restaurant) args[0] : null;
                        if(restaurant != null){
                            resturantName.setText(restaurant.getName());
                        }
                    }
                    DBController.DATABASE.retrieveFoodListFromCartItems(order.getMeals(), new ICallback() {
                        @Override
                        public void execute(Object... args) {
                            if(args.length > 0){
                                ArrayList<Food> food = args[0] instanceof ArrayList ? (ArrayList<Food>) args[0] : new ArrayList<>();
                                if(!food.isEmpty()){
                                    menuAdapter.updateFoods(food);
                                }
                            }
                        }
                    },new ArrayList<>());
                }
            });

            if(SetUpConfig.ISADMIN ){
                itemView.setOnLongClickListener(v->{

                    return true;
                });
            }

        }
    }
}
