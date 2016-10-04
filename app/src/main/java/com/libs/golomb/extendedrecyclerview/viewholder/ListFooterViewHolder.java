package com.libs.golomb.extendedrecyclerview.viewholder;

import android.view.View;
import android.widget.TextView;

import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.R;

/**
 * Created by golomb on 22/07/2016.
 * This class is a default footer view holder for the ExtendedRecycleView
 */
public class ListFooterViewHolder<T> extends ExtendedViewHolder<T>{

    private static final String MESSAGE = " Items";
    private final TextView mText;

    public ListFooterViewHolder(View itemView, ExtendedRecycleAdapter adapter) {
        super(itemView, adapter);
        mText = (TextView) itemView.findViewById(R.id.footer_text);
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
    public void bind(DataExtractor<T, ExtendedViewHolder<T>> mDataExtractor, int position, int itemType) {
        setText(mDataExtractor.size());
    }

    public void setText(String text){
        mText.setText(text);
    }

    public void setText(int numOfItem){
        setText(numOfItem + MESSAGE);
    }
}
