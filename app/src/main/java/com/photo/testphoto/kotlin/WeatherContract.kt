package com.photo.testphoto.kotlin

import com.photo.testphoto.mvp.BasePresenter
import com.photo.testphoto.mvp.BaseView

class WeatherContract  {
    interface View : BaseView{
        fun getWeatherSucdess(weatherinfoModel: WeatherinfoModel)

        fun getWeatherFailed(msg:String)
    }

    interface Presenter:BasePresenter<View>{
        fun getWeather(cityId:String)
    }
}