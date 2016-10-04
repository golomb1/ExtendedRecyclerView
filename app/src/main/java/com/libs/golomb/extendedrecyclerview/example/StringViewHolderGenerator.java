package com.libs.golomb.extendedrecyclerview.example;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;
import com.libs.golomb.extendedrecyclerview.DataExtractor.SectionListDataExtractor;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.example.viewholder.StringSectionViewHolder;
import com.libs.golomb.extendedrecyclerview.viewholder.ExtendedViewHolder;
import com.libs.golomb.extendedrecyclerview.IViewHolderGenerator;
import com.libs.golomb.extendedrecyclerview.R;
import com.libs.golomb.extendedrecyclerview.example.viewholder.StringEmptyViewHolder;
import com.libs.golomb.extendedrecyclerview.example.viewholder.StringFooterViewHolder;
import com.libs.golomb.extendedrecyclerview.example.viewholder.StringHeaderViewHolder;
import com.libs.golomb.extendedrecyclerview.example.viewholder.StringViewHolder;


/**
 * Created by golomb on 13/07/2016.
 * Example
 */
public class StringViewHolderGenerator implements IViewHolderGenerator<ExtendedViewHolder<SampleData>> {

    private Activity mActivity;

    public StringViewHolderGenerator(Activity activity) {
        mActivity = activity;
    }

    @Override
    public ExtendedViewHolder<SampleData> generate(ExtendedRecycleAdapter adapter, ViewGroup parent, int viewType) {
        if(viewType == DataExtractor.HEADER) {
            View view = mActivity.getLayoutInflater().inflate(R.layout.header, parent, false);
            return new StringHeaderViewHolder(view, adapter);
        }
        else if(viewType == DataExtractor.FOOTER){
            View view = mActivity.getLayoutInflater().inflate(R.layout.footer, parent, false);
            return new StringFooterViewHolder(view, adapter);
        }
        else if(viewType == DataExtractor.ITEM){
            View view = mActivity.getLayoutInflater().inflate(R.layout.list_item, parent, false);
            return new StringViewHolder(view, adapter);
        }
        else if(viewType == DataExtractor.NO_ITEM){
            View view = mActivity.getLayoutInflater().inflate(R.layout.no_items, parent, false);
            return new StringEmptyViewHolder(view, adapter);
        }
        else if(viewType == SectionListDataExtractor.SECTION){
            View view = mActivity.getLayoutInflater().inflate(R.layout.section, parent, false);
            return new StringSectionViewHolder(view, adapter);
        }
        return null;
    }

    @Override
    public void bindEmptyView(ExtendedViewHolder<SampleData> holder) {
        // nothing to do
        Log.d("TGolomb","StringViewHolderGenerator.bindEmptyView");
    }
}

