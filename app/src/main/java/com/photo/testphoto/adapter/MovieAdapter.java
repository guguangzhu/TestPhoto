package com.photo.testphoto.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.photo.testphoto.R;
import com.photo.testphoto.bean.Subject;
import com.photo.testphoto.utils.DeviceUtils;

import java.util.List;
import java.util.Random;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder>{

    private List<Subject> resIds;
    private Context context;
    private Random random=new Random();
    private SparseIntArray array=new SparseIntArray();

    public MovieAdapter(Context context,List<Subject> resIds){
        this.resIds = resIds;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.textView.setText(resIds.get(position).getTitle());

        if (array.get(position) == 0){
//            GlideApp.with(context).asBitmap()
//                    .load(resIds.get(position).getImages().getLarge())
//                    .placeholder(R.mipmap.ic_launcher)
//                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//
//                        @Override
//                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            int height = resource.getHeight(); //获取bitmap信息，可赋值给外部变量操作，也可在此时行操作。
//                            holder.imageView.getLayoutParams().height= RxImageTool.dp2px(height);
//                            array.put(position,height);
//                        }
//
//
//                    });
        }else {
            holder.imageView.getLayoutParams().height=DeviceUtils.dip2px(context,array.get(position));
        }

        Glide.with(context)
                .load(resIds.get(position).getImages().getLarge())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return resIds.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
            textView = itemView.findViewById(R.id.tv_name);
        }

    }



}