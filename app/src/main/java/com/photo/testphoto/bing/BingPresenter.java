package com.photo.testphoto.bing;

import android.content.Context;

import com.photo.testphoto.bean.BingImageBean;
import com.photo.testphoto.bean.Subject;
import com.photo.testphoto.mvp.BasePresenterImpl;
import com.photo.testphoto.network.HttpUtil;
import com.photo.testphoto.network.SubscribeHandler;
import com.photo.testphoto.network.rxjava.ProgressSubscriber;
import com.photo.testphoto.network.rxjava.SubscriberOnNextListener;

import java.util.List;

import io.reactivex.Observable;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BingPresenter extends BasePresenterImpl<BingContract.View> implements BingContract.Presenter{

    @Override
    public void getImage(int count) {
        Observable ob = HttpUtil.getInstance().getBingImage( count);
        SubscriberOnNextListener<List<BingImageBean>> listener=new SubscriberOnNextListener<List<BingImageBean>>() {


            @Override
            public void onNext(List<BingImageBean> listBaseBean) {
                mView.getImageSuccess(listBaseBean);
            }

            @Override
            public void onFail(String err) {
                mView.getImageFailed(err);
            }
        };
        SubscribeHandler.observeOn(ob,new ProgressSubscriber(listener, (Context) mView,false));
    }
}
