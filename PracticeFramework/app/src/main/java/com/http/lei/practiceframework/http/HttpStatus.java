package com.http.lei.practiceframework.http;

/**
 * Created by lei on 2017/5/22.
 */
public enum  HttpStatus {

    CONTINUE(100,"Continue"),
    SWITCHING_PROTOCOLS(101,"Switching Protocols"),

    //successful
    OK(200,"OK"),
    CREATED(201,"Created"),
    ACCEPTED(202,"Accepted"),
    NON_AUTHORITATIVE_INFORMATION(203,"Non-Authoritative Information"),
    NO_CONTENT(204,"No Content"),
    RESET_CONTENT(205,"Reset Content"),

    //redirection
    MULTIPLE_CHOICES(300,"Multiple Choices"),
    MOVED_PERMANENTLY(301,"Moved Permanently"),
    FOUND(302,"Found"),
    SEE_OTHER(303,"See Other"),
    USE_PROXY(305,"Use Proxy"),
    UNUSED(306,"Unused"),
    TEMPORARY_REDIRECT(307,"Temporary Redirect"),

    //400~~499客户端的错误
    BAD_REQUEST(400,"Bad Request"),
    PAYMENT_REQUIRED(402,"Payment Requerid"),
    FORBIDDEN(403,"Forbidden"),
    NOT_FOUND(404,"Not Found"),
    METHOD_NOT_ALLOWED(405,"Method Not Allowed");

    //500~~599服务器端错误


    private int mStatus;
    private String mMessage;

    private HttpStatus(int code,String message){
        this.mStatus = code;
        this.mMessage = message;
    }

    public boolean isSuccess(){
        int value = mStatus / 100;
        if (value == 2){
            return true;
        }
        return false;
    }

    public static HttpStatus getValue(int value){
        for (HttpStatus httpStatus : values()){
            if (value == httpStatus.mStatus){
                return httpStatus;
            }
        }
        return null;
    }

}
