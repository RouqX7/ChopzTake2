package com.example.chops.views.Adapters;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chops.Interfaces.ICallback;
import com.example.chops.views.Menu;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.> {
    public ArrayList<Menu> menus;
    public ICallback callback;

}
