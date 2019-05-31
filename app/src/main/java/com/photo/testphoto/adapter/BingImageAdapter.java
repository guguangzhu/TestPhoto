package com.photo.testphoto.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.photo.testphoto.R;
import com.photo.testphoto.bean.BingImageBean;
import com.photo.testphoto.bean.Subject;
import com.photo.testphoto.utils.DeviceUtils;


public class BingImageAdapter extends CommonRecyclerAdapter<BingImageBean> {
    private SparseIntArray array=new SparseIntArray();
    private ListenerWithPosition.OnClickWithPositionListener listener;

    public BingImageAdapter(Context context, ListenerWithPosition.OnClickWithPositionListener listener) {
        super(context, null, R.layout.item_movie);
        this.listener = listener;
    }

    @Override
    public void convert(final CommonRecyclerHolder holder, BingImageBean data) {
        holder.setTextViewText(R.id.tv_name,data.getTitle());

        holder.setOnClickListener(listener,R.id.iv_image);


        Glide.with(mContext)
                .load("https://cn.bing.com"+data.getUrl())
                .into((ImageView) holder.getView(R.id.iv_image));
    }
}
