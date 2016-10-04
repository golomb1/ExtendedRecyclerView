package com.libs.golomb.extendedrecyclerview.example.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.example.SampleData;
import com.libs.golomb.extendedrecyclerview.viewholder.ExtendedViewHolder;
import com.libs.golomb.extendedrecyclerview.R;

/**
 * Created by tomer on 02/10/2016.
 * Example
 */
public class StringHeaderViewHolder  extends ExtendedViewHolder<SampleData> {

    private final TextView mText;

    public StringHeaderViewHolder(View itemView, ExtendedRecycleAdapter adapter) {
        super(itemView,adapter);
        mText = (TextView) itemView.findViewById(R.id.header_text);
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
        return HEADER;
    }

    @Override
    public void bind(DataExtractor<SampleData, ExtendedViewHolder<SampleData>> mDataExtractor, int position, int itemType) {
        mText.setText("Header of " + mDataExtractor.size() + " elements!");
    }
}
