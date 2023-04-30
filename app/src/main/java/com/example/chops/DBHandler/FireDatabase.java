package com.example.chops.DBHandler;

import com.example.chops.Interfaces.ICallback;
import com.example.chops.controllers.CartController;
import com.example.chops.controllers.CustomerController;
import com.example.chops.models.CartItem;
import com.example.chops.models.Customer;
import com.example.chops.models.Food;
import com.example.chops.models.Order;
import com.example.chops.models.Restaurant;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FireDatabase implements IDatabase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void createCustomer(Customer customer, ICallback callback) {
        db.collection("Customers").document(customer.getId()).set(customer).addOnSuccessListener(
                (result)-> {
                    callback.execute(result,true);
                }
        ).addOnFailureListener(result->{
            callback.execute(result.getLocalizedMessage(),false);
        });
    }

    @Override
    public void retrieveCustomer(String id, ICallback callback) {
        db.collection("Customers").document(id).get().addOnSuccessListener(
                (result)->{
                    Customer c = result.toObject(Customer.class);
                    callback.execute(c,true);
                }
        ).addOnFailureListener(result->{
            callback.execute(null,false,result.getLocalizedMessage());
        });

    }

    @Override
    public void createRestaurant(Restaurant restaurant, ICallback callback) {
        db.collection("Restaurants").document(restaurant.getId()).set(restaurant).addOnSuccessListener(
                (result)-> {
                    callback.execute("Success",true);
                }
        ).addOnFailureListener(result->{
            callback.execute(result.getLocalizedMessage(),false);
        });
    }

    @Override
    public void getAllRestaurants(ICallback callback) {

    }

    @Override
    public void getSingleRestaurant(String id, ICallback callback) {
        db.collection("Restaurants").document(id).get().addOnSuccessListener(
                (result)->{
                    callback.execute(result.toObject(Restaurant.class),true);
                }
        ).addOnFailureListener(
                (result)->{
                    callback.execute(null,false);
                }
        );
    }

    @Override
    public void streamRestaurants(ICallback callback) {
        final CollectionReference ref = db.collection("Restaurants");
        ref.addSnapshotListener((snapshot, error)->{
            if (error != null) {
                callback.execute(new ArrayList<>(), false, error);
                return;
            }

            if (snapshot != null) {
                ArrayList<Restaurant> restaurantsRetrieved = new ArrayList<>();
                for(DocumentChange docChanges : snapshot.getDocumentChanges()) {
                    switch (docChanges.getType()) {
                        default:
                           QueryDocumentSnapshot documentSnapshot = docChanges.getDocument();
                            restaurantsRetrieved.add(documentSnapshot.toObject(Restaurant.class));
                            break;
                    }
                }
                callback.execute(restaurantsRetrieved, true);
        }
        });
    }
    @Override
    public void streamFoodList(ICallback callback) {
        final CollectionReference ref = db.collection("Foods");
        ref.addSnapshotListener((snapshot, error)->{
            if (error != null) {
                callback.execute(new ArrayList<>(), false, error);
                return;
            }

            if (snapshot != null) {
                ArrayList<Food> foodListRetrieved = new ArrayList<>();
                for(DocumentChange docChanges : snapshot.getDocumentChanges()) {
                    switch (docChanges.getType()) {
                        default:
                           QueryDocumentSnapshot documentSnapshot = docChanges.getDocument();
                            foodListRetrieved.add(documentSnapshot.toObject(Food.class));
                            break;
                    }
                }
                callback.execute(foodListRetrieved, true);
        }
        });
    }

    @Override
    public void getFoodList(ICallback callback) {
        db.collection("Foods").get().addOnSuccessListener(
                (queryDocumentSnapshots )->{
                    ArrayList<Food> foods = new ArrayList<>();
                    for(DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()){
                        foods.add(doc.toObject(Food.class));
                    }
                    callback.execute(foods);
                }
        );
    }

    @Override
    public void retrieveFoodListFromIds(ArrayList<String> ids, ICallback callback,ArrayList<Food> foodList) {
        if(ids.size() <= 0){
            callback.execute(foodList, true);
            return;
        }
        System.out.println("RetrievingFoodList Ids");
        String current = ids.remove(ids.size()-1);
        getFood(current, new ICallback() {
            @Override
            public void execute(Object... args) {
                if(args.length > 0){
                    Food food = args[0] instanceof  Food ? (Food)args[0] : null;

                    if(food!=null){
                        System.out.println("Food--++>"+food.getId());
                        foodList.add(food);
                        retrieveFoodListFromIds(ids,callback,foodList);
                    }
                }

            }
        });

    }

    @Override
    public void retrieveFoodListFromCartItems(ArrayList<CartItem> cartItems, ICallback callback, ArrayList<Food> foodList) {
        if(cartItems.size() <= 0){
            callback.execute(foodList, true);
            return;
        }
        CartItem current = cartItems.remove(cartItems.size()-1);
        getFood(current.getFoodId(), new ICallback() {
            @Override
            public void execute(Object... args) {
                if(args.length > 0){
                    Food food = args[0] instanceof  Food ? (Food)args[0] : null;

                    if(food!=null){
                        foodList.add(food);
                        retrieveFoodListFromCartItems(cartItems,callback,foodList);
                    }
                }

            }
        });

    }

    @Override
    public void getUserOrders(ArrayList<String> orderIDs, ICallback callback, ArrayList<Order> orders) {
        {

            db.collection("Orders").whereEqualTo("userId", CustomerController.GET_CURRENT_USER.getId()).addSnapshotListener((snapshot,error) -> {
                if (snapshot != null) {
                    System.out.println("Snapshot "+snapshot.getDocuments());
                    for (DocumentChange docChanges : snapshot.getDocumentChanges()) {
                        switch (docChanges.getType()) {
                            default:
                                QueryDocumentSnapshot documentSnapshot = docChanges.getDocument();
                                orders.add(documentSnapshot.toObject(Order.class));
                                break;
                        }
                    }
                    callback.execute(orders, true);
                    System.out.println("Orders - >" + orders);

                }

            });
        }
    }

    @Override
    public void createOrder(Order order, ICallback callback) {
        db.collection("Orders").document(order.getId()).set(order).addOnSuccessListener(
                (result)-> {
                    callback.execute("Success",true);
                }
        ).addOnFailureListener(result->{
            callback.execute(result.getLocalizedMessage(),false);
        });
    }

    @Override
    public void getUserCurrentOrder(String id, ICallback callback) {
        db.collection("CurrentOrder").document(id).get().addOnSuccessListener(
                (result)->{
                    Order order = result.toObject(Order.class);
                    callback.execute(order,true);
                }
        ).addOnFailureListener((res)->callback.execute(null,false));
    }

    @Override
    public void getAllOrders(ICallback callback) {

    }

    @Override
    public void addToCart(Food food, int quantity, ICallback callback, String uid, String restaurantId) {
        retrieveCart(uid, new ICallback() {
            @Override
            public void execute(Object... args) {
                Order order = args[0] instanceof Order ? (Order) args[0] : null;
                if (order!=null) {
                    ArrayList<CartItem> meals = order.getMeals();
                    boolean updated = false;
                    System.out.println("Food->>"+food.getPrice());
                    for (CartItem c : meals) {
                        if (c.getFoodId().equals(food.getId())) {
                            c.setTotalPrice(food.getPrice());
                            c.setQuantity(quantity);

                            updated = true;
                        }
                    }
                    if (!updated) {
                        CartItem newItem = new CartItem(UUID.randomUUID().toString(), food.getId(), quantity, food.getPrice());
                       newItem.setTotalPrice(food.getPrice());
                        meals.add(newItem);
                    }
                    System.out.println("MEALS->>" + meals);
                    order.setMeals(meals);
                } else {
                    order = new Order(UUID.randomUUID().toString(), uid, "", 0, false, false, restaurantId, new ArrayList<>());

                    CartItem newItem = new CartItem(UUID.randomUUID().toString(), food.getId(), 1, food.getPrice());
                    order.getMeals().add(newItem);
                    System.out.println("New MEALS->>" + order.getMeals());

                }
                updateCurrentOrder(uid, order, new ICallback() {
                    @Override
                    public void execute(Object... args) {
                        callback.execute(args[0], true);
                    }
                });

            }});
    }
    private void updateCurrentOrder(String uid, Order order, ICallback callback){
        db.collection("CurrentOrder").document(uid).set(order).addOnSuccessListener(
                (result)->{
                    callback.execute(order,true);
                }
        ).addOnFailureListener(
                (result)->{
                    callback.execute(order,false);
                }
        );
    }
    @Override
    public void removeFromCart(String uid, String foodId, int quantity, ICallback callback) {
        retrieveCart(uid, new ICallback() {
            @Override
            public void execute(Object... args) {
                Order order = args[0] instanceof  Order ? (Order)args[0] : null;
                if(order!=null){
                    ArrayList<CartItem> meals =  new ArrayList<>(order.getMeals());
                    ArrayList<CartItem> newMeals = new ArrayList<>();
                    boolean updated = false;
                    for(CartItem c : meals){
                        if(c.getFoodId().equals(foodId)){
                            System.out.println("Food to remove: "+foodId+" ~ Current Quantity: "+c.getQuantity());
                            c.setQuantity(quantity);
                            System.out.println("Quantity: "+c.getQuantity());
                            newMeals.add(c);
                        }else{
                            newMeals.add(c);
                        }
                    }
                    order.setMeals(newMeals);

                    updateCurrentOrder(uid, order, new ICallback() {
                        @Override
                        public void execute(Object... args) {
                            callback.execute(args[0],true);
                        }
                    });
                }


            }
        });
    }

    @Override
    public void updateCustomer(Customer customer, ICallback callback) {
        db.collection("Customers").document(customer.getId()).set(customer).addOnSuccessListener(e->callback.execute(e));
    }

    @Override
    public void retrieveCart(String uid, ICallback callback) {

        db.collection("CurrentOrder").document(uid).get().addOnSuccessListener(
                (result)->{

                    Order order  = result.toObject(Order.class);
                    System.out.println("CurrentORder ->>>"+"uid: "+uid+"<<<>"+order);
                    callback.execute(order, true);
                }
        ).addOnFailureListener(
                (result)->{
                    new MockDB().retrieveCart(uid,callback);
                }
        );

    }

    @Override
    public void payForOrder(Order order, ICallback callback) {
        createOrder(order, new ICallback() {
            @Override
            public void execute(Object... args) {
                CustomerController.GET_CURRENT_USER.getOrders().add(order.getId());
                CustomerController.GET_CURRENT_USER.setMoney(-1 * (CartController.calculateCart(order.getMeals())));
                Map<String,Object> updatesToDo = new HashMap<>();
                updatesToDo.put("orders",CustomerController.GET_CURRENT_USER.getOrders());
                updatesToDo.put("money",CustomerController.GET_CURRENT_USER.getMoney());
                db.collection("Customers").document(CustomerController.GET_CURRENT_USER.getId()).update(updatesToDo).addOnSuccessListener(
                        (result)->{
                            db.collection("CurrentOrder").document(CustomerController.GET_CURRENT_USER.getId()).delete();
                            callback.execute(true);
                        }
                );
            }
        });

    }

    @Override
    public void createFood(Food food, ICallback callback) {
        System.out.println("SAVING FOOD");
        db.collection("Foods").document(food.getId()).set(food).addOnSuccessListener(
                (result)-> {
                    System.out.println("DONE!!");
                    callback.execute(result,true);
                }
        ).addOnFailureListener(result->{
            callback.execute(result.getLocalizedMessage(),false);
        });
    }

    @Override
    public void getFood(String id, ICallback callback) {
        System.out.println("RETIREVING "+ id);
        db.collection("Foods").document(id).get().addOnSuccessListener(
                (result)->{
                    Food food = result.toObject(Food.class);
                    callback.execute(food);
                }
        ).addOnFailureListener(
                (r)->{
                    new MockDB().getFood(id,callback);
                }
        );
    }
}
