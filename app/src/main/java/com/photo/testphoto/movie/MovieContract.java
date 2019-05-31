package com.photo.testphoto.movie;

import android.content.Context;

import com.photo.testphoto.bean.Subject;
import com.photo.testphoto.bean.base.BaseBean;
import com.photo.testphoto.mvp.BasePresenter;
import com.photo.testphoto.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MovieContract {
    interface View extends BaseView {
        void getMovieSuccess(List<Subject> listBaseBean);


        void getMovieFailed(String message);
    }

    interface  Presenter extends BasePresenter<View> {
        void getMovie(int start,int count);
    }
}
