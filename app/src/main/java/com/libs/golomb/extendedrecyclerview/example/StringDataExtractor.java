package com.libs.golomb.extendedrecyclerview.example;

import com.libs.golomb.extendedrecyclerview.DataExtractor.SectionListDataExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by golomb on 13/07/2016.
 * Example
 */
public class StringDataExtractor extends SectionListDataExtractor<String,SampleData> {

    public StringDataExtractor(List<SampleData> list) {
        super(list);
    }

    public StringDataExtractor(List<SampleData> list, boolean header, boolean footer) {
        super(list, header, footer);
    }


    @SuppressWarnings("unchecked")
    public void addItem(String s) {
        SampleData item = new SampleData(s);
        ((List<SampleData>)list).add(item);
        if(keyList.contains(s)){
            hashMap.get(s).add(item);
        }
        else{
            keyList.add(s);
            ArrayList<SampleData> tmp = new ArrayList<>();
            tmp.add(item);
            hashMap.put(s,tmp);
        }
    }
}
