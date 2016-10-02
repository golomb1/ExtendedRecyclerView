package com.libs.golomb.extendedrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by golomb on 13/07/2016.
 * This class represent an adapter
 */
public class ExtendedRecycleAdapter<T> extends RecyclerView.Adapter<ExtendedViewHolder<T>> implements ItemTouchHelperAdapter {

    private DataExtractor<T,ExtendedViewHolder<T>> mDataExtractor;
    private IViewHolderGenerator<ExtendedViewHolder<T>> mViewHolderGenerator;
    private ItemTouchHelper mTouchHelper;
    private ExtendedTouchCallback mTouchCallback;
    private OnClickListener<T> mListener;

    /***
     * Constructor
     * @param dataExtractor - the item DataExtractor for the list.
     * @param mViewHolderGenerator - generator for the viewHolder and theirs ids
     * @param listener - touch listener on every item
     */
    public ExtendedRecycleAdapter(DataExtractor<T,ExtendedViewHolder<T>> dataExtractor, IViewHolderGenerator<ExtendedViewHolder<T>> mViewHolderGenerator, OnClickListener<T> listener){
        this.mViewHolderGenerator = mViewHolderGenerator;
        this.mListener = listener;
        update(dataExtractor);
    }

    /***
     * Constructor
     * @param dataExtractor - the item DataExtractor for the list.
     * @param mViewHolderGenerator - generator for the viewHolder and theirs ids
     */
    public ExtendedRecycleAdapter(DataExtractor<T,ExtendedViewHolder<T>> dataExtractor, IViewHolderGenerator<ExtendedViewHolder<T>> mViewHolderGenerator){
        this(dataExtractor,mViewHolderGenerator,null);
    }

    /***
     * notify on updates in the collection of items.
     * @param dataExtractor the new collection.
     */
    public void update(DataExtractor<T,ExtendedViewHolder<T>> dataExtractor) {
        update(dataExtractor,mListener);
    }

    /***
     * notify on updates in the collection of items.
     * @param dataExtractor the new collection.
     * @param listener for click on item
     */
    public void update(DataExtractor<T,ExtendedViewHolder<T>> dataExtractor, OnClickListener<T> listener) {
        this.mListener = listener;
        mDataExtractor = dataExtractor;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        if (mDataExtractor != null && position < mDataExtractor.size()) {
            return mDataExtractor.getItemId(position);
        }
        return RecyclerView.NO_ID;
    }


    @Override
    public int getItemCount() {
        if(mDataExtractor != null && mDataExtractor.size() != 0) {
            int hasHeader = mDataExtractor.hasHeader() ? 1 : 0;
            int hasFooter = mDataExtractor.hasFooter() ? 1 : 0;
            return mDataExtractor.size() + hasHeader + hasFooter;
        }
        return 1;
    }

    /***
     * This method is relevant in case of difference type of items: item, playlist_header, footer
     * @param position - the position in the list
     * @return the type.
     */
    @Override
    public int getItemViewType(int position) {
        //We return an item if results are null or if the position is within the bounds of the results
        if (mDataExtractor == null || mDataExtractor.size() == 0) {
            return DataExtractor.NO_ITEM;
        } else {
            return mDataExtractor.GetItemType(position);
        }
    }

    /***
     * Create the relevant viewHolder
     */
    @Override
    public ExtendedViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        ExtendedViewHolder<T> viewHolder = mViewHolderGenerator.generate(this, parent, viewType);
        if(viewType == DataExtractor.HEADER)
            mTouchCallback.setHeader(viewHolder);
        if(viewType == DataExtractor.FOOTER)
            mTouchCallback.setFooter(viewHolder);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ExtendedViewHolder<T> holder, int position) {
        if (mDataExtractor != null && mDataExtractor.size() != 0) {
            mDataExtractor.bindViewHolder(holder, position);
            // if listener is define then make sure that when click, the data item is given to the listener.
            if(mListener != null && getItemViewType(position) == DataExtractor.ITEM) {
                final T item = mDataExtractor.get(mDataExtractor.hasHeader() ? position-1 : position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("TTTT","CLICKED!!!");
                        mListener.onClick(view, item);
                    }
                });
            }
        }
        else{
            mViewHolderGenerator.bindEmptyView(holder);
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        mDataExtractor.onItemMove(this,fromPosition,toPosition);
    }

    @Override
    public void onItemSwap(int position) {
        mDataExtractor.onItemSwap(this,position);
    }

    public void setTouchHelper(ItemTouchHelper mTouchHelper, ItemTouchHelper.Callback callback) {
        this.mTouchHelper = mTouchHelper;
        if(callback instanceof ExtendedTouchCallback) {
            this.mTouchCallback = (ExtendedTouchCallback) callback;
        }
    }

    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        if(mTouchHelper != null){
            this.mTouchHelper.startDrag(viewHolder);
        }
    }


    public interface OnClickListener<T> {
        void onClick(View view,T item);
    }
}

