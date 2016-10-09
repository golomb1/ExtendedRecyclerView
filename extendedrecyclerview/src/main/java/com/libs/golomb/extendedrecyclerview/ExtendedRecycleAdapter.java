package com.libs.golomb.extendedrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;
import com.libs.golomb.extendedrecyclerview.Utils.ExtendedTouchCallback;
import com.libs.golomb.extendedrecyclerview.Utils.ItemTouchHelperAdapter;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by golomb on 13/07/2016.
 * This class represent an adapter
 */
@SuppressWarnings("unused")
public class ExtendedRecycleAdapter<T> extends RecyclerView.Adapter<ExtendedViewHolder<T>> implements ItemTouchHelperAdapter,FastScrollRecyclerView.SectionedAdapter {

    private DataExtractor<T> mDataExtractor;
    private IViewHolderGenerator<ExtendedViewHolder<T>> mViewHolderGenerator;
    private ItemTouchHelper mTouchHelper;
    private ExtendedTouchCallback mTouchCallback;
    private OnClickListener<T> mListener;

    // Selection listener to call when selecting a item in selection Mode
    private SelectionListener<T> mLongListener;
    // is this adapter support multi-choice.
    private boolean multiChoice;
    // is the list is in selection mode.
    private boolean isInSelectionMode;
    // the list of chosen elements.
    private List<T> chosenElements;
    // the list of chosen element's indexes.
    private List<Integer> chosenElementsIndexes;

    // This index help to distinct between different adapters,
    // by adding this index to the calculation of each element type,
    // we can distinct between elements of every element.
    private int adapterIndex;


    /***************************************************************************************************************************************/
    /*                                                   Constructors                                                                      */
    /***************************************************************************************************************************************/


    /***
     * Constructor
     * @param dataExtractor - the item DataExtractor for the list.
     * @param mViewHolderGenerator - generator for the viewHolder and theirs ids
     * @param listener - touch listener on every item
     */
    public ExtendedRecycleAdapter(DataExtractor<T> dataExtractor, IViewHolderGenerator<ExtendedViewHolder<T>> mViewHolderGenerator, OnClickListener<T> listener){
        this.mViewHolderGenerator = mViewHolderGenerator;
        this.mListener = listener;
        this.multiChoice = false;
        this.isInSelectionMode = false;
        adapterIndex = 0;
        update(dataExtractor);
    }

    /***
     * Constructor
     * @param dataExtractor - the item DataExtractor for the list.
     * @param mViewHolderGenerator - generator for the viewHolder and theirs ids
     */
    public ExtendedRecycleAdapter(DataExtractor<T> dataExtractor, IViewHolderGenerator<ExtendedViewHolder<T>> mViewHolderGenerator){
        this(dataExtractor,mViewHolderGenerator,null);
    }


    /***************************************************************************************************************************************/
    /*                                                      Methods                                                                        */
    /***************************************************************************************************************************************/



    /***************************************/
    /*            Update adapter           */
    /***************************************/

    /***
     * notify on updates in the collection of items.
     * @param dataExtractor the new collection.
     */
    @SuppressWarnings("WeakerAccess")
    public void update(DataExtractor<T> dataExtractor) {
        update(dataExtractor,mListener);
    }

    /***
     * notify on updates in the collection of items.
     * @param dataExtractor the new collection.
     * @param listener for click on item
     */
    @SuppressWarnings("WeakerAccess")
    public void update(DataExtractor<T> dataExtractor, OnClickListener<T> listener) {
        this.mListener = listener;
        mDataExtractor = dataExtractor;
        notifyDataSetChanged();
    }


    /***************************************/
    /*           Multi selection           */
    /***************************************/


    /**
     * This make the adapter enable multi-choice mode, when the user will long click on a item, SelectionListener.enterSelectionMode is called.
     * when item is selected, the method SelectionListener.select will be called.
     * when item is undoSelection, the method SelectionListener.undoSelection will be called.
     * @param listener for selection events.
     */
    public void enableMultipleChoice(SelectionListener<T> listener){
        this.multiChoice = true;
        this.chosenElements = new ArrayList<>();
        this.chosenElementsIndexes = new ArrayList<>();
        this.mLongListener = listener;
    }

    /**
     * disable the multi-choice mode
     */
    public void disableMultipleChoice(){
        exitSelectionMode();
        this.multiChoice = false;
        this.chosenElements = null;
        this.chosenElementsIndexes = null;
        this.mLongListener = null;
    }

    /***
     * @return the multi-choice mode of this adapter.
     */
    public boolean isMultiChoice(){
        return multiChoice;
    }

    public boolean isInActiveSelectionMode(){
        return isInSelectionMode;
    }
    /**
     * @return the list of selected elements, null if the adapter is not in selectionMode.
     */
    public List<T> getChosenElements(){
        return this.chosenElements;
    }

    /**
     * force the adapter to exit selection mode
     */
    public void exitSelectionMode(){
        while(chosenElementsIndexes.size() > 0){
            int i = chosenElementsIndexes.remove(0);
            notifyItemChanged(i);
        }
        this.chosenElements.clear();
        this.chosenElementsIndexes.clear();
        isInSelectionMode = false;
    }

    /***************************************/
    /*         element Type & Id           */
    /***************************************/

    void setAdapterIndex(int adapterIndex) {
        this.adapterIndex = adapterIndex;
    }

    @Override
    public long getItemId(int position) {
        if (mDataExtractor != null && position < mDataExtractor.size()) {
            return mDataExtractor.getItemId(position);
        }
        return RecyclerView.NO_ID;
    }

    /***
     * This method is relevant in case of difference type of items: item, header, footer.
     * This method add adapterIndex to the type in order to distinct between elements of
     *             different adapters that use the same recyclerview
     * @param position - the position in the list
     * @return the type.
     */
    @Override
    public int getItemViewType(int position) {
        //We return an item if results are null or if the position is within the bounds of the results
        if (!hasElements()) {
            return adapterIndex + DataExtractor.NO_ITEM;
        } else {
            return adapterIndex + mDataExtractor.getItemType(position);
        }
    }

    /***************************************/
    /*         element Type & Id           */
    /***************************************/

    @Override
    public int getItemCount() {
        if(hasElements()) {
            int metadataCount = mDataExtractor.getMetaDataCount();
            return mDataExtractor.size() + metadataCount;
        }
        return 1;// No Item screen
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
    public void onBindViewHolder(final ExtendedViewHolder<T> holder, int position) {
        if (hasElements()) {
            holder.bind(mDataExtractor,position,mDataExtractor.getItemType(position));
            if(chosenElementsIndexes.contains(position)){
                holder.selectedMode();
            }
            else{
                holder.unselectedMode();
            }
            // if listener is define then make sure that when click, the data item is given to the listener.
            if((mListener != null || multiChoice) && mDataExtractor.isItem(position)) {
                final T item = mDataExtractor.getAt(position);
                holder.setListeners(position, item);
            }
        }
        else{
            mViewHolderGenerator.bindEmptyView(holder);
        }
    }


    void handleClick(ExtendedViewHolder<T> holder, View view, int fixedPosition, T item) {
        if (isInSelectionMode) {
            // if already select then undo selection of this element.
            if (chosenElementsIndexes.contains(fixedPosition)) {
                chosenElements.remove(item);
                chosenElementsIndexes.remove((Integer) fixedPosition);
                holder.unselectedMode();
                if (mLongListener != null) {
                    mLongListener.undoSelection(view, item);
                }
            } else { // select this element.
                chosenElements.add(item);
                chosenElementsIndexes.add(fixedPosition);
                holder.selectedMode();
                if (mLongListener != null) {
                    mLongListener.select(view, item);
                }
            }
        } else if (mListener != null) { // in normal mode call mListener
            mListener.onClick(view, item);
        }
    }

    boolean handleLongClick(ExtendedViewHolder<T> holder, View view, int fixedPosition, T item) {
        if (multiChoice && !isInSelectionMode) {
            isInSelectionMode = true;
            chosenElements.add(item);
            chosenElementsIndexes.add(fixedPosition);
            holder.selectedMode();
            if (mLongListener != null) {
                mLongListener.enterSelectionMode();
                mLongListener.select(view, item);
            }
            return true;
        }
        return false;
    }


    @SuppressWarnings("WeakerAccess")
    public void setTouchHelper(ItemTouchHelper mTouchHelper, ItemTouchHelper.Callback callback) {
        this.mTouchHelper = mTouchHelper;
        if(callback instanceof ExtendedTouchCallback) {
            this.mTouchCallback = (ExtendedTouchCallback) callback;
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


    /**
     * start a drag operation programmatic
     * @param viewHolder that being dragged.
     */
    @SuppressWarnings("WeakerAccess")
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        if(mTouchHelper != null){
            this.mTouchHelper.startDrag(viewHolder);
        }
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return mDataExtractor.getSectionName(position);
    }

    public boolean hasHeader(){
        return mDataExtractor != null && mDataExtractor.hasHeader();
    }

    public boolean hasFooter(){
        return mDataExtractor != null && mDataExtractor.hasFooter();
    }

    public boolean isHeader(int position){
        return mDataExtractor != null && mDataExtractor.getItemType(position) == DataExtractor.HEADER;
    }

    public boolean isFooter(int position){
        return mDataExtractor != null && mDataExtractor.getItemType(position) == DataExtractor.FOOTER;
    }

    public int computeListHeight(){
        SparseIntArray map = mViewHolderGenerator.getViewsHeight();
        int totalHeight = 0;
        for(int i=0; i < map.size(); i++){
            int elementType = map.keyAt(i);
            int itemHeight = map.get(i);
            int numOfElements = mDataExtractor.getItemTypeCount(elementType);
            totalHeight += (numOfElements * itemHeight);
        }
        return totalHeight;
    }
    /***************************************/
    /*            helper methods           */
    /***************************************/

    /**
     * @return if there elements in the dataExtractor.
     */
    private boolean hasElements() {
        return mDataExtractor != null && mDataExtractor.size() != 0;
    }


    /***************************************************************************************************************************************/
    /*                                                     Interfaces                                                                      */
    /***************************************************************************************************************************************/

    public interface OnClickListener<T> {
        void onClick(View view,T item);
    }

    public interface SelectionListener<T> {
        void select(View view, T item);

        void undoSelection(View view, T item);

        void enterSelectionMode();
    }
}