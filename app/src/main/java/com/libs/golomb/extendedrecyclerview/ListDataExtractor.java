package com.libs.golomb.extendedrecyclerview;

import java.util.Collections;
import java.util.List;

/**
 * Created by golomb on 13/07/2016.
 * Example
 */
public abstract class ListDataExtractor<T, VH extends ExtendedViewHolder<T>> implements DataExtractor<T,VH> {

    private final boolean header;
    private final boolean footer;
    protected List<? extends T> list;

    public ListDataExtractor(List<? extends T> list) {
        this(list,true,true);
    }


    public ListDataExtractor(List<? extends T> list,boolean header,boolean footer) {
        this.list = list;
        this.header = header;
        this.footer = footer;
    }



    @Override
    public int GetItemType(int position) {
        if(list.size() != 0) {
            if (header && position == 0)
                return HEADER;
            if ((header && position < list.size() + 1) || (!header && position < list.size()))
                return ITEM;
            else if(footer)
                return FOOTER;
        }
        return -1;
    }

    /**
     * TODO - add header, footer data
     * in order to add new types of elements, there is a need to override this function.
     * @param holder
     * @param position
     */
    @Override
    public void bindViewHolder(VH holder, int position) {
        int type = GetItemType(position);
        if(type == HEADER){
            holder.bind(null,position,type);
        }
        else if(type == ITEM){
            holder.bind(get(header ? position-1 : position),(header ? position-1 : position),type);
        }
        else if(type == FOOTER){
            holder.bind(null,position,type);
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T get(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ITEM;
    }

    @Override
    public void onItemMove(ExtendedRecycleAdapter<T> adapter, int fromPosition, int toPosition) {
        int swapCount = header ? 1 : 0;
        swapDataElement(fromPosition - swapCount,toPosition - swapCount);
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
        list.remove(position - 1);
        if(list.size() > 0)
            adapter.notifyItemRemoved(position);
        adapter.notifyDataSetChanged();
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
        if(size() == 0){
            return "";
        }
        else if(hasHeader()){
            if(position == 0){
                return "";
            }
            else if(hasFooter() && position == size() + 1){
                return "";
            }
        }
        else if(hasFooter() && position == size()){
            return "";
        }
        return getElementSectionName(get(hasHeader() ? position - 1 : position));
    }

    protected abstract String getElementSectionName(T item);
}
