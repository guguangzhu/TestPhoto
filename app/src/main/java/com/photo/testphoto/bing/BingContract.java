package com.photo.testphoto.bing;

import android.content.Context;

import com.photo.testphoto.bean.BingImageBean;
import com.photo.testphoto.mvp.BasePresenter;
import com.photo.testphoto.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BingContract {
    interface View extends BaseView {
        void getImageSuccess(List<BingImageBean> list);


        void getImageFailed(String message);
    }

    interface  Presenter extends BasePresenter<View> {
        void getImage(int count);
    }
}
