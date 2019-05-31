package com.photo.testphoto.kotlin

import android.os.Bundle
import android.view.View
import com.photo.testphoto.R
import com.photo.testphoto.mvp.BaseView
import com.photo.testphoto.mvp.MVPBaseActivity
import com.photo.testphoto.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : MVPBaseActivity<WeatherContract.View, WeatherPresenter>(), WeatherContract.View{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        button.setOnClickListener {
            mPresenter.getWeather("101190201")
        };
    }


    override fun getWeatherSucdess(weatherinfoModel: WeatherinfoModel) {
        ToastUtil.showToast(this,"成功")
    }

    override fun getWeatherFailed(msg: String) {

    }

}