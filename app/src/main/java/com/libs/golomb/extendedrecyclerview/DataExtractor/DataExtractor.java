package com.libs.golomb.extendedrecyclerview.DataExtractor;

import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.viewholder.ExtendedViewHolder;

/**
 * Created by golomb on 13/07/2016.
 * represent a data source
 * TODO - include filters, groups
 * @param <T> the data type
 */
public interface DataExtractor<T,VH extends ExtendedViewHolder<T>>{

    public static final int ITEM = 0;
    public static final int NO_ITEM = 1;
    public static final int FOOTER = 2;
    public static final int HEADER = 3;

    /***
     * Get the item type of the item in the position position.
     * @param position of the item
     * @return the type, or -1 if invalid position
     */
    int getItemType(int position);

    /***
     * @return the size of all the data set, without the metadata elements.
     */
    int size();

    /***
     * This method return the item data in a given position,
     * this method does not include the headers or footer, only the items.
     * for example, position 0 is a data item even if there is a header.
     * @param position ot the item
     * @return the item
     */
    T getElement(int position);

    /**
     * @param position of the item
     * @return the id
     */
    long getItemId(int position);

    /**
     * What to do when item is moved or rearranged
     * @param adapter of the recycler view, for notifying and ect.
     * @param fromPosition the old position of the item.
     * @param toPosition the new position of the item.
     */
    void onItemMove(ExtendedRecycleAdapter<T> adapter, int fromPosition, int toPosition);

    /**
     * Handling what to do when an item is being swapped.
     * @param adapter of the recycler view for notifying and ect.
     * @param position of the item that was swapped.
     */
    void onItemSwap(ExtendedRecycleAdapter<T> adapter, int position);

    /**
     * is supporting footer
     */
    boolean hasFooter();

    /**
     * is supporting header
     */
    boolean hasHeader();

    /**
     * @param position of the given element.
     * @return true if the type of element in position is and item, or false if it is a metadata like header or footer.
     */
    boolean isItem(int position);

    /**
     * returns the element in a given position, from the set of the data element and the metadata.
     * @param position of the wanted element
     * @return the item.
     */
    T getAt(int position);


    String getSectionName(int position);

    int getMetaDataCount();


}
