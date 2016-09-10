package com.move4mobile.hack.athon.teamblue.search;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.move4mobile.hack.athon.teamblue.RetrofitManager;
import com.move4mobile.hack.athon.teamblue.network.model.Product;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUploadHelper implements ImageHelper.Listener {

    private Context context;
    private Listener listener;

    public ImageUploadHelper(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }


    @Override
    public void onImageResult(Uri imageUri) {
        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(imageUri);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        RequestBody requestFile = RequestBodyUtil.create(MediaType.parse("multipart/form-data"), inputStream);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image[file]", "image.jpg", requestFile);
        RetrofitManager.getApiService().getProducts(body).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.d("abc", "response");
                if (response.isSuccessful()) {
                    listener.onResponse(response.body());
                } else {
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("abc", "error");
                listener.onError();
            }
        });
    }

    public interface Listener {
        void onResponse(List<Product> products);

        void onError();
    }

}
