package com.http.lei.customframwork.http.service.convert;


import com.google.gson.Gson;
import com.http.lei.customframwork.http.response.HttpResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;


/**
 * Created by lei on 2017/5/17.
 */
public class JsonConvert implements Convert {

    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private Gson gson = new Gson();

    @Override
    public Object parse(HttpResponse response, Type type) throws IOException {
        Reader reader = new InputStreamReader(response.getBody());
        return gson.fromJson(reader,type);
    }

    @Override
    public boolean isCanParse(String contentType) {
        return CONTENT_TYPE.equals(contentType);
    }

    @Override
    public Object parse(String content, Type type) throws IOException {
        return gson.fromJson(content,type);
    }


}
