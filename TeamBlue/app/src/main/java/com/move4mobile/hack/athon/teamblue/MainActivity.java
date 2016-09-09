package com.move4mobile.hack.athon.teamblue;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.move4mobile.hack.athon.teamblue.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

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
}
