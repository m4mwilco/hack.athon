package com.move4mobile.hack.athon.teamblue.network;

import com.move4mobile.hack.athon.teamblue.network.model.Product;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @GET("/")
    Call<List<Product>> getProducts();

}
