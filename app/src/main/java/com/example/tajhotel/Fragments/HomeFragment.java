package com.example.tajhotel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.tajhotel.Adapters.HomeCategoriesAdapter;
import com.example.tajhotel.Adapters.Recipe_Adapter;
import com.example.tajhotel.CustomClasses.FoodCategories;
import com.example.tajhotel.CustomClasses.Recipe_Model;
import com.example.tajhotel.LocalDataBase.DataBaseHelper;
import com.example.tajhotel.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    DataBaseHelper db;
    private ArrayList<FoodCategories> categoriesArrayList;
    private HomeCategoriesAdapter homeCategoriesAdapter;
    private RecyclerView recyclerView;
    private ImageSlider imageSlider, imageSliderBottom;
    private RecyclerView recyclerViewFoodList;
    private TextView topPicks, customerReviews;
    private Recipe_Adapter recipe_adapter;
    private int RECYCLER_VISIBILITY0 = 0, RECYCLER_VISIBILITY1 = 0, RECYCLER_VISIBILITY2 = 0, RECYCLER_VISIBILITY3 = 0;
    private Animation slideLEFT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        categoriesArrayList = new ArrayList<>();
        recyclerView = rootView.findViewById(R.id.categories_recycler_view);
        recyclerViewFoodList = rootView.findViewById(R.id.food_item_display_recycler_view);
        topPicks = rootView.findViewById(R.id.top_picks);
        customerReviews = rootView.findViewById(R.id.customer_reviews);

        /** Initialization of Local database **/
        db = new DataBaseHelper(getContext());

        // Animation for food list
        slideLEFT = AnimationUtils.loadAnimation(getContext(), R.anim.food_list_recycler_animation);

        //Adding different Food Categories in ArrayList
        categoriesArrayList.add(new FoodCategories(R.drawable.burger_categories, "Burger"));
        categoriesArrayList.add(new FoodCategories(R.drawable.noodles_categories, "Noodles"));
        categoriesArrayList.add(new FoodCategories(R.drawable.chicken_categories, "Chicken"));
        categoriesArrayList.add(new FoodCategories(R.drawable.pizza_categories, "Pizza"));

        homeCategoriesAdapter = new HomeCategoriesAdapter(getContext(), categoriesArrayList, new HomeCategoriesAdapter.OnCategoryClick() {
            @Override
            public void onClick(int pos) {
                controlCategoriesFoodListDisplay(pos);
                setFoodItem(pos);
            }

        });

        homeCategoriesAdapter.notifyDataSetChanged();

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(homeCategoriesAdapter);

        imageSlider = rootView.findViewById(R.id.banner_slider);
        imageSliderBottom = rootView.findViewById(R.id.banner_slider_down);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://pixelz.cc/wp-content/uploads/2018/12/hamburgers-with-fire-uhd-4k-wallpaper.jpg", "Let's explore the spices"));
        slideModels.add(new SlideModel("https://images.creativemarket.com/0.1.0/ps/5829524/1820/1213/m1/fpnw/wm1/awu82uoqwx3lgrsj2omlbzhzouszk713n45h7c4cpor2my2zlpqyb34pt2ogzhep-.jpg?1549379772&s=beb2804ea962fb49e6242654b23c6cd5", "Hunger station"));
        slideModels.add(new SlideModel("https://pluspng.com/img-png/restaurant-png-hd--1920.png", "Taj Hotel brings you more"));
        slideModels.add(new SlideModel("https://images.creativemarket.com/0.1.0/ps/3379855/910/647/m2/fpnw/wm1/q8mbhyffbveq5rn80tfc6z1glvbvwspe2ztxajpe3pkhgvitci56eo9qjscpvj7s-.jpg?1507495664&s=9f9420263f473c06dffd55135d685013", "Explore More, Eat More"));
        slideModels.add(new SlideModel("https://shilpaahuja.com/wp-content/uploads/2017/08/best-vegetarian-food-chennai-india-tamil-nadu-indian-tasty.jpg", "Authentic foods for your loved ones"));
        imageSlider.setImageList(slideModels, true);
        imageSliderBottom.setImageList(slideModels, true);


        return rootView;
    }


    /**
     * controlCategoriesFoodListDisplay method is used to display the particular Categories food list
     *
     * @param pos
     */
    void controlCategoriesFoodListDisplay(int pos) {
        switch (pos) {
            case 0:
                RECYCLER_VISIBILITY0 = visibilityActions(RECYCLER_VISIBILITY0);
                RECYCLER_VISIBILITY1 = 0;
                RECYCLER_VISIBILITY2 = 0;
                RECYCLER_VISIBILITY3 = 0;
                break;
            case 1:
                RECYCLER_VISIBILITY1 = visibilityActions(RECYCLER_VISIBILITY1);
                RECYCLER_VISIBILITY0 = 0;
                RECYCLER_VISIBILITY2 = 0;
                RECYCLER_VISIBILITY3 = 0;
                break;
            case 2:
                RECYCLER_VISIBILITY2 = visibilityActions(RECYCLER_VISIBILITY2);
                RECYCLER_VISIBILITY0 = 0;
                RECYCLER_VISIBILITY1 = 0;
                RECYCLER_VISIBILITY3 = 0;
                break;
            case 3:
                RECYCLER_VISIBILITY3 = visibilityActions(RECYCLER_VISIBILITY3);
                RECYCLER_VISIBILITY0 = 0;
                RECYCLER_VISIBILITY1 = 0;
                RECYCLER_VISIBILITY2 = 0;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pos);
        }
    }


    /**
     * visibilityActions method is used to control the visibilities of recycler view and customer reviews
     *
     * @param RECYCLER_VISIBILITY
     * @return RECYCLER_VISIBILITY
     */
    private int visibilityActions(int RECYCLER_VISIBILITY) {

        if (RECYCLER_VISIBILITY == 0) {

            topPicks.setAnimation(slideLEFT);
            recyclerViewFoodList.startAnimation(slideLEFT);

            topPicks.setVisibility(View.VISIBLE);
            recyclerViewFoodList.setVisibility(View.VISIBLE);
            customerReviews.setVisibility(View.GONE);
            imageSliderBottom.setVisibility(View.GONE);
            RECYCLER_VISIBILITY = 1;
        } else {
            topPicks.setVisibility(View.GONE);
            recyclerViewFoodList.setVisibility(View.GONE);
            customerReviews.setVisibility(View.VISIBLE);
            imageSliderBottom.setVisibility(View.VISIBLE);
            RECYCLER_VISIBILITY = 0;
        }

        return RECYCLER_VISIBILITY;
    }


    private void setFoodItem(int pos) {

        List<Recipe_Model> list = new ArrayList<>();

        switch (pos) {
            case 0:
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Cheese Burger", "This is Cheese Burger", Boolean.FALSE, "10", 4));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Caramelized Onion Burger", "This is Caramelized Onion Burger", Boolean.FALSE, "3", 4));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Chicken Burger", "This is USA burgger", Boolean.FALSE, "10", 1));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Mushroom Burger", "This is Mushroom Burger", Boolean.FALSE, "22", 5));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Aloo Tikki Burger", "This is Aloo Tikki Burger", Boolean.FALSE, "6", 2));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Paneer Burger", "This is Paneer Burger", Boolean.FALSE, "20", 3));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Red Chilli Burger", "This is Red Chilli Burger", Boolean.FALSE, "13", 4));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Hamburger", "This is Hamburger", Boolean.FALSE, "4", 2));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Double Cheese Burger", "This is Cheese Burger", Boolean.FALSE, "3", 5));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Double-Decker Burgers", "This is Double-Decker Burgers", Boolean.FALSE, "10", 5));
                break;

            case 1:
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Spicy Noodles", "This is Spicy Noodles", Boolean.FALSE, "3", 4));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Soup Noodles", "This is Soup Noodles", Boolean.FALSE, "3", 4));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Cheese Noodles", "This is Cheese Noodles", Boolean.FALSE, "10", 1));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Italian Noodles", "This is Italian Noodles", Boolean.FALSE, "22", 5));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Chicken Noodles", "This is Chicken Noodles", Boolean.FALSE, "3", 2));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Masala Noodles", "This is Masala Noodles", Boolean.FALSE, "3", 3));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Simple Noodles", "This is Simple Noodles", Boolean.FALSE, "3", 4));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Double Cheese Noodles", "This is Double Cheese Noodles", Boolean.FALSE, "3", 2));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Paneer Noodles", "This is Paneer Noodles", Boolean.FALSE, "3", 5));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Corn Noodles", "This is Corn Noodles", Boolean.FALSE, "3", 5));
                break;

            case 2:
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Butter Chicken", "This is Butter Chicken", Boolean.FALSE, "6", 4));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Chicken Dum Biryani", "This is Chicken Dum Biryani", Boolean.FALSE, "3", 4));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Roasted Chicken Masala", "This is Roasted Chicken Masala", Boolean.FALSE, "10", 1));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Spicy Chicken 65", "This is Spicy Chicken 65", Boolean.FALSE, "22", 5));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Tandoori Chicken", "This is Tandoori Chicken", Boolean.FALSE, "3", 2));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Chicken Tikka Masala", "This is Chicken Tikka Masala", Boolean.FALSE, "3", 3));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Mughlai Chicken", "This is Mughlai Chicken", Boolean.FALSE, "3", 4));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Spicy Chicken Curry", "This is Spicy Chicken Curry", Boolean.FALSE, "3", 2));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Chicken Biryani", "This is Chicken Biryani", Boolean.FALSE, "3", 5));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Chicken Kebab", "This is Chicken Kebab", Boolean.FALSE, "3", 5));
                break;

            case 3:
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Pepperoni Pizza", "This is Pepperoni Pizza", Boolean.FALSE, "12", 4));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Multigrain Pizza", "This is Multigrain Pizza", Boolean.FALSE, "3", 4));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Chicken Pizza", "This is Chicken Pizza", Boolean.FALSE, "10", 1));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Margherita Pizza", "This is Margherita Pizza", Boolean.FALSE, "22", 5));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Cheese Burst Pizza", "This is JCheese Burst Pizza", Boolean.FALSE, "3", 2));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Mushroom Pizza", "This is Mushroom Pizza", Boolean.FALSE, "3", 3));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Mozzarella Pizza", "This is Mozzarella Pizza", Boolean.FALSE, "3", 4));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Hawaiian Pizza", "This is Hawaiian Pizza", Boolean.FALSE, "3", 2));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Paneer Top Pizza", "This is Paneer Top Pizza", Boolean.FALSE, "3", 5));
                list.add(new Recipe_Model(R.drawable.mutton_biriyani, "Extra Cheese Pizza", "This is Extra Cheese Pizza", Boolean.FALSE, "3", 5));
                break;
        }

        recipe_adapter = new Recipe_Adapter(getContext(), list, new DataBaseHelper(getContext()));
        recyclerViewFoodList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        recyclerViewFoodList.setAdapter(recipe_adapter);

    }

}