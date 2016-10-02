package com.libs.golomb.extendedrecyclerview.example.viewholder;

import android.util.Log;
import android.view.View;

import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.ExtendedViewHolder;

/**
 * Created by tomer on 02/10/2016.
 */
public class StringEmptyViewHolder extends ExtendedViewHolder<String> {

    public StringEmptyViewHolder(View itemView, ExtendedRecycleAdapter adapter) {
        super(itemView, adapter);
    }

    @Override
    public boolean isSwappable() {
        return false;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public int getType() {
        return NO_ITEM;
    }

    @Override
    public void bind(String s, int i, int type) {
        Log.d("TGolomb","StringEmptyViewHolder.bind");
    }
}
