package com.photo.testphoto.network;

import android.content.Context;

import com.photo.testphoto.BuildConfig;
import com.photo.testphoto.network.interceptor.LoggerInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {

    private static ApiService SERVICE;

    /**
     * 请求超时时间
     */
    private static final int DEFAULT_TIMEOUT = 5;
    private static String BASE_URL = Url.BASE_URL;

    public static ApiService getInstance() {
        if (SERVICE == null) {
            /**
             * 手动创建一个OkHttpClient并设置超时时间
             */
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//                    .cookieJar(new CookieManger(context));//添加cookie


            if (BuildConfig.DEBUG) {
                /**
                 * debug模式下打印json
                 */
                LoggerInterceptor loggerInterceptor = new LoggerInterceptor();
                httpClientBuilder.addInterceptor(loggerInterceptor);
            }

            /**
             * 对所有请求添加请求头(全局header,可局部动态添加header)
             */
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response originalResponse = chain.proceed(request);
                    return originalResponse.newBuilder()
                            .header("key1", "value1")
                            .addHeader("key2", "value2")
                            .build();

                }
            });

            SERVICE = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build().create(ApiService.class);

        }
        return SERVICE;
    }

}
