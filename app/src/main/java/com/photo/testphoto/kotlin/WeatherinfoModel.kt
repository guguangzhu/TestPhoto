package com.photo.testphoto.kotlin

data class WeatherinfoModel constructor(var weatherinfo: WeatherinfoBean){

    data class WeatherinfoBean(
            val city: String,
            val cityId: String
    )
}