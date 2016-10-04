package com.libs.golomb.extendedrecyclerview.DataExtractor;

import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.viewholder.ExtendedViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * Created by tomer on 04/10/2016.
 * Simple implementation of ListDataExtractor, this class support the following: header, footer, swapping, moving.
 */

public class SimpleListDataExtractor<T> implements DataExtractor<T,ExtendedViewHolder<T>> {

    public static final String NO_SECTION = "";
    private final boolean header;
    private final boolean footer;
    protected List<? extends T> list;

    public SimpleListDataExtractor(List<? extends T> list) {
        this(list,true,true);
    }


    public SimpleListDataExtractor(List<? extends T> list,boolean header,boolean footer) {
        this.list = list;
        this.header = header;
        this.footer = footer;
    }

    @Override
    public int getItemType(int position) {
        if(list.size() != 0) {
            if (hasHeader()) {
                if (position == 0) {
                    return HEADER;
                } else if (hasFooter() && position == list.size() + 1) {
                    return FOOTER;
                }
                else if(position > 0 && position < list.size() + 1){
                    return ITEM;
                }
            }
            else if(hasFooter() && position == list.size()){
                return FOOTER;
            }
            else if(position >= 0 && position < list.size()){
                return ITEM;
            }
        }
        else {
            return NO_ITEM;
        }
        return -1;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T getElement(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ITEM;
    }

    @Override
    public boolean hasFooter() {
        return footer;
    }

    @Override
    public boolean hasHeader() {
        return header;
    }

    @Override
    public String getSectionName(int position) {
        if(isItem(position)){
            return getElementSectionName(getAt(position));
        }
        return NO_SECTION;
    }

    @Override
    public boolean isItem(int position) {
        return isTypeOfItem(getItemType(position));
    }

    @Override
    public T getAt(int position) {
        return list.get(transferIndexToDataIndex(position));
    }

    @Override
    public int getMetaDataCount() {
        return (hasHeader() ? 1 : 0) + (hasFooter() ? 1 : 0);
    }

    protected boolean isTypeOfItem(int type) {
        return type == ITEM;
    }

    protected int transferIndexToDataIndex(int position) {
        return hasHeader() ? position-1 : position;
    }

    protected String getElementSectionName(T item){
        return "";
    }

    @Override
    public void onItemMove(ExtendedRecycleAdapter<T> adapter, int fromPosition, int toPosition) {
        swapDataElement(transferIndexToDataIndex(fromPosition),transferIndexToDataIndex(toPosition));
        adapter.notifyItemMoved(fromPosition, toPosition);
    }


    private void swapDataElement(int fromPosition, int toPosition){
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(list, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(list, i, i - 1);
            }
        }
    }

    @Override
    public void onItemSwap(ExtendedRecycleAdapter<T> adapter, int position) {
        removeItem(adapter,position);
    }

    protected void removeItem(ExtendedRecycleAdapter<T> adapter, int position){
        list.remove(transferIndexToDataIndex(position));
        if (list.size() > 0) {
            adapter.notifyItemRemoved(position);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
