package com.http.lei.customframwork.http.service;

import com.http.lei.customframwork.http.HttpMethod;

/**
 * Created by lei on 2017/5/16.
 */
public class FrameworkRequest {

    private String url;

    private HttpMethod method;

    private byte[] data;

    private FrameworkResponse response;

    private String contentType;




    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public FrameworkResponse getResponse() {
        return response;
    }

    public void setResponse(FrameworkResponse response) {
        this.response = response;
    }
}
