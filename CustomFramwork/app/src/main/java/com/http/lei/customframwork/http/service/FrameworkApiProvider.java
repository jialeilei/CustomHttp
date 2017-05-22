package com.http.lei.customframwork.http.service;


import com.http.lei.customframwork.http.HttpMethod;
import com.http.lei.customframwork.http.service.convert.Convert;
import com.http.lei.customframwork.http.service.convert.JsonConvert;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by lei on 2017/5/16.
 */
public class FrameworkApiProvider {


    private static final String ENCODE_UTF8 = "utf-8";
    private static WorkStation sWorkStation = new WorkStation();
    private static final List<Convert> sConverts = new ArrayList<>();
    static {
        sConverts.add(new JsonConvert());
    }

    public static void helloWorld(String url,Map<String,String> value,FrameworkResponse response){

        FrameworkRequest request = new FrameworkRequest();

        WrapperResponse wrapperResponse = new WrapperResponse(response,sConverts);

        request.setUrl(url);
        request.setMethod(HttpMethod.POST);
        request.setData(encodeParam(value));
        request.setResponse(wrapperResponse);

        sWorkStation.add(request);
    }


    public static byte[] encodeParam(Map<String,String> value){

        if (value == null || value.size() == 0){
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        int count = 0;
        try {
            for (Map.Entry<String, String> entry : value.entrySet()) {
                buffer.append(URLEncoder.encode(entry.getKey(),ENCODE_UTF8)).
                        append("=").
                        append(URLEncoder.encode(entry.getValue(), ENCODE_UTF8));
                if (count != value.size() - 1){
                    buffer.append("&");
                }
                count++;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return buffer.toString().getBytes();
    }


}
