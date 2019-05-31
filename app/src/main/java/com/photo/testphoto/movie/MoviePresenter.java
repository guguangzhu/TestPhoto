package com.photo.testphoto.movie;

import android.content.Context;

import com.photo.testphoto.bean.Subject;
import com.photo.testphoto.bean.base.BaseBean;
import com.photo.testphoto.mvp.BasePresenterImpl;
import com.photo.testphoto.network.HttpUtil;
import com.photo.testphoto.network.SubscribeHandler;
import com.photo.testphoto.network.rxjava.ProgressSubscriber;
import com.photo.testphoto.network.rxjava.SubscriberOnNextListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MoviePresenter extends BasePresenterImpl<MovieContract.View> implements MovieContract.Presenter{


    @Override
    public void getMovie(int start, int count) {
        Observable ob = HttpUtil.getInstance().getTopMovies(0, 100);
        SubscriberOnNextListener<List<Subject>> listener=new SubscriberOnNextListener<List<Subject>>() {


            @Override
            public void onNext(List<Subject> listBaseBean) {
                mView.getMovieSuccess(listBaseBean);
            }

            @Override
            public void onFail(String err) {
                mView.getMovieFailed(err);
            }
        };
        SubscribeHandler.observeOn(ob,new ProgressSubscriber(listener, (Context) mView,false));

    }
}
