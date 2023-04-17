package com.example.chops.DBHandler;

import com.example.chops.Interfaces.ICallback;
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
    public void streamOrders(ICallback callback) {
        {
            final CollectionReference ref = db.collection("Orders");
            ref.addSnapshotListener((snapshot, error)->{
                if (error != null) {
                    callback.execute(new ArrayList<>(), false, error);
                    return;
                }

                if (snapshot != null) {
                    ArrayList<Order> ordersRetrieved = new ArrayList<>();
                    for(DocumentChange docChanges : snapshot.getDocumentChanges()) {
                        switch (docChanges.getType()) {
                            default:
                                QueryDocumentSnapshot documentSnapshot = docChanges.getDocument();
                                ordersRetrieved.add(documentSnapshot.toObject(Order.class));
                                break;
                        }
                    }
                    callback.execute(ordersRetrieved, true);
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

    }

    @Override
    public void getAllOrders(ICallback callback) {

    }

    @Override
    public void addToCart(Food food, int quantity, ICallback callback, String uid) {

    }

    @Override
    public void removeFromCart(String uid, String foodId, int quantity, ICallback callback) {

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

    }
}
