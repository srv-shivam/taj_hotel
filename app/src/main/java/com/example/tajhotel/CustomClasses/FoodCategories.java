package com.example.tajhotel.CustomClasses;

public class FoodCategories {

    int image;
    String foodName;

    public FoodCategories(int image, String foodName) {
        this.image = image;
        this.foodName = foodName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

}
