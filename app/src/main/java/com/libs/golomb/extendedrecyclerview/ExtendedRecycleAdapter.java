package com.libs.golomb.extendedrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;
import com.libs.golomb.extendedrecyclerview.Utils.ExtendedTouchCallback;
import com.libs.golomb.extendedrecyclerview.Utils.ItemTouchHelperAdapter;
import com.libs.golomb.extendedrecyclerview.viewholder.ExtendedViewHolder;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

/**
 * Created by golomb on 13/07/2016.
 * This class represent an adapter
 */
public class ExtendedRecycleAdapter<T> extends RecyclerView.Adapter<ExtendedViewHolder<T>> implements ItemTouchHelperAdapter,FastScrollRecyclerView.SectionedAdapter  {

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

    private boolean hasElements() {
        return mDataExtractor != null && mDataExtractor.size() != 0;
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
        if(hasElements()) {
            int metadataCount = mDataExtractor.getMetaDataCount();
            return mDataExtractor.size() + metadataCount;
        }
        return 1;// No Item screen
    }

    /***
     * This method is relevant in case of difference type of items: item, header, footer
     * @param position - the position in the list
     * @return the type.
     */
    @Override
    public int getItemViewType(int position) {
        //We return an item if results are null or if the position is within the bounds of the results
        if (!hasElements()) {
            return DataExtractor.NO_ITEM;
        } else {
            return mDataExtractor.getItemType(position);
        }
    }

    /***
     * Create the relevant viewHolder
     */
    @Override
    public ExtendedViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        ExtendedViewHolder<T> viewHolder = mViewHolderGenerator.generate(this, parent, viewType);
        if(viewType == DataExtractor.HEADER) {
            mTouchCallback.setHeader(viewHolder);
        }
        if(viewType == DataExtractor.FOOTER) {
            mTouchCallback.setFooter(viewHolder);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ExtendedViewHolder<T> holder, int position) {
        if (hasElements()) {
            holder.bind(mDataExtractor,position,mDataExtractor.getItemType(position));
            // if listener is define then make sure that when click, the data item is given to the listener.
            if(mListener != null && mDataExtractor.isItem(position)) {
                final T item = mDataExtractor.getAt(position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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

    @NonNull
    @Override
    public String getSectionName(int position) {
        String result =  mDataExtractor.getSectionName(position);
        Log.d("Tgolomb",result);
        return result;
    }

    public interface OnClickListener<T> {
        void onClick(View view,T item);
    }
}

