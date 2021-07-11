package com.example.tajhotel.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tajhotel.CustomClasses.Recipe_Model;
import com.example.tajhotel.LocalDataBase.DataBaseHelper;
import com.example.tajhotel.R;

import java.util.ArrayList;

public class CartFoodItemsAdapter extends RecyclerView.Adapter<CartFoodItemsAdapter.ViewHolder> {

    Context context;
    ArrayList<Recipe_Model> foodList;
    DataBaseHelper db;

    public CartFoodItemsAdapter(Context context, ArrayList<Recipe_Model> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view  = layoutInflater.inflate(R.layout.cart_orders_list_view, parent, false);
        db = new DataBaseHelper(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartFoodItemsAdapter.ViewHolder holder, int position) {

        holder.foodImage.setImageResource(foodList.get(position).getPic());
        holder.foodName.setText(foodList.get(position).getFood_name());
        holder.foodDescription.setText(foodList.get(position).getFood_summery());
        holder.foodPrice.setText(foodList.get(position).getFood_price());
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
                        if(checkDelete==true) {
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
    }



    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImage, deleteImage;
        TextView foodName, foodDescription, foodPrice;
        RatingBar foodRatings;
        Spinner foodQuantity;
        public ViewHolder(View itemView) {
            super(itemView);

            this.foodImage = itemView.findViewById(R.id.cart_food_item_img);
            this.foodName = itemView.findViewById(R.id.cart_food_item_name);
            this.foodDescription = itemView.findViewById(R.id.cart_food_item_description);
            this.foodPrice = itemView.findViewById(R.id.cart_food_item_price);
            this.foodRatings = itemView.findViewById(R.id.cart_food_item_ratings);
            this.deleteImage = itemView.findViewById(R.id.cart_delete_btn);
            this.foodQuantity = itemView.findViewById(R.id.cart_food_item_quantity);


            ArrayAdapter<String> quantities = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,
                    context.getResources().getStringArray(R.array.Quantity));
            quantities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            foodQuantity.setAdapter(quantities);
        }
    }
}
