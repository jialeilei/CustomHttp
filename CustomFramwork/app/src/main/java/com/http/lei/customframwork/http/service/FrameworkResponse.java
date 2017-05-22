package com.http.lei.customframwork.http.service;

/**
 * Created by lei on 2017/5/16.
 */
public abstract class FrameworkResponse<T> {

    public abstract void success(FrameworkRequest request, T data);

    public abstract void fail(int errorCode,String errorMsg);


}
