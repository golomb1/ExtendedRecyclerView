package com.libs.golomb.extendedrecyclerview.example.viewholder;

import android.util.Log;
import android.view.View;

import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.example.SampleData;
import com.libs.golomb.extendedrecyclerview.viewholder.ExtendedViewHolder;

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
    public void bind(DataExtractor<SampleData, ExtendedViewHolder<SampleData>> mDataExtractor, int position, int itemType) {
        Log.d("TGolomb","StringEmptyViewHolder.bind");
    }
}
