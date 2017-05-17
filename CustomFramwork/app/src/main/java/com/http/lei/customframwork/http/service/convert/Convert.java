package com.http.lei.customframwork.http.service.convert;


import com.http.lei.customframwork.http.response.HttpResponse;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by lei on 2017/5/17.
 */
public interface Convert {

    Object parse(HttpResponse response,Type type) throws IOException;

    Object parse(String content,Type type) throws IOException;

    boolean isCanParse(String contentType);

}
