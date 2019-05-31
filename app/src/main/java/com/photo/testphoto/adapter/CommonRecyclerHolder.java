package com.photo.testphoto.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通用的RecyclerView的Holder
 *
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-07  16:30
 */

public class CommonRecyclerHolder extends RecyclerView.ViewHolder {
    public View mConvertView;
    public int position;
    private SparseArray<View> mViews;

    public CommonRecyclerHolder(View itemView) {
        super(itemView);
        this.mConvertView = itemView;
        this.mViews = new SparseArray<>();
    }


    /**
     * 得到item上的控件
     *
     * @param viewId 控件的id
     * @return 对应的控件
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;

    }

    public CommonRecyclerHolder setTextViewText(@IdRes int textViewId, String text) {
        TextView tv = getView(textViewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        } else {
            tv.setText(" ");
        }
        return this;
    }

    public CommonRecyclerHolder setTextColor(@IdRes int textViewId, int color) {
        TextView tv = getView(textViewId);
        tv.setTextColor(color);
        return this;
    }

    public CommonRecyclerHolder setBackgroundColor(@IdRes int textViewId, int color) {
        View view = getView(textViewId);
        if (view != null) {
            view.setBackgroundColor(color);
        }
        return this;
    }

    public CommonRecyclerHolder setImageResource(@IdRes int textViewId, int rId) {
        ImageView iv = getView(textViewId);
        if (rId != 0) {
            iv.setImageResource(rId);
        }else {
            iv.setImageResource(0);
        }
        return this;
    }

    public CommonRecyclerHolder setVisible(@IdRes int textViewId, boolean isVisible) {
        View view = getView(textViewId);
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }
        return this;
    }

    public CommonRecyclerHolder setOnClickListener(ListenerWithPosition.OnClickWithPositionListener clickListener, @IdRes int... viewIds) {
        ListenerWithPosition<CommonRecyclerHolder> listener = new ListenerWithPosition<>(position, this);
        listener.setOnClickListener(clickListener);
        for (int id : viewIds) {
            View v = getView(id);
            v.setOnClickListener(listener);
        }
        return this;
    }
}
