package com.libs.golomb.extendedrecyclerview.example.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.ExtendedViewHolder;
import com.libs.golomb.extendedrecyclerview.R;


/**
 * Created by golomb on 13/07/2016.
 * Example
 */
public class StringViewHolder extends ExtendedViewHolder<String> {

    public TextView mText;

    public StringViewHolder(View itemView,ExtendedRecycleAdapter adapter) {
        super(itemView,adapter);
        ImageView mDragHolder = (ImageView) itemView.findViewById(R.id.drag_holder);
        setDragHolder(mDragHolder);
        mText = (TextView) itemView.findViewById(R.id.item_title);
        int a = 5;
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
    public void bind(String s, int i, int type) {
        mText.setText(s);
    }
}
