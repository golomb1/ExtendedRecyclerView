package com.libs.golomb.extendedrecyclerview.example;

import com.libs.golomb.extendedrecyclerview.ExtendedViewHolder;
import com.libs.golomb.extendedrecyclerview.ListDataExtractor;

import java.util.List;

/**
 * Created by golomb on 13/07/2016.
 * Example
 */
public class StringDataExtractor extends ListDataExtractor<String,ExtendedViewHolder<String>>{

    public StringDataExtractor(List<String> list) {
        super(list);
    }

    public StringDataExtractor(List<String> list, boolean header, boolean footer) {
        super(list, header, footer);
    }

    public void addItem(String text) {
        if(list != null) {
            ((List<String>) list).add(text);
        }
    }

    @Override
    protected String getElementSectionName(String item) {
        return String.valueOf(item.charAt(0));
    }
}
