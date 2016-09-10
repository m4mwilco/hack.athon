package com.move4mobile.hack.athon.teamblue;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.move4mobile.hack.athon.teamblue.databinding.ItemProductBinding;

/**
 * Created by Pepijn on 9-9-2016.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    public interface ProductClickListener {
        void onProductClicked(View v, long id);
    }

    private Cursor mCursor;
    private ProductClickListener mListener;

    public ProductAdapter(ProductClickListener listener) {
        mListener = listener;
        setHasStableIds(true);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemProductBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_product,
                parent,
                false
        );
        return new ProductViewHolder(binding.getRoot(), mListener);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.populate(mCursor);
    }

    public Cursor swapCursor(Cursor newCursor) {
        Cursor current = mCursor;
        mCursor = newCursor;
        notifyDataSetChanged();
        if (current != null) {
            current.close();
        }
        return current;
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(0);
    }

    @Override
    public int getItemCount() {
        return mCursor!=null?mCursor.getCount():0;
    }
}
