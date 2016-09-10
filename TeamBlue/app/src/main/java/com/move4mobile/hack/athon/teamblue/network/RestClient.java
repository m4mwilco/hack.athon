package com.move4mobile.hack.athon.teamblue.network;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.preference.PreferenceManager;

import com.move4mobile.hack.athon.teamblue.ProductContract;
import com.move4mobile.hack.athon.teamblue.RetrofitManager;
import com.move4mobile.hack.athon.teamblue.network.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Pepijn on 9-9-2016.
 */

public class RestClient {

    public static void storeProducts(Context context, List<Product> productList) {
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.delete(ProductContract.URI, null, null);
        if(productList!=null) {
            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                product.position = i;
            }
            ContentValues[] valueses = new ContentValues[productList.size()];
            for (int i = 0; i < valueses.length; i++) {
                valueses[i] = productList.get(i).toValues();
            }
            contentResolver.bulkInsert(ProductContract.URI, valueses);
        }
    }

    public static void refreshProducts(final Context context) {
        RetrofitManager.getApiService().getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()) {
                    storeProducts(context, response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
}
