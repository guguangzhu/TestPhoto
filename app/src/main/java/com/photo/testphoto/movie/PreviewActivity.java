package com.photo.testphoto.movie;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.photo.testphoto.ImageFragment;
import com.photo.testphoto.R;
import com.photo.testphoto.adapter.ImageFragmentAdapter;
import com.photo.testphoto.bean.BingImageBean;
import com.photo.testphoto.mvp.RxActivity;
import com.photo.testphoto.view.MyImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreviewActivity extends RxActivity {

//    @BindView(R.id.iv_detail)
//    MyImageView ivDetail;
    @BindView(R.id.layout_preview)
    LinearLayout layoutPreview;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    List<BingImageBean> mList;

    private List<Fragment> fragments = new ArrayList<>();

    ImageFragmentAdapter adapter;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);
        mList= (List<BingImageBean>) getIntent().getSerializableExtra("key_data");
        index=getIntent().getIntExtra("key_index",-1);

        if(mList==null||mList.isEmpty()){
            throw new IllegalArgumentException("参数不能为空");
        }

        for (int i = 0; i < mList.size(); i++) {
            fragments.add(ImageFragment.newInstance("https://cn.bing.com"+mList.get(i).getUrl(),index));
        }

        adapter=new ImageFragmentAdapter(getSupportFragmentManager(),fragments);


        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);



//        Glide.with(this)
//                .load("https://cn.bing.com"+mList.get(index).getUrl())
//                .into(ivDetail);
    }

    public void callFinish(){
        finish();
    }


    class SamplePagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            MyImageView photoView = new MyImageView(container.getContext(),position);
            photoView.setBackgroundColor(Color.BLACK);
//            ImageView iv = new ImageView(container.getContext());
//            iv.
//            iv.setScaleType(ImageView.ScaleType.FIT_XY);
//            ImageUtils.loadImage(photoView, imageUrlArray[position]);
//            ImageUtils.loadImage(photoView, "http://dynamic-image.yesky.com/740x-/uploadImages/2016/341/52/PADK5I4JU12B.jpg");
//            PhotoViewAttacher attacher = new PhotoViewAttacher(iv);
//            photoView.
//            photoView.setImageResource(iv);
//            photoView.`

            // Now just add PhotoView to ViewPager and return it
//            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            Glide.with(PreviewActivity.this).load("https://cn.bing.com"+mList.get(position).getUrl()).into(photoView);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

}
