package com.libs.golomb.extendedrecyclerview;

import android.util.SparseIntArray;
import android.view.ViewGroup;

import java.util.Map;

/**
 * Created by golomb on 13/07/2016.
 * This class represent the logic of creating a view holder
 */
public interface IViewHolderGenerator<VH extends ExtendedViewHolder> {

    /**
     * @param adapter = the recycle adapter
     * @param parent = the parent view of the view holder
     * @param viewType = the element type.
     * @return the view holder
     */
    VH generate(ExtendedRecycleAdapter adapter, ViewGroup parent, int viewType);

    /**
     * bind the view holder for empty data set
     * @param holder - the view holder
     */
    void bindEmptyView(VH holder);

    /**
     * @return a mapping of each view type to it's size.
     */
    SparseIntArray getViewsHeight();
}
