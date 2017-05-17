package com.http.lei.customframwork.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.http.lei.customframwork.R;
import com.http.lei.customframwork.http.HttpMethod;
import com.http.lei.customframwork.http.okhttp.OkHttpRequest;
import com.http.lei.customframwork.http.response.HttpResponse;
import com.http.lei.customframwork.http.service.FrameworkApiProvider;
import com.http.lei.customframwork.http.service.FrameworkRequest;
import com.http.lei.customframwork.http.service.FrameworkResponse;
import com.http.lei.customframwork.http.service.WrapperResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String URL = "https://www.baidu.com";
    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }


    private void initView() {
        tvShow = (TextView) findViewById(R.id.tvShow);

    }


    private void initEvent(){

        //okHttpTest();

        originTest();

    }

    private void originTest() {

        Map<String,String> map = new HashMap<>();
        map.put("username","lei");

        FrameworkApiProvider.helloWorld(URL, map, new FrameworkResponse<Person>() {
            @Override
            public void success(FrameworkRequest request, Person object) {
                Log.i(TAG,"success "+object.toString());
            }

            @Override
            public void fail(int errorCode, String errorMsg) {
                Log.i(TAG,"fail");
            }
        });

    }

    private void okHttpTest() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                //OkHttpRequest request = new OkHttpRequest(client, HttpMethod.GET,"https://www.baidu.com/");
                OkHttpRequest request = new OkHttpRequest.Builder().client(client).method(HttpMethod.GET).url(URL).build();
                try {
                    HttpResponse response = request.execute();
                    String content = null;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()));
                    while ((content = reader.readLine()) != null){
                        System.out.println(content);
                        Log.d(TAG,content+"");
                    }
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
