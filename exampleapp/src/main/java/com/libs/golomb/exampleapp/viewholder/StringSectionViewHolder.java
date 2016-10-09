package com.libs.golomb.exampleapp.viewholder;


import android.view.View;
import android.widget.TextView;

import com.libs.golomb.exampleapp.R;
import com.libs.golomb.exampleapp.SampleData;
import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.ExtendedViewHolder;

/**
 * Created by tomer on 04/10/2016.
 * Example
 */
public class StringSectionViewHolder extends ExtendedViewHolder<SampleData> {

    private final TextView mText;

    public StringSectionViewHolder(View itemView, ExtendedRecycleAdapter adapter) {
        super(itemView, adapter);
        mText = (TextView) itemView.findViewById(R.id.section_text);
    }

    @Override
    public boolean isSwappable() {
        return false;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public int getType() {
        return FOOTER;
    }

    @Override
    public void bind(DataExtractor<SampleData> mDataExtractor, int position, int itemType) {
        mText.setText(mDataExtractor.getAt(position + 1).getSection());
    }

    @Override
    public void selectedMode() {

    }

    @Override
    public void unselectedMode() {

    }
}
