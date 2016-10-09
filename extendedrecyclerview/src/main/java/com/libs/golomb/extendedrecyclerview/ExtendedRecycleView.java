package com.libs.golomb.extendedrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;

import com.libs.golomb.extendedrecyclerview.Utils.ExtendedTouchCallback;
import com.libs.golomb.extendedrecyclerview.Utils.ItemTouchHelperAdapter;
import com.libs.golomb.extendedrecyclerview.Scroller.FastScrollRecyclerView2;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by golomb on 13/07/2016.
 * This class should represent a recycle view with customs option build in.
 */
@SuppressWarnings("unused")
public class ExtendedRecycleView extends FastScrollRecyclerView2 {

    private Context mContext;
    private ItemTouchHelper mTouchHelper;
    private ItemTouchHelper.Callback mCallback;

    // list of the views that we want to show when the list is not empty.
    private List<View> mNonEmptyViews = Collections.emptyList();
    // list of the views that we want to show when the list is empty.
    private List<View> mEmptyViews = Collections.emptyList();

    private List<ExtendedRecycleAdapter> mRegistersAdapters;

    // the maximum number of element types that adapter can use.
    private int adapterTypeDomain = 100;

    // this represent a data observer, it
    private AdapterDataObserver mObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggleViews();
        }
    };

    /***************************************************************************************************************************************/
    /*                                                   Constructors                                                                      */
    /***************************************************************************************************************************************/

    public ExtendedRecycleView(Context context) {
        super(context);
        mRegistersAdapters = new ArrayList<>();
    }

    public ExtendedRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mRegistersAdapters = new ArrayList<>();
    }

    public ExtendedRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mRegistersAdapters = new ArrayList<>();
    }

    /***************************************************************************************************************************************/
    /*                                                      Methods                                                                        */
    /***************************************************************************************************************************************/

    /***************************************/
    /*          Register Adapters          */
    /***************************************/

    /**
     * This method set the number of distinct types of element that adapter can have, the default is 100.
     * This methods can only be called when there isn't any adapter in the recyclerview.
     * The minimum value for domain length is 5.
     * @param domainLength the length of distinct type that adapter can have.
     */
    public void setAdapterTypeDomainLength(int domainLength){
        if(!(getAdapter() == null && mRegistersAdapters.size() == 0)) {
            throw new IllegalStateException("setAdapterTypeDomainLength can only be called when there isn't any adapter in the recycler view");
        }
        else if(domainLength < 5){
            throw new InvalidParameterException("domain length minimum value is 5");
        }
        else{
            adapterTypeDomain = domainLength;
        }
    }

    /***
     * register new adapter for this recyclerview.
     * @param adapter to add.
     * @return the index of this adapter.
     */
    public int registerAdapter(ExtendedRecycleAdapter adapter){
        int index = mRegistersAdapters.size();
        mRegistersAdapters.add(index,adapter);
        adapter.setAdapterIndex(index * adapterTypeDomain);
        return index;
    }

    /***
     * unregister adapter from this recyclerview.
     * @param adapter to be removed
     * @return <tt>true</tt> if this list contained the specified element
     */
    public boolean unregisterAdapter(ExtendedRecycleAdapter adapter){
        return mRegistersAdapters.remove(adapter);
    }

    /**
     * unregister adapter from this recyclerview.
     * @param index of the adapter to remove.
     * @return the adapter.
     */
    public ExtendedRecycleAdapter unregisterAdapter(int index){
        return mRegistersAdapters.remove(index);
    }

    /**
     * @param index of the new adapter.
     * @param removeAndRecycleExistingViews If set to true, RecyclerView will recycle all existing
     *                                      Views. If adapters have stable ids and/or you want to
     *                                      animate the disappearing views, you may prefer to set
     *                                      this to false.
     * @param clear <tt>true</tt> if to clear all the recycled views that was in use until now,
     *              this will case the inflating of all the view from zero.
     */
    public void switchAdapter(int index,boolean removeAndRecycleExistingViews, boolean clear){
        ExtendedRecycleAdapter adapter = mRegistersAdapters.get(index);
        this.swapAdapter(adapter,removeAndRecycleExistingViews);
        if(clear){
            this.getRecycledViewPool().clear();
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mObserver);
            if(adapter instanceof ExtendedRecycleAdapter){
                ((ExtendedRecycleAdapter)adapter).setTouchHelper(mTouchHelper,mCallback);
                if(!mRegistersAdapters.contains(adapter)){
                    registerAdapter((ExtendedRecycleAdapter) adapter);
                }
            }
        }
        mObserver.onChanged();
    }








    private void toggleViews() {
        if (getAdapter() != null && !mEmptyViews.isEmpty() && !mNonEmptyViews.isEmpty()) {
            if (getAdapter().getItemCount() == 0) {

                //show all the empty views
                showViews(mEmptyViews);
                //hide the RecyclerView
                setVisibility(View.GONE);

                //hide all the views which are meant to be hidden
                hideViews(mNonEmptyViews);
            } else {
                //hide all the empty views
                showViews(mNonEmptyViews);

                //show the RecyclerView
                setVisibility(View.VISIBLE);

                //hide all the views which are meant to be hidden
                hideViews(mEmptyViews);
            }
        }
    }

    private void showViews(List<View> viewList) {
        for (View view : viewList) {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void hideViews(List<View> viewList) {
        for (View view : viewList) {
            view.setVisibility(View.GONE);
        }
    }


    public void hideIfEmpty(View... views) {
        mNonEmptyViews = Arrays.asList(views);
    }

    public void showIfEmpty(View... emptyViews) {
        mEmptyViews = Arrays.asList(emptyViews);
    }

    public void enableDefaultDivider(Context context, int orientation){
        //this.addItemDecoration(new DividerItemDecoration(context,orientation));
    }

    public void enableDefaultDivider(int orientation){
        this.enableDefaultDivider(mContext,orientation);
    }

    public void enableDefaultAnimation(){
        this.setItemAnimator(new DefaultItemAnimator());
    }

    public void enableDefaultTouchCallback(ItemTouchHelper.Callback callback){
        mTouchHelper = new ItemTouchHelper(callback);
        mCallback = callback;
        mTouchHelper.attachToRecyclerView(this);
        Adapter adapter = getAdapter();
        if(adapter != null && adapter instanceof ExtendedRecycleAdapter){
            ((ExtendedRecycleAdapter)adapter).setTouchHelper(mTouchHelper,mCallback);
        }
    }

    public void setDefaultLayoutManager(){
        setLayoutManager(new LinearLayoutManager(mContext));
    }









    public void initializeDefault(Context context, int orientation, ItemTouchHelperAdapter touchHelperAdapter, ExtendedRecycleAdapter adapter){
        mContext = context;
        //enableDefaultDivider(orientation);
        enableDefaultAnimation();
        enableDefaultTouchCallback(new ExtendedTouchCallback(touchHelperAdapter));
        setDefaultLayoutManager();
        setAdapter(adapter);
    }

    public void initializeDefault(Context context,int orientation,ExtendedRecycleAdapter adapter){
        initializeDefault(context,orientation,adapter,adapter);
    }

}
