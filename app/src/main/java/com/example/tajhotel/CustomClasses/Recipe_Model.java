package com.example.tajhotel.CustomClasses;

public class Recipe_Model {
    int pic;
    String food_name, food_summery, food_price;
    Boolean food_order;
    float rating;

    public Recipe_Model(int pic, String food_name, String food_summery, Boolean food_order, String food_price, float rating) {
        this.pic = pic;
        this.food_name = food_name;
        this.food_summery = food_summery;
        this.food_order = food_order;
        this.food_price = food_price;
        this.rating = rating;
    }


    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_summery() {
        return food_summery;
    }

    public void setFood_summery(String food_summery) {
        this.food_summery = food_summery;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Boolean getFood_order() {
        return food_order;
    }

    public void setFood_order(Boolean food_order) {
        this.food_order = food_order;
    }

    public String getFood_price() {
        return food_price;
    }

    public void setFood_price(String food_price) {
        this.food_price = food_price;
    }
}
