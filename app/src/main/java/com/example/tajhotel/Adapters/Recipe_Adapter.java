package com.example.tajhotel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.tajhotel.CustomClasses.Recipe_Model;
import com.example.tajhotel.LocalDataBase.DataBaseHelper;
import com.example.tajhotel.MainActivity;
import com.example.tajhotel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Recipe_Adapter extends RecyclerView.Adapter<Recipe_Adapter.FoodHolder> {

    Context context;
    List<Recipe_Model> data;
    int HEART_FLAG = 0;
    DataBaseHelper db;

    public Recipe_Adapter(Context context, List<Recipe_Model> data, DataBaseHelper dataBaseHelper) {
        this.context = context;
        this.data = data;
        this.db = dataBaseHelper;
    }

//    public Recipe_Adapter(){}


    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.home_more_food_list, parent, false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(Recipe_Adapter.FoodHolder holder, int position) {

        holder.price.setText("₹" + data.get(position).getFood_price());
        holder.image.setImageResource(data.get(position).getPic());
        holder.foodName.setText(data.get(position).getFood_name());
        holder.ratingBar.setRating(data.get(position).getRating());

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            int ADD_TO_CART_FLAG = 0;

            @Override
            public void onClick(View v) {

                if (ADD_TO_CART_FLAG == 0) {

                    /** here we are keeping track of whether the particular food item is added to cart or not **/
                    keepsCheckOfOrderedFood(position);

                    Toast.makeText(context, "Food Added to cart : " + position, Toast.LENGTH_SHORT).show();
                    holder.addToCartBtn.setText("ADDED TO CART");
                    ADD_TO_CART_FLAG = 1;
                } else {
                    keepsCheckOfRemovedOrderedFood(position);
                    Toast.makeText(context, "Food Removed from cart", Toast.LENGTH_SHORT).show();
                    holder.addToCartBtn.setText("ADD TO CART");
                    ADD_TO_CART_FLAG = 0;
                }
            }
        });

    }

    /**
     * This keepsCheckOfOrderedFood method, keeps the track of food list added to the orderedFoodArrayList
     *
     * @param position
     */
    private void keepsCheckOfOrderedFood(int position) {
        if (data.get(position).getFood_order() == Boolean.FALSE) {
            storeOrderedFood(position);
            Recipe_Model recipe_model = data.get(position);
            recipe_model.setFood_order(Boolean.TRUE);
            data.set(position, recipe_model);
        }
    }

    private void keepsCheckOfRemovedOrderedFood(int position) {
        if (data.get(position).getFood_order() == Boolean.TRUE) {
            removeOrderedFood(position);
            Recipe_Model recipe_model = data.get(position);
            recipe_model.setFood_order(Boolean.FALSE);
            data.set(position, recipe_model);
        }
    }


    /**
     * This storeOrderedFood method stores the food items selected to the orderedFoodArrayList
     *
     * @param position
     */
    public void storeOrderedFood(int position) {

        boolean insertCheck = db.insertData(data.get(position).getFood_name(), data.get(position).getFood_price(),
                data.get(position).getPic(), data.get(position).getFood_summery(), data.get(position).getRating());

        if (insertCheck == false) {
            Toast.makeText(context, "Enable to save into cart", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * This removeOrderedFood method removes the food item from the orderedFoodArrayList
     *
     * @param position
     */
    public void removeOrderedFood(int position) {

        boolean removeCheck = db.removeData(data.get(position).getFood_name());

        if (removeCheck == false) {
            Toast.makeText(context, "Enable to remove from cart", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(context, data.get(position).getFood_name(), Toast.LENGTH_LONG).show();
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder {

        RatingBar ratingBar;
        ImageView image, heartImage;
        TextView foodName;
        TextView price;
        AppCompatButton addToCartBtn;

        public FoodHolder(View holder) {
            super(holder);
            ratingBar = holder.findViewById(R.id.food_item_ratings);
            foodName = holder.findViewById(R.id.food_item_name);
            image = holder.findViewById(R.id.food_item_img);
            price = holder.findViewById(R.id.food_item_price);
            heartImage = holder.findViewById(R.id.food_item_heart);
            addToCartBtn = holder.findViewById(R.id.food_item_button);

            heartImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (HEART_FLAG == 0) {
                        Toast.makeText(context, "Added to WishList", Toast.LENGTH_SHORT).show();
                        heartImage.setImageResource(R.drawable.ic_filled_heart_com);
                        HEART_FLAG = 1;
                    } else {
                        Toast.makeText(context, "Removed from WishList", Toast.LENGTH_SHORT).show();
                        heartImage.setImageResource(R.drawable.ic_heart_svgrepo_com__1_);
                        HEART_FLAG = 0;
                    }
                }
            });

        }
    }
}
