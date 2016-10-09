package com.libs.golomb.exampleapp.viewholder;

import android.util.Log;
import android.view.View;

import com.libs.golomb.exampleapp.SampleData;
import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.ExtendedViewHolder;

/**
 * Created by tomer on 02/10/2016.
 * Example
 */
public class StringEmptyViewHolder extends ExtendedViewHolder<SampleData> {

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
    public void bind(DataExtractor<SampleData> mDataExtractor, int position, int itemType) {
        Log.d("TGolomb","StringEmptyViewHolder.bind");
    }

    @Override
    public void selectedMode() {

    }

    @Override
    public void unselectedMode() {

    }
}
