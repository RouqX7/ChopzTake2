package com.example.chops.views.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chops.Domain.CategoryDomain;
import com.example.chops.Interfaces.ICallback;
import com.example.chops.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<CategoryDomain> categoryDomains;
    ICallback onCatagorySelected;

    public CategoryAdapter(ArrayList<CategoryDomain> categoryDomains) {
        this.categoryDomains = categoryDomains;
    }
    public CategoryAdapter(ArrayList<CategoryDomain> categoryDomains, ICallback onCatagorySelected) {
        this.categoryDomains = categoryDomains;
        this.onCatagorySelected = onCatagorySelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);

return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
holder.categoryName.setText(categoryDomains.get(position).getTitle());
holder.categoryDomain = categoryDomains.get(position);
String picUrl ="";
switch (position){
    case 0:{
        picUrl = "cat_1";
        break;
    }
    case 1:{
        picUrl = "cat_2";
        break;
    }
    case 2:{
        picUrl = "cat_3";
        break;
    }
    default:{
        picUrl = "cat_4";
        break;
    }

}
int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.categoryPic);
    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
    TextView categoryName;
    ImageView categoryPic;

    CategoryDomain categoryDomain;
    ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.CategoryName);
            categoryPic = itemView.findViewById(R.id.CategoryPic);
            categoryPic.setOnClickListener(e->{
                onCatagorySelected.execute(categoryDomain);
            });

        }
    }
}
