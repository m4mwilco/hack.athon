package com.move4mobile.hack.athon.teamblue;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.move4mobile.hack.athon.teamblue.databinding.ActivityBaseBinding;


/**
 * Created by pepijntb on 31-08-16.
 */

public class BaseSubActivity extends AppCompatActivity {

    private ActivityBaseBinding mBinding;
    private BackPressedListener mBackPressedListener;

    public static <F extends Fragment> Intent getStartIntent(Context context, Class<F> fragmentClass, Bundle args, boolean transparentToolbar) {
        Intent intent = new Intent(Intent.ACTION_VIEW, null, context, BaseSubActivity.class);
        intent.putExtra("fragment", fragmentClass);
        intent.putExtra("fragmentArgs", args);
        intent.putExtra("transparentToolbar", transparentToolbar);
        return intent;
    }

    public static Intent getStartIntent(Context context, Fragment fragment, boolean transparentToolbar) {
        return getStartIntent(context, fragment.getClass(), fragment.getArguments(), transparentToolbar);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (getIntent().getBooleanExtra("transparentToolbar", false)) {
            mBinding.toolbar.setBackgroundColor(Color.TRANSPARENT);
            ((ViewGroup.MarginLayoutParams) mBinding.content.getLayoutParams()).topMargin = 0;
        }
        if (savedInstanceState == null) {
            Class fragmentClass = (Class<Fragment>) getIntent().getSerializableExtra("fragment");
            Bundle fragmentArgs = getIntent().getBundleExtra("fragmentArgs");
            Fragment fragment = Fragment.instantiate(this, fragmentClass.getCanonicalName(), fragmentArgs);
            if (fragment instanceof BackPressedListener) {
                mBackPressedListener = (BackPressedListener) fragment;
            }
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, fragment)
                    .commit();
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
            if (fragment instanceof BackPressedListener) {
                mBackPressedListener = (BackPressedListener) fragment;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mBackPressedListener != null) {
            if (mBackPressedListener.onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }
}
