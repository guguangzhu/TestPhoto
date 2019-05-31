package com.photo.testphoto.movie;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.photo.testphoto.R;
import com.photo.testphoto.adapter.CommonRecyclerHolder;
import com.photo.testphoto.adapter.ListenerWithPosition;
import com.photo.testphoto.adapter.MovieAdapter;
import com.photo.testphoto.adapter.MyMovieAdapter;
import com.photo.testphoto.adapter.NewMovieAdapter;
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
 * 邮箱 784787081@qq.com
 */

public class MovieActivity extends MVPBaseActivity<MovieContract.View, MoviePresenter> implements MovieContract.View, ListenerWithPosition.OnClickWithPositionListener<CommonRecyclerHolder> {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    NewMovieAdapter adapter;
    List<Subject> mList = new ArrayList<>();
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        adapter = new NewMovieAdapter(this, this);

        rvList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvList.setAdapter(adapter);
//        mPresenter.getMovie(0, 20);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                mPresenter.getMovie(0, 20);

            }

        });
        rvList.setAdapter(adapter);
        refreshLayout.autoRefresh();
    }

    @Override
    public void getMovieSuccess(List<Subject> listBaseBean) {
        refreshLayout.finishRefresh();
        mList.clear();
        mList.addAll(listBaseBean);
        adapter.setData(mList);

    }

    @Override
    public void getMovieFailed(String message) {
        refreshLayout.finishRefresh();

        RxToast.error(message);
    }

    @Override
    public void onClick(View v, int position, CommonRecyclerHolder holder) {
        Intent intent=new Intent(this,PreviewActivity.class);
        intent.putExtra("key_data", (Serializable) mList);
        intent.putExtra("key_index",position);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,holder.getView(R.id.iv_image),"movie_logo").toBundle());
    }

//    @OnClick(R.id.button)
//    public void onViewClicked() {
//        mPresenter.getMovie(0,20);
//    }

}
