package com.photo.testphoto.kotlin

import android.content.Context
import com.photo.testphoto.bean.Subject
import com.photo.testphoto.bean.base.BaseBean
import com.photo.testphoto.mvp.BasePresenterImpl
import com.photo.testphoto.network.HttpUtil
import com.photo.testphoto.network.SubscribeHandler
import com.photo.testphoto.network.rxjava.ProgressSubscriber
import com.photo.testphoto.network.rxjava.SubscriberOnNextListener
import io.reactivex.Observable

class WeatherPresenter : BasePresenterImpl<WeatherContract.View>(),WeatherContract.Presenter{


    override fun getWeather(cityId: String) {
//        Observable ob = HttpUtil.getInstance();
        val ob= HttpUtil.getInstance().getWeather(cityId)
        val listener =object : SubscriberOnNextListener<WeatherinfoModel>{
            override fun onNext(t: WeatherinfoModel?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFail(err: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
        SubscribeHandler.observeOn(ob, ProgressSubscriber<BaseBean<WeatherinfoModel>>(listener, mView as Context, false))

    }
}