package com.move4mobile.hack.athon.teamblue;

import android.content.ContentUris;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.move4mobile.hack.athon.teamblue.databinding.FragmentProductDetailsBinding;

/**
 * Created by Pepijn on 9-9-2016.
 */

public class ProductDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private FragmentProductDetailsBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_product_details,
                container,
                false
        );
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, getArguments(), this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = ContentUris.withAppendedId(ProductContract.URI, args.getLong(ProductContract._ID));
        return new CursorLoader(
                getContext(),
                uri,
                new String[] {
                        ProductContract._ID,
                        ProductContract.NAME,
                        ProductContract.DESCRIPTION,
                        ProductContract.PRICE,
                        ProductContract.IMAGE,
                },
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data!=null) {
            if(data.moveToFirst()) {
                populate(data);
            }
        }
    }

    private void populate(Cursor cursor) {
        mBinding.description.setText(cursor.getString(2));
        Glide.with(this).load(cursor.getString(4)).into(mBinding.image);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
