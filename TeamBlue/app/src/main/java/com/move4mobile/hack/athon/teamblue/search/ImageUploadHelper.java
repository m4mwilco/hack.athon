package com.move4mobile.hack.athon.teamblue.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;

import com.move4mobile.hack.athon.teamblue.RetrofitManager;
import com.move4mobile.hack.athon.teamblue.network.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
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
        byte[] content = new byte[0];
        try {
            content = scaleImage(imageUri);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), content);
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
                Log.d("abc", "error", t);
                listener.onError();
            }
        });
    }

    private byte[] scaleImage(Uri file) throws IOException {


        int targetW = 1000;
        int targetH = 1000;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try (InputStream inputStream = fromUri(file)) {
            BitmapFactory.decodeStream(inputStream);
        }
        int photoW = options.outWidth;
        int photoH = options.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        Bitmap scaledBitmap = null;
        try (InputStream inputStream = fromUri(file)) {
            scaledBitmap = BitmapFactory.decodeStream(inputStream, new Rect(), options);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        return outputStream.toByteArray();
    }

    private InputStream fromUri(Uri uri) throws FileNotFoundException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);        return inputStream;
    }

    public interface Listener {
        void onResponse(List<Product> products);

        void onError();
    }

}
