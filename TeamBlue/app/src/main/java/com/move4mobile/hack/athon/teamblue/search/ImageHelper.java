package com.move4mobile.hack.athon.teamblue.search;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.move4mobile.hack.athon.teamblue.util.ListenableAppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageHelper extends ListenableAppCompatActivity.BaseListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = ImageHelper.class.getSimpleName();
    private static final String BUNDLE_CURRENT_URI = TAG + "current_uri";
    private Listener listener;
    private ListenableAppCompatActivity activity;

    private Uri currentPhotoUri;

    public ImageHelper(@NonNull Listener listener, @NonNull ListenableAppCompatActivity activity) {
        this.listener = listener;
        this.activity = activity;
        activity.addListener(this);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            Uri photoURI = null;
            try {
                photoURI = createImageFile();
            } catch (IOException e) {
                // Error occurred while creating the File
                e.printStackTrace();
            }

            if (photoURI != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void requestImage() {
        dispatchTakePictureIntent();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentPhotoUri = savedInstanceState.getParcelable(BUNDLE_CURRENT_URI);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Data: " + data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            listener.onImageResult(currentPhotoUri);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_CURRENT_URI, currentPhotoUri);
    }

    private Uri createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getCacheDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        Uri photoURI = FileProvider.getUriForFile(activity,
                "com.example.android.fileprovider",
                image);
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoUri = photoURI;
        return photoURI;
    }

    public interface Listener {

        void onImageResult(Uri imageUri);

    }

}
