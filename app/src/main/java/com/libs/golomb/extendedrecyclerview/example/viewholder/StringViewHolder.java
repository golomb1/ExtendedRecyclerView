package com.libs.golomb.extendedrecyclerview.example.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.example.SampleData;
import com.libs.golomb.extendedrecyclerview.viewholder.ExtendedViewHolder;
import com.libs.golomb.extendedrecyclerview.R;


/**
 * Created by golomb on 13/07/2016.
 * Example
 */
public class StringViewHolder extends ExtendedViewHolder<SampleData> {

    private TextView mText;

    public StringViewHolder(View itemView,ExtendedRecycleAdapter adapter) {
        super(itemView,adapter);
        ImageView mDragHolder = (ImageView) itemView.findViewById(R.id.drag_holder);
        setDragHolder(mDragHolder);
        mText = (TextView) itemView.findViewById(R.id.item_title);
    }


    @Override
    public boolean isSwappable() {
        return true;
    }

    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public int getType() {
        return ITEM;
    }

    @Override
    public void bind(DataExtractor<SampleData, ExtendedViewHolder<SampleData>> mDataExtractor, int position, int itemType) {
        mText.setText(mDataExtractor.getAt(position).toString());
    }
}
