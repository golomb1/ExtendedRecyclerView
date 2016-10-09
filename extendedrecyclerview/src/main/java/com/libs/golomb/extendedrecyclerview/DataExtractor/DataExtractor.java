package com.libs.golomb.extendedrecyclerview.DataExtractor;

import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;

/**
 * Created by golomb on 13/07/2016.
 * represent a data source
 * @param <T> the data type
 */
public interface DataExtractor<T>{

    int ITEM = 0;
    int NO_ITEM = 1;
    int FOOTER = 2;
    int HEADER = 3;

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

    /**
     * @param position of the item.
     * @return the section that the item in position is belong to.
     */
    String getSectionName(int position);

    /**
     * @return the number of element that aren't representing a item, like header, footer, sections and so on.
     */
    int getMetaDataCount();

    /**
     * @param elementType the type of the relevant element group.
     * @return the number of elements of this type.
     */
    int getItemTypeCount(int elementType);
}
