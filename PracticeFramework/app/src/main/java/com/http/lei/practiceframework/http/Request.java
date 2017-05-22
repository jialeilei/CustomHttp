package com.http.lei.practiceframework.http;


/**
 * Created by lei on 2017/5/19.
 */
public class Request {

    private String url;

    private Method method;

    private byte[] data;

    private Response response;

    private String contentType;

   /* private Request(Builder builder){
        url = builder.getUrl();
        method = builder.getMethod();
        data = builder.getData();
        response = builder.getResponse();
        contentType = builder.getContentType();
    }

    public static class Builder{
        private String url;
        private Method method;
        private byte[] data;
        private Response response;
        private String contentType;

        public Request build(){
            return new Request(this);
        }
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder data(byte[] data) {
            this.data = data;
            return this;
        }

        public Builder response(Response response) {
            this.response = response;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Method getMethod() {
            return method;
        }

        public byte[] getData() {
            return data;
        }

        public Response getResponse() {
            return response;
        }

        public String getContentType() {
            return contentType;
        }
    }*/

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public Method getMethod() {
        return method;
    }

    public byte[] getData() {
        return data;
    }

    public Response getResponse() {
        return response;
    }

    public String getContentType() {
        return contentType;
    }
}
