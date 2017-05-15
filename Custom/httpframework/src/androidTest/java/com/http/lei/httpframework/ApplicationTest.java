package com.http.lei.httpframework;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import http.HttpMethod;
import http.request.OkHttpRequest;
import http.response.HttpResponse;
import okhttp3.OkHttpClient;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() throws IOException {
        super(Application.class);

        System.out.println("test println");
        Log.d("test", "test log.d");
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                OkHttpRequest request = new OkHttpRequest(client, HttpMethod.GET,"http://www.baidu.com");
                try {
                    HttpResponse response = request.execute();

                    String content = null;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()));
                    while ((content = reader.readLine()) != null){
                        System.out.println(content);
                        Log.d("test",content+"");
                    }
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();






    }
}