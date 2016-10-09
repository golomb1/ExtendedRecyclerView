package com.libs.golomb.extendedrecyclerview;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;

/***
 * This class represent a version of view holder that support all the functionality of the ExtendedRecycleView
 * @param <T>
 */
public abstract class ExtendedViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    protected static final int ITEM = 0;
    protected static final int NO_ITEM = 1;
    protected static final int FOOTER = 2;
    protected static final int HEADER = 3;

    private ExtendedRecycleAdapter<T> mAdapter;
    private int mPosition;
    private T mItem;


    public ExtendedViewHolder(View itemView, ExtendedRecycleAdapter<T> adapter){
        super(itemView);
        mAdapter = adapter;
    }

    /**
     * this method is called when the user start to drag the view holder via the drag holder.
     */
    @SuppressWarnings("WeakerAccess")
    protected void onStartDrag(){
        mAdapter.onStartDrag(this);
    }

    /**
     * set up the drag holder and all the relevant listeners.
     * @param dragHolder to add.
     */
    @SuppressWarnings("WeakerAccess")
    public void setDragHolder(View dragHolder){
        if(dragHolder != null) {
            dragHolder.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int eventType = MotionEventCompat.getActionMasked(event);
                    if (eventType == MotionEvent.ACTION_DOWN || eventType == MotionEvent.ACTION_UP) {
                        onStartDrag();
                    }
                    return false;
                }
            });
        }
    }

    /**
     * @param position of the item related to this viewholder.
     * @param item the item that related to this viewholder.
     */
    void setListeners(int position, T item) {
        this.mPosition = position;
        this.mItem = item;
        if(itemView != null) {
            this.itemView.setOnClickListener(this);
            this.itemView.setOnLongClickListener(this);
        }
    }

    /**
     * @return the element that is tied to this viewholder.
     */
    @SuppressWarnings("unused")
    public T getBindItem() {
        return mItem;
    }

    /**
     * @return the element's position that is tied to this viewholder.
     */
    @SuppressWarnings("unused")
    public int getItemPosition(){
        return mPosition;
    }

    /***************************************/
    /*             Click Events            */
    /***************************************/

    /***
     * When click on a item, if adapter is set, then call it to handle it
     * This is to efficiently support multi-selection in a recycler view,
     *              and to support a extended version of onClickListener.
     * @param view that was long clicked.
     */
    @Override
    public void onClick(View view) {
        if(mAdapter != null){
            mAdapter.handleClick(this, view, mPosition, mItem);
        }
    }

    /***
     * When long click on a item, if adapter is set, then call it to handle it
     * This is to efficiently support multi-selection in a recycler view.
     * @param view that was long clicked.
     * @return <tt>true</tt> if the click was handle or false otherwise.
     */
    @Override
    public boolean onLongClick(View view) {
        return mAdapter != null && mAdapter.handleLongClick(this, view, mPosition, mItem);
    }

    /***************************************/
    /*             Click Events            */
    /***************************************/

    /***
     * @return <tt>true</tt> if this viewholder support swap to dismiss.
     */
    public abstract boolean isSwappable();

    /***
     * @return <tt>true</tt> if this viewholder support drag and rearrangement.
     */
    public abstract boolean isMovable();

    /**
     * @return the itemViewType of the item that being hold bt this viewholder.
     */
    public abstract int getType();

    /**
     * Bind element in a given position in dataExtractor to this view holder.
     * @param mDataExtractor that contains all the elements.
     * @param position of the item.
     * @param itemType of the item.
     */
    public abstract void bind(DataExtractor<T> mDataExtractor, int position, int itemType);

    /***
     * what to do when this item is selected as part of multi-selection.
     */
    public abstract void selectedMode();

    /***
     * what to do when this item is unselected as part of multi-selection.
     */
    public abstract void unselectedMode();
}
