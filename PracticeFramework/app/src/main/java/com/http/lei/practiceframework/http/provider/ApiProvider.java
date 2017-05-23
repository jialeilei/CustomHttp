package com.http.lei.practiceframework.http.provider;


import com.http.lei.practiceframework.http.Method;
import com.http.lei.practiceframework.http.WorkStation;
import com.http.lei.practiceframework.http.Request;
import com.http.lei.practiceframework.http.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by lei on 2017/5/19.
 */
public class ApiProvider {

    private static final String ENCODE_UTF8 = "utf-8";

    private static WorkStation sWorkStation = new WorkStation();

    public static void post(String url,Map<String,String> value,Response response) {

        Request request = new Request.Builder().url(url).
                method(Method.POST).
                data(encodeParam(value)).
                response(response).
                build();
        /*Request request = new Request();
        request.setUrl(url);
        request.setMethod(Method.POST);
        request.setData(encodeParam(value));
        request.setResponse(response);*/

        sWorkStation.add(request);
    }




    private static byte[] encodeParam(Map<String, String> value) {
        if (value == null || value.size() == 0){
            return null;
        }
        StringBuffer buffer = null;
        int count = 0;
        try {
            for (Map.Entry<String, String> entry : value.entrySet()) {
                buffer.append(URLEncoder.encode(entry.getKey(),ENCODE_UTF8)).
                        append("=").append(URLEncoder.encode(entry.getValue(),ENCODE_UTF8));
                if (count != value.size() - 1){
                    buffer.append("&");
                }
                count ++;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buffer.toString().getBytes();
    }


}
