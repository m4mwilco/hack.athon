package com.move4mobile.hack.athon.teamblue.network.model;

import android.content.ContentValues;

import com.move4mobile.hack.athon.teamblue.ProductContract;

/**
 * Created by Pepijn on 9-9-2016.
 */

public class Product {
    public long id;
    public String title;
    public String intro;
    public int price;
    public String image_url;

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        values.put(ProductContract._ID, id);
        values.put(ProductContract.NAME, title);
        values.put(ProductContract.DESCRIPTION, intro);
        values.put(ProductContract.PRICE, price);
        values.put(ProductContract.IMAGE, image_url);
        values.put(ProductContract.CATEGORY, "Default");
        return values;
    }
}
