package com.libs.golomb.extendedrecyclerview;

import android.view.View;
import android.widget.TextView;

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
    public void bind(T t, int i, int type) {
        mText.setText(String.valueOf(t));
    }

    public void setText(String text){
        mText.setText(text);
    }

    public void setText(int numOfItem){
        setText(numOfItem + MESSAGE);
    }
}
