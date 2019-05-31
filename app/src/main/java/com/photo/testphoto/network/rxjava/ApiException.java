package com.photo.testphoto.network.rxjava;

public interface ApiException {
    /**
     * 解析数据失败
     **/
    int PARSE_ERROR=10001;

    /**
     * 网络问题
     **/
    int BAD_NETWORK=10002;

    /**
     * 连接错误
     **/
    int CONNECT_ERROR=10003;

    /**
     * 连接超时
     **/
    int CONNECT_TIMEOUT=10004;

    /**
     * section token past due 过期
     **/
    int TOKEN_PAST_DUE=10005;

    /**
     * 未知错误
     **/
    int UNKNOWN_ERROR=10006;
}
