package com.photo.testphoto.login;


import com.photo.testphoto.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    @Override
    public void loginSuccess(Object user) {

    }

    @Override
    public void loginFailed(String message) {

    }
}
