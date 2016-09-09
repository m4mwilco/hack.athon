package com.move4mobile.hack.athon.teamblue;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.move4mobile.hack.athon.teamblue.databinding.FragmentProductListBinding;

/**
 * Created by Pepijn on 9-9-2016.
 */

public class ProductListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,ProductAdapter.ProductClickListener {

    private FragmentProductListBinding mBinding;
    private ProductAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ProductAdapter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_product_list,
                container,
                false
        );
        mBinding.recycler.setAdapter(mAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.setItemCount(-1);
        getLoaderManager().initLoader(0, getArguments(), this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                ProductContract.URI,
                new String[]{
                        ProductContract._ID,
                        ProductContract.NAME,
                        ProductContract.IMAGE,
                },
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        if(data!=null) {
            mBinding.setItemCount(data.getCount());
        } else {
            mBinding.setItemCount(-2);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onProductClicked(View v, long id) {
        Bundle args = new Bundle();
        args.putLong(ProductContract._ID, id);
        startActivity(BaseSubActivity.getStartIntent(getContext(), ProductDetailsFragment.class, args, false));
    }
}
