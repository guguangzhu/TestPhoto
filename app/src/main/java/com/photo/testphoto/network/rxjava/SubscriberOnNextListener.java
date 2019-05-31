package com.photo.testphoto.network.rxjava;

public interface SubscriberOnNextListener<T> {

    /**
     * 返回数据
     *
     * @param t the t
     */
    void onNext(T t);


    /**
     * On fail.
     * 失败
     *
     * @param err the err
     */
    void onFail(String err);
}
