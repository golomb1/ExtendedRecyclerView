package com.libs.golomb.exampleapp.viewholder;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.libs.golomb.exampleapp.R;
import com.libs.golomb.exampleapp.SampleData;
import com.libs.golomb.extendedrecyclerview.DataExtractor.DataExtractor;
import com.libs.golomb.extendedrecyclerview.ExtendedRecycleAdapter;
import com.libs.golomb.extendedrecyclerview.ExtendedViewHolder;

/**
 * Created by golomb on 13/07/2016.
 * Example
 */
public class StringViewHolder extends ExtendedViewHolder<SampleData> {

    private TextView mText;

    public StringViewHolder(View itemView, ExtendedRecycleAdapter adapter) {
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
    public void bind(DataExtractor<SampleData> mDataExtractor, int position, int itemType) {
        mText.setText(mDataExtractor.getAt(position).toString());
    }

    @Override
    public void selectedMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            itemView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.light_gray,null));
        }
        else{
            itemView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.light_gray));
        }
    }

    @Override
    public void unselectedMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            itemView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.itemBG,null));
        }
        else{
            itemView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.itemBG));
        }
    }
}
