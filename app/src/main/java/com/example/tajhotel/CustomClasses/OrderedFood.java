package com.example.tajhotel.CustomClasses;

public class OrderedFood {

    private int foodImage;
    private float foodRatings;
    private String foodName, foodDescription, foodPrice;

    public OrderedFood(int foodImage, String foodPrice, float foodRatings, String foodName, String foodDescription) {
        this.foodImage = foodImage;
        this.foodPrice = foodPrice;
        this.foodRatings = foodRatings;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
    }

    public int getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(int foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public float getFoodRatings() {
        return foodRatings;
    }

    public void setFoodRatings(float foodRatings) {
        this.foodRatings = foodRatings;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }
}
