package com.libs.golomb.extendedrecyclerview.DataExtractor;


import com.libs.golomb.ItemViewTypeAnnotation;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tomer on 04/10/2016.
 * Implementation of a SimpleListDataExtractor that support sections in the list
 */

public class SectionListDataExtractor<K,T extends SectionListDataExtractor.SectionElement<K>> extends SimpleListDataExtractor<T> {

    public static final int SECTION = 4;
    protected List<K> keyList;
    protected HashMap<K,List<T>> hashMap;

    public SectionListDataExtractor(List<? extends T> list) {
        super(list);
        prepareHash(list);
    }

    public SectionListDataExtractor(List<? extends T> list, boolean header, boolean footer) {
        super(list, header, footer);
        prepareHash(list);
    }


    @Override
    public int getItemType(int position) {
        if(hasHeader()){
            if(position == 0){
                return HEADER;
            }
            else if(hasFooter() && position == size() + keyList.size() + 1){
                return FOOTER;
            }
            else{
                return getDataType(position-1);
            }
        }
        else{
            if(hasFooter() && position == size() + keyList.size()){
                return FOOTER;
            }
            else{
                return getDataType(position);
            }
        }
    }

    @Override
    public T getAt(int position) {
        if(hasHeader()) {
            if (position == 0) {
                return null;
            } else {
                position--;
            }
        }
        for(K key : keyList){
            if(position == 0){
                return null;
            }
            else{
                position--;
                List<T> tmp = hashMap.get(key);
                if(position < tmp.size()){
                    return tmp.get(position);
                }
                else{
                    position -= tmp.size();
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }


    @Override
    public int getMetaDataCount() {
        return (hasHeader() ? 1 : 0) + (hasFooter() ? 1 : 0) + keyList.size();
    }

    @Override
    public int getItemTypeCount(int elementType) {
        if(elementType == SECTION){
            return keyList.size();
        }
        return super.getItemTypeCount(elementType);
    }

    @Override
    protected int transferIndexToDataIndex(int position) {
        T item = getAt(position);
        if(item == null){
            return -101;
        }
        else{
            return list.indexOf(item);
        }
    }

    @Override
    public String getSectionName(int position) {
        int type = getItemType(position);
        if(isTypeOfItem(type)){
            return getElementSectionName(getAt(position));
        }
        else if(type == SECTION){
            return getElementSectionName(getAt(position + 1));
        }
        return NO_SECTION;
    }

    @Override
    protected String getElementSectionName(T item){
        return item.getSectionName();
    }


    private int getDataType(int position){
        for(K key : keyList){
            if(position == 0){
                return SECTION;
            }
            else{
                position--;
                List<T> tmp = hashMap.get(key);
                if(position < tmp.size()){
                    return ITEM;
                }
                else{
                    position -= tmp.size();
                }
            }
        }
        throw new IndexOutOfBoundsException();
    }


    private void prepareHash(List<? extends T> list){
        HashMap<K,List<T>> hashMap = new HashMap<>();
        List<K> keyList = new ArrayList<>();
        for(T element : list){
            K key = element.getSection();
            if(hashMap.containsKey(key)){
                hashMap.get(key).add(element);
            }
            else{
                ArrayList<T> tmp = new ArrayList<>();
                tmp.add(element);
                hashMap.put(key,tmp);
                keyList.add(key);
            }
        }
        this.hashMap = hashMap;
        this.keyList = keyList;
    }

    protected void removeItem(ExtendedRecycleAdapter<T> adapter, int position) {
        T item = list.remove(transferIndexToDataIndex(position));
        List<T> tmp = hashMap.get(item.getSection());
        tmp.remove(item);
        if(tmp.size() == 0){
            hashMap.remove(item.getSection());
            keyList.remove(item.getSection());
            adapter.notifyItemRemoved(position - 1);
        }
        if (list.size() > 0) {
            adapter.notifyItemRemoved(position);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public interface SectionElement<K>{
        K getSection();
        String getSectionName();
    }
}
