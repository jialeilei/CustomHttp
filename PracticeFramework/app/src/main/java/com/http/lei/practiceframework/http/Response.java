package com.http.lei.practiceframework.http;

import com.http.lei.practiceframework.http.Request;

/**
 * Created by lei on 2017/5/19.
 */
public abstract class Response<T> {

    public abstract void success(Request request, T data);

    public abstract void fail(int errorCode,String errorMsg);

}
