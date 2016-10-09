package com.libs.golomb.exampleapp;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.libs.golomb.exampleapp.viewholder.StringEmptyViewHolder;
import com.libs.golomb.exampleapp.viewholder.StringFooterViewHolder;
import com.libs.golomb.exampleapp.viewholder.StringHeaderViewHolder;
import com.libs.golomb.exampleapp.viewholder.StringSectionViewHolder;
import com.libs.golomb.exampleapp.viewholder.StringViewHolder;
import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;
import com.libs.golomb.extendedrecyclerview.DataExtractor.SectionListDataExtractor;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.ExtendedViewHolder;
import com.libs.golomb.extendedrecyclerview.MyAnnotation;
import com.libs.golomb.extendedrecyclerview.ViewHolderGenerator;


/**
 * Created by golomb on 13/07/2016.
 * Example
 */
@SuppressWarnings("WeakerAccess")
public class StringViewHolderGenerator extends ViewHolderGenerator<SampleData> {

    public StringViewHolderGenerator(Activity activity) {
        super(activity);
    }

    /*
    ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            Log.d("Test", "" + view.getHeight());
                        }
                    }
                });
            }
     */

    @MyAnnotation(itemViewType = DataExtractor.HEADER)
    public ExtendedViewHolder<SampleData> generateHeader(ExtendedRecycleAdapter adapter, ViewGroup parent) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.header, parent, false);
        return new StringHeaderViewHolder(view, adapter);
    }

    @MyAnnotation(itemViewType = DataExtractor.FOOTER)
    public ExtendedViewHolder<SampleData> generateFooter(ExtendedRecycleAdapter adapter, ViewGroup parent) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.footer, parent, false);
        return new StringFooterViewHolder(view, adapter);
    }

    @MyAnnotation(itemViewType = DataExtractor.ITEM)
    public ExtendedViewHolder<SampleData> generateItem(ExtendedRecycleAdapter adapter, ViewGroup parent) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.list_item, parent, false);
        return new StringViewHolder(view, adapter);
    }

    @MyAnnotation(itemViewType = DataExtractor.NO_ITEM)
    public ExtendedViewHolder<SampleData> generateNoItem(ExtendedRecycleAdapter adapter, ViewGroup parent) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.no_items, parent, false);
        return new StringEmptyViewHolder(view, adapter);
    }

    @MyAnnotation(itemViewType = SectionListDataExtractor.SECTION)
    public ExtendedViewHolder<SampleData> generateSection(ExtendedRecycleAdapter adapter, ViewGroup parent) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.section, parent, false);
        return new StringSectionViewHolder(view, adapter);
    }

    @Override
    public void bindEmptyView(ExtendedViewHolder<SampleData> holder) {
        // nothing to do
        Log.d("TGolomb", "StringViewHolderGenerator.bindEmptyView");
    }
}

