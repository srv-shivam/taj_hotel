package com.example.tajhotel.LocalDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {


    public DataBaseHelper(Context context) {
        super(context, "FoodItemDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table CartFoods(foodname TEXT primary key, foodprice TEXT, foodphoto INTEGER, " +
                "fooddescription TEXT, foodratings REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists CartFoods");
        onCreate(db);
    }

    public boolean insertData(String foodName, String foodPrice, int foodPhoto, String foodDescription, float foodRating) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("foodname", foodName);
        contentValues.put("foodprice", foodPrice);
        contentValues.put("foodphoto", foodPhoto);
        contentValues.put("fooddescription", foodDescription);
        contentValues.put("foodratings", foodRating);

        long flag = sqLiteDatabase.insert("CartFoods", null, contentValues);

        if (flag == -1) return false;
        else return true;
    }

    public boolean removeData(String foodName) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int flag = sqLiteDatabase.delete("CartFoods", "foodname=?", new String[] {foodName});

        if (flag == -1) return true;
        else return true;
    }

    public Cursor retrieveData() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from CartFoods", null);
        return cursor;
    }

}
