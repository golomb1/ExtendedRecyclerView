package com.libs.golomb.extendedrecyclerview.viewholder;

import android.view.View;

import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;

/**
 * Created by golomb on 22/07/2016.
 * This class represent a state where the list have no element, this view holder isn't swappable or movable.
 * @param <T> - The type element, the same as the rest of the view holder
 */
public class EmptyViewHolder<T> extends ExtendedViewHolder<T>{

    public EmptyViewHolder(View itemView, ExtendedRecycleAdapter adapter) {
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
    public void bind(DataExtractor<T, ExtendedViewHolder<T>> mDataExtractor, int position, int itemType) {
        //nothing
    }
}
