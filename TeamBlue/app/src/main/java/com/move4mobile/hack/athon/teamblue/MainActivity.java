package com.move4mobile.hack.athon.teamblue;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.move4mobile.hack.athon.teamblue.databinding.ActivityMainBinding;
import com.move4mobile.hack.athon.teamblue.search.ImageHelper;
import com.move4mobile.hack.athon.teamblue.util.ListenableAppCompatActivity;

public class MainActivity extends ListenableAppCompatActivity implements ImageHelper.Listener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageHelper imageHelper = new ImageHelper(this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, new ProductListFragment())
                    .commit();
        }
    }

    @Override
    public void onImageResult(Uri imageUri) {
        Log.d(TAG, "Image received: " + imageUri);
    }
}
