package com.libs.golomb.extendedrecyclerview;

import android.app.Activity;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by tomer on 09/10/2016.
 * This class use annotation in order to simplified the implementation of the generator as a factory.
 */

public abstract class ViewHolderGenerator<T> implements IViewHolderGenerator<ExtendedViewHolder<T>> {
    private SparseArray<Method> factory;
    private SparseIntArray viewHeight;
    private boolean wasHeightComputed;
    private Activity mActivity;

    public ViewHolderGenerator(Activity activity) {
        mActivity = activity;
        factory = new SparseArray<>();
        viewHeight = new SparseIntArray();
        wasHeightComputed = false;
        prepareData();
    }

    public Activity getActivity() {
        return mActivity;
    }

    private void prepareData() {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyAnnotation.class)) {
                // do something
                MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
                Class<?>[] v = method.getParameterTypes();
                if (v.length != 2 ||
                        !ExtendedRecycleAdapter.class.isAssignableFrom(v[0]) ||
                        !ViewGroup.class.isAssignableFrom(v[1]) ||
                        !ExtendedViewHolder.class.isAssignableFrom(method.getReturnType())) {
                    throw new RuntimeException("generation method must be of the signature: (ExtendedRecycleAdapter,ViewGroup,int)");
                }
                factory.put(annotation.itemViewType(), method);
            }
        }
    }

    @SuppressWarnings({"TryWithIdenticalCatches", "unchecked"})
    protected void measureHeight(ViewGroup root) {
        for (int i = 0; i < factory.size(); i++) {
            int key = factory.keyAt(i);
            Method method = factory.get(key);
            try {
                ExtendedViewHolder viewHolder = (ExtendedViewHolder) method.invoke(this, null, root);
                viewHolder.itemView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                viewHeight.put(key, viewHolder.itemView.getMeasuredHeight());
                Log.d("Tgolomb", "Height of type " + key + " is " + viewHolder.itemView.getMeasuredHeight());
            } catch (IllegalAccessException e) {
                throw new RuntimeException("generation method must be of the signature: (ExtendedRecycleAdapter,ViewGroup,int)");
            } catch (InvocationTargetException e) {
                throw new RuntimeException("generation method must be of the signature: (ExtendedRecycleAdapter,ViewGroup,int)");
            }
        }
    }

    @SuppressWarnings({"TryWithIdenticalCatches", "unchecked"})
    private ExtendedViewHolder<T> createViewHolder(ExtendedRecycleAdapter adapter, ViewGroup parent, int viewType) {
        try {
            return (ExtendedViewHolder<T>) factory.get(viewType).invoke(this, adapter, parent);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("generation method must be of the signature: (ExtendedRecycleAdapter,ViewGroup,int)");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("generation method must be of the signature: (ExtendedRecycleAdapter,ViewGroup,int)");
        }
    }


    @Override
    public SparseIntArray getViewsHeight() {
        if (!wasHeightComputed) {
            measureHeight(new RelativeLayout(mActivity));
            wasHeightComputed = true;
        }
        return this.viewHeight;
    }

    @Override
    public ExtendedViewHolder<T> generate(ExtendedRecycleAdapter adapter, ViewGroup parent, int viewType) {
        return createViewHolder(adapter, parent, viewType);
    }
}
