package com.example.tajhotel.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tajhotel.CustomClasses.OrderedFood;
import com.example.tajhotel.CustomClasses.Recipe_Model;
import com.example.tajhotel.LocalDataBase.DataBaseHelper;
import com.example.tajhotel.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe_Adapter extends RecyclerView.Adapter<Recipe_Adapter.FoodHolder> {

    Context context;
    List<Recipe_Model> data;
    int LENGTH;
    int HEART_FLAG = 0;
    DataBaseHelper db;
//    int ADD_TO_CART_FLAG = 0;
//    ArrayList<OrderedFood> orderedFoodArrayList;
    Map<Integer, Recipe_Model> orderedFoodMap;
//    ArrayList<Boolean> orderedBoolean;

    public Recipe_Adapter(Context context, List<Recipe_Model> data, int ordered_Count, DataBaseHelper dataBaseHelper) {
        this.context = context;
        this.data = data;
        this.LENGTH = ordered_Count;
        this.db = dataBaseHelper;
//        orderedFoodArrayList = new ArrayList<OrderedFood>(LENGTH);
    }


    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.home_more_food_list, parent, false);
//        orderedFoodArrayList = new ArrayList<OrderedFood>(LENGTH);
        orderedFoodMap = new HashMap<Integer, Recipe_Model>(LENGTH);
//        orderedBoolean = new ArrayList<Boolean>(10);

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
                }
                else {
//                    removeOrderedFood(position);
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
     * @param position
     */
    private void keepsCheckOfOrderedFood(int position) {
        if(data.get(position).getFood_order() == Boolean.FALSE) {
            storeOrderedFood(position);
            Recipe_Model recipe_model = data.get(position);
            recipe_model.setFood_order(Boolean.TRUE);
            data.set(position, recipe_model);
        }
//        else if(data.get(position).getFood_order() == Boolean.TRUE){
//            removeOrderedFood(position);
//            Recipe_Model recipe_model = data.get(position);
//            recipe_model.setFood_order(Boolean.FALSE);
//            data.set(position, recipe_model);
//        }
    }

    private void keepsCheckOfRemovedOrderedFood(int position) {
        if(data.get(position).getFood_order() == Boolean.TRUE){
            removeOrderedFood(position);
//            removeOrderedFood(data.get(position));
            Recipe_Model recipe_model = data.get(position);
            recipe_model.setFood_order(Boolean.FALSE);
            data.set(position, recipe_model);
        }
    }


    /**
     * This storeOrderedFood method stores the food items selected to the orderedFoodArrayList
     * @param position
     */
    public void storeOrderedFood(int position) {

//        orderedFoodMap.put(position, data.get(position));

        boolean insertCheck = db.insertData(data.get(position).getFood_name(), data.get(position).getFood_price(),
                data.get(position).getPic(), data.get(position).getFood_summery(), data.get(position).getRating());

        if(insertCheck == false) {
            Toast.makeText(context, "Enable to save into cart", Toast.LENGTH_SHORT).show();
        }
//        orderedFoodArrayList.add(new OrderedFood(data.get(position).getPic(),
//                data.get(position).getFood_price(),
//                data.get(position).getRating(),
//                data.get(position).getFood_name(),
//                data.get(position).getFood_summery()));
    }


    /**
     * This removeOrderedFood method removes the food item from the orderedFoodArrayList
     * @param position
     */
    public void removeOrderedFood(int position) {

//        orderedFoodMap.remove(position);

        boolean removeCheck = db.removeData(data.get(position).getFood_name());

        if (removeCheck == false) {
            Toast.makeText(context, "Enable to remove from cart", Toast.LENGTH_SHORT).show();
        }
//        orderedFoodArrayList.remove(position);
//        int count = 0;
//        for(int i =0; i<orderedFoodArrayList.size(); i++) {
//            count++;
//            Log.d("Ordered Food : " + i + " : ", orderedFoodArrayList.get(i).getFoodName());
//        }

        Toast.makeText(context, data.get(position).getFood_name(), Toast.LENGTH_LONG).show();
//        orderedFoodArrayList.remove(position);
    }


    @Override
    public int getItemCount() {
//        LENGTH = data.size();
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

//            addToCartBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (ADD_TO_CART_FLAG == 0) {
//
//                        /** This is used to keep track for the food items which are added to the cart **/
//                        if(orderedBoolean.get(getAdapterPosition()) == Boolean.FALSE) {
//                            storeOrderedFood();
//                            orderedBoolean.set(getAdapterPosition(), Boolean.TRUE);
//                        }
//                        else if(orderedBoolean.get(getAdapterPosition()) == Boolean.TRUE) {
//                            removeOrderedFood();
//                            orderedBoolean.set(getAdapterPosition(), Boolean.FALSE);
//                        }
//
//                        Toast.makeText(context, "Food Added to cart", Toast.LENGTH_SHORT).show();
//                        addToCartBtn.setText("ADDED TO CART");
//                        ADD_TO_CART_FLAG = 1;
//                    } else {
//                        Toast.makeText(context, "Food Removed from cart", Toast.LENGTH_SHORT).show();
//                        addToCartBtn.setText("ADD TO CART");
//                        ADD_TO_CART_FLAG = 0;
//                    }
//                }
//            });


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

//        public void storeOrderedFood() {
//
//            orderedFoodArrayList.add(new OrderedFood(data.get(getAdapterPosition()).getPic(),
//                    data.get(getAdapterPosition()).getFood_price(),
//                    data.get(getAdapterPosition()).getRating(),
//                    data.get(getAdapterPosition()).getFood_name(),
//                    data.get(getAdapterPosition()).getFood_summery()));
//        }
//
//        public void removeOrderedFood() {
//            orderedFoodArrayList.remove(getAdapterPosition());
//        }
    }

    public HashMap<Integer, Recipe_Model> returnOrderedData() {

        return (HashMap<Integer, Recipe_Model>) orderedFoodMap;
//        return orderedFoodArrayList;
    }
}
