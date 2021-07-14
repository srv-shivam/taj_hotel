package com.example.tajhotel.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tajhotel.CustomClasses.Recipe_Model;
import com.example.tajhotel.LocalDataBase.DataBaseHelper;
import com.example.tajhotel.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CartFoodItemsAdapter extends RecyclerView.Adapter<CartFoodItemsAdapter.ViewHolder> {

    Context context;
    ArrayList<Recipe_Model> foodList;
    DataBaseHelper db;
    int sum = 0;
    Map<Integer, Integer> quantityMap;
    ArrayList<Integer> valuesList;

    public CartFoodItemsAdapter(Context context, ArrayList<Recipe_Model> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cart_orders_list_view, parent, false);
        db = new DataBaseHelper(context);
        quantityMap = new HashMap<>(foodList.size());

        for (int i = 0; i < foodList.size(); i++) {
            quantityMap.put(i, 1);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartFoodItemsAdapter.ViewHolder holder, int position) {

        holder.foodImage.setImageResource(foodList.get(position).getPic());
        holder.foodName.setText(foodList.get(position).getFood_name());
        holder.foodDescription.setText(foodList.get(position).getFood_summery());
        holder.foodPrice.setText("₹" + foodList.get(position).getFood_price());
        holder.foodRatings.setRating(foodList.get(position).getRating());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setCancelable(false);
                builder.setMessage("Do you really want to delete the item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // called deleteData method to remove data from database
                        boolean checkDelete = db.removeData(foodList.get(position).getFood_name());
                        if (checkDelete == true) {
                            foodList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, foodList.size());
                        } else {
                            dialog.cancel();
                            Toast.makeText(context, "Some error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create();
                builder.setTitle("Are you sure").show();
            }
        });


        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseFoodQuantity(holder.foodQuantity);
            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseFoodQuantity(holder.foodQuantity);
            }
        });


        holder.foodQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                quantityMap.replace(position, Integer.parseInt(holder.foodQuantity.getText().toString()));
            }
        });

    }

    private void decreaseFoodQuantity(TextView foodQuantity) {
        int i = Integer.parseInt(foodQuantity.getText().toString().trim());
        if (i < 2) {
            Toast.makeText(context, "You cannot have less than 1 quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        --i;
        foodQuantity.setText(String.valueOf(i));
    }

    private void increaseFoodQuantity(TextView foodQuantity) {
        int i = Integer.parseInt(foodQuantity.getText().toString().trim());
        if (i > 100) {
            Toast.makeText(context, "You cannot have more than 100 quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        ++i;
        foodQuantity.setText(String.valueOf(i));
    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }

    /**
     * This method is used to calculate the total quantity and then return it back to the desired place
     *
     * @return sum
     */
    public int returnTotalQuantity() {
        valuesList = (ArrayList<Integer>) quantityMap.values().stream().collect(Collectors.toList());
        sum = 0;
        for (int i = 0; i < valuesList.size(); i++) {
            sum += valuesList.get(i);
        }

        return sum;
    }

    /**
     * This method is used to calculate price with quantity multiplied to it and return back to desired place
     *
     * @return price
     */
    public int returnPrice() {
        int price = 0;

        for (int i = 0; i < foodList.size(); i++) {
            int p = Integer.parseInt(foodList.get(i).getFood_price().trim());
            price += price = p * valuesList.get(i);
        }
        return price;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImage, deleteImage;
        TextView foodName, foodDescription, foodPrice, foodQuantity;
        RatingBar foodRatings;
        Button plusButton, minusButton;

        public ViewHolder(View itemView) {
            super(itemView);

            this.foodImage = itemView.findViewById(R.id.cart_food_item_img);
            this.foodName = itemView.findViewById(R.id.cart_food_item_name);
            this.foodDescription = itemView.findViewById(R.id.cart_food_item_description);
            this.foodPrice = itemView.findViewById(R.id.cart_food_item_price);
            this.foodRatings = itemView.findViewById(R.id.cart_food_item_ratings);
            this.deleteImage = itemView.findViewById(R.id.cart_delete_btn);
            this.foodQuantity = itemView.findViewById(R.id.cart_food_item_quantity);
            this.plusButton = itemView.findViewById(R.id.cart_food_item_plus_btn);
            this.minusButton = itemView.findViewById(R.id.cart_food_item_minus_btn);
        }
    }

}
