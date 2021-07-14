package com.example.tajhotel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tajhotel.CustomClasses.FoodCategories;
import com.example.tajhotel.R;

import java.util.ArrayList;

public class HomeCategoriesAdapter extends RecyclerView.Adapter<HomeCategoriesAdapter.ViewHolder> {

    Context context;
    ArrayList<FoodCategories> foodCategories;
    int selectedCategory = -1;
    OnCategoryClick onCategoryClick;

    public HomeCategoriesAdapter(Context context, ArrayList<FoodCategories> foodCategories, OnCategoryClick onCategoryClick) {
        this.context = context;
        this.foodCategories = foodCategories;
        this.onCategoryClick = onCategoryClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.food_catergories_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeCategoriesAdapter.ViewHolder holder, int position) {

        FoodCategories categories = foodCategories.get(position);
        holder.foodImageView.setImageResource(categories.getImage());
        holder.foodTextView.setText(categories.getFoodName());
    }

    @Override
    public int getItemCount() {
        return foodCategories.size();
    }

    public interface OnCategoryClick {
        void onClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImageView;
        TextView foodTextView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.foodImageView = itemView.findViewById(R.id.image_categories);
            this.foodTextView = itemView.findViewById(R.id.food_name_categories);
            this.cardView = itemView.findViewById(R.id.card_view_category);

            cardView.setOnClickListener(v -> {
                selectedCategory = getAdapterPosition();

                if (onCategoryClick != null) {
                    onCategoryClick.onClick(getAdapterPosition());
                }
                notifyDataSetChanged();
            });
        }
    }
}
