package com.photo.testphoto.login;

import android.content.Context;

import com.photo.testphoto.mvp.BasePresenter;
import com.photo.testphoto.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseView {
        void loginSuccess(Object user);


        void loginFailed(String message);
    }

    interface  Presenter extends BasePresenter<View> {
        void login(String username,String password);
    }
}
