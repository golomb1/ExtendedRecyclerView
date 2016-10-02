package com.libs.golomb.extendedrecyclerview.example.viewholder;

import android.view.View;
import android.widget.TextView;

import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.ExtendedViewHolder;
import com.libs.golomb.extendedrecyclerview.R;

/**
 * Created by tomer on 02/10/2016.
 */
public class StringFooterViewHolder extends ExtendedViewHolder<String> {

    private final TextView mText;

    public StringFooterViewHolder(View itemView, ExtendedRecycleAdapter adapter) {
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
    public void bind(String s, int i, int type) {
        mText.setText(String.valueOf(s));
    }
}
