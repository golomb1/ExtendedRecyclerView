package com.libs.golomb.extendedrecyclerview;

import android.view.View;

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
    public void bind(T t, int i, int type) {
        //nothing
    }
}
