package com.libs.golomb.extendedrecyclerview.example.viewholder;

import android.view.View;
import android.widget.TextView;

import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.R;
import com.libs.golomb.extendedrecyclerview.example.SampleData;
import com.libs.golomb.extendedrecyclerview.viewholder.ExtendedViewHolder;

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
    public void bind(DataExtractor<SampleData, ExtendedViewHolder<SampleData>> mDataExtractor, int position, int itemType) {
        mText.setText(mDataExtractor.getAt(position + 1).getSection());
    }
}
