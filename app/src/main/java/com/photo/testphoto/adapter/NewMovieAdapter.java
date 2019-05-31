package com.photo.testphoto.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.photo.testphoto.R;
import com.photo.testphoto.bean.Subject;
import com.photo.testphoto.utils.DeviceUtils;


public class NewMovieAdapter extends CommonRecyclerAdapter<Subject> {
    private SparseIntArray array=new SparseIntArray();
    private ListenerWithPosition.OnClickWithPositionListener listener;

    public NewMovieAdapter(Context context, ListenerWithPosition.OnClickWithPositionListener listener) {
        super(context, null, R.layout.item_movie);
        this.listener = listener;
    }

    @Override
    public void convert(final CommonRecyclerHolder holder, Subject data) {
        holder.setTextViewText(R.id.tv_name,data.getTitle());

        holder.setOnClickListener(listener,R.id.iv_image);

        if (array.get(holder.position) == 0){
//            GlideApp.with(mContext).asBitmap()
//                    .load(data.getImages().getLarge())
//                    .placeholder(R.mipmap.ic_launcher)
//                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//
//                        @Override
//                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            int height = resource.getHeight(); //获取bitmap信息，可赋值给外部变量操作，也可在此时行操作。
//                            holder.getView(R.id.iv_image).getLayoutParams().height= DeviceUtils.dip2px(mContext,height);
//                            array.put(holder.position,height);
//                        }
//
//
//                    });
        }else {
            holder.getView(R.id.iv_image).getLayoutParams().height=DeviceUtils.dip2px(mContext,array.get(holder.position));
        }

        Glide.with(mContext)
                .load(data.getImages().getLarge())
                .into((ImageView) holder.getView(R.id.iv_image));
    }
}
