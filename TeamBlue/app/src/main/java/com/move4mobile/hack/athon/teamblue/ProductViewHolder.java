package com.move4mobile.hack.athon.teamblue;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.move4mobile.hack.athon.teamblue.databinding.ItemProductBinding;

/**
 * Created by Pepijn on 9-9-2016.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemProductBinding mBinding;
    private long mId;
    private ProductAdapter.ProductClickListener mListener;

    public ProductViewHolder(View itemView, ProductAdapter.ProductClickListener listener) {
        super(itemView);
        mBinding= DataBindingUtil.getBinding(itemView);
        mListener = listener;
        itemView.setOnClickListener(this);
    }

    public void populate(Cursor c) {
        mId = c.getLong(0);
        mBinding.title.setText(c.getString(1));
    }

    @Override
    public void onClick(View view) {
        if(mListener!=null) {
            mListener.onProductClicked(view, mId);
        }
    }
}
