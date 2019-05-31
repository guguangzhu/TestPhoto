package com.photo.testphoto.network;

import com.photo.testphoto.bean.BingImageBean;
import com.photo.testphoto.bean.Subject;
import com.photo.testphoto.bean.WeatherBean;
import com.photo.testphoto.bean.base.BaseBean;
import com.photo.testphoto.kotlin.WeatherinfoModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    /**
     * Gets top movie.
     * @return the top movie
     */
    @POST("top250")
    Observable<BaseBean<List<Subject>>> getTopMovies(@Query("start") int start, @Query("count") int count);
    /**
     * Gets top movie.
     * @return the top movie
     */
    @GET("http://www.weather.com.cn/adat/sk/{cityId}.html")
    Observable<BaseBean<WeatherinfoModel>> getWeather(@Path("cityId") String cityId);
    /**
     * Gets top movie.
     * @return the top movie
     */
    @GET("https://cn.bing.com/HPImageArchive.aspx?format=js")
    Observable<BaseBean<List<BingImageBean>>> getBingImage(@Query("n") int number);
}
