package com.http.lei.customhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * create by lei
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private static final String REQUEST_URL = "http://www.imooc.com";
    private final String TAG = "MainActivity";
    private TextView tvContent;
    private Button btnShow,btnSync,btnAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        tvContent = (TextView) findViewById(R.id.tvContent);
        btnShow = (Button) findViewById(R.id.btnShow);
        btnShow.setOnClickListener(this);
        btnSync = (Button) findViewById(R.id.btnSync);
        btnSync.setOnClickListener(this);
        btnAsync = (Button) findViewById(R.id.btnAsync);
        btnAsync.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnShow:

                getHttp();

                postHttp();

                multipartHttp();




                break;
            case R.id.btnSync:

                syncHttp();


                break;

            case R.id.btnAsync:

                headHttp();

                break;

            default:

                break;
        }
    }

    /**
     * 上传文件
     */
    private void multipartHttp() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse(""),new File("/user/"));//path
        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name","jack")
                .addFormDataPart("filename", "picture.jpg", requestBody).build();

        Request request = new Request.Builder().url("http://localhost:8080/web/upload").post(body).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                tvContent.setText("success");
            }else {
                tvContent.setText("fail");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void postHttp() {

        OkHttpClient client = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("username", "lei")
                .add("userage", "123456")
                .build();

        Request request = new Request.Builder().post(formBody).url("").build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                tvContent.setText("success");
            }else {
                tvContent.setText("fail");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getHttp() {

        OkHttpClient client = new OkHttpClient();
        HttpUrl httpUrl = HttpUrl.parse("http://api.heweather.com/x3/weather").newBuilder()
                .addQueryParameter("city","beijing")
                .addQueryParameter("key","")
                .build();

        Log.i(TAG,"url.toString() "+httpUrl.toString());
        Request request = new Request.Builder().url(httpUrl).build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                Log.i(TAG,""+response.body().string());


            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 同步请求
     */
    private void syncHttp() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://www.imooc.com").build();

                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()){
                        Log.i(TAG,"success "+response.body().string());
                        System.out.print("success");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvContent.setText("success ");
                            }
                        });
                    }else {
                        Log.i(TAG,"fail");
                        System.out.print("fail");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvContent.setText("fail ");
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 请求头
     */
    private void headHttp() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().
                        url(REQUEST_URL).
                        addHeader("User-Agent", "lei http").build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()){
                        Headers headers = response.headers();
                        for (int i = 0; i < headers.size(); i++) {
                            Log.i(TAG,headers.name(i)+" : "+headers.value(i));
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }
}
