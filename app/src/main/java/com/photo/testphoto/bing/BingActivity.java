package com.photo.testphoto.bing;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.guge.imagemodule.PreviewActivity;
import com.photo.testphoto.R;
import com.photo.testphoto.adapter.BingImageAdapter;
import com.photo.testphoto.adapter.CommonRecyclerHolder;
import com.photo.testphoto.adapter.ListenerWithPosition;
import com.photo.testphoto.adapter.NewMovieAdapter;
import com.photo.testphoto.bean.BingImageBean;
import com.photo.testphoto.bean.Subject;
import com.photo.testphoto.mvp.MVPBaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vondear.rxtool.view.RxToast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BingActivity extends MVPBaseActivity<BingContract.View, BingPresenter> implements BingContract.View ,ListenerWithPosition.OnClickWithPositionListener<CommonRecyclerHolder>{

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    BingImageAdapter adapter;
    List<BingImageBean> mList = new ArrayList<>();
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        adapter = new BingImageAdapter(this, this);

        rvList.setLayoutManager(new GridLayoutManager(this,2));
        rvList.setAdapter(adapter);
//        mPresenter.getMovie(0, 20);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                mPresenter.getImage( 10);

            }

        });
        rvList.setAdapter(adapter);
        refreshLayout.autoRefresh();
    }


    @Override
    public void getImageSuccess(List<BingImageBean> list) {
        refreshLayout.finishRefresh();
        mList.clear();
        mList.addAll(list);
        adapter.setData(mList);
    }

    @Override
    public void getImageFailed(String message) {
        refreshLayout.finishRefresh();
        RxToast.error(message);
    }

    @Override
    public void onClick(View v, int position, CommonRecyclerHolder holder) {
        Intent intent=new Intent(this, PreviewActivity.class);
        List<String> list=new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            list.add("https://cn.bing.com"+mList.get(i).getUrl());
        }
        intent.putExtra("key_data", (Serializable) list);
        intent.putExtra("key_index",position);
        startActivity(intent);
    }
}
