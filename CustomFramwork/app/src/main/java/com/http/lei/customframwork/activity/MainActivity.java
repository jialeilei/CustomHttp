package com.http.lei.customframwork.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.http.lei.customframwork.R;
import com.http.lei.customframwork.http.service.FrameworkApiProvider;
import com.http.lei.customframwork.http.service.FrameworkRequest;
import com.http.lei.customframwork.http.service.FrameworkResponse;
import com.http.lei.customframwork.imooc.service.MoocApiProvider;
import com.http.lei.customframwork.imooc.service.MoocRequest;
import com.http.lei.customframwork.imooc.service.MoocResponse;
import com.http.lei.customframwork.util.LogTool;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private static final String URL_BAIDU = "https://www.baidu.com";
    private static final String URL = "http://10.0.0.27/bbsServer/login.php";
    private TextView tvShow;
    private Button btnOk,btnOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }


    private void initView() {

        tvShow = (TextView) findViewById(R.id.tvShow);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOrigin = (Button) findViewById(R.id.btnOrigin);

    }


    private void initEvent(){

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //okHttp();
                imooc();
            }
        });

        btnOrigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogTool.i("onClick");
                System.out.println("origin");

                origin();

            }
        });

    }

    private void imooc(){
        Map<String,String> map = new HashMap<>();
        map.put("name","lei");
        map.put("pwd", "123");
        MoocApiProvider.helloWorld(URL, map, new MoocResponse<Person>() {
            @Override
            public void success(MoocRequest request, Person data) {
                LogTool.i("success " + data.toString());
                System.out.print("Main success " + data.toString());
            }
            @Override
            public void fail(int errorCode, String errorMsg) {
                LogTool.i("fail");
                System.out.print("Main fail " + errorMsg);
            }
        });
    }
    private void origin() {
        Map<String,String> map = new HashMap<>();
        map.put("name","lei");
        map.put("pwd", "123");
        FrameworkApiProvider.helloWorld(URL, map, new FrameworkResponse<Person>() {
            @Override
            public void success(FrameworkRequest request, Person object) {
                LogTool.i( "success " + object.toString());
                show("onResponse " + object.toString());
            }

            @Override
            public void fail(int errorCode, String errorMsg) {
                LogTool.i("fail");
                show( "onFailure");
            }
        });

    }

    private void okHttp() {

        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder().
                add("name", "lei").
                add("pwd", "123").
                build();
        Request request = new Request.Builder().url(URL).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogTool.i("onFailure");
                show( "onFailure");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    LogTool.d("success " + response.body());
                } else {
                    LogTool.i("onResponse " + response.body());
                }
                show("onResponse "+response.body());
            }
        });
    }

    private void show(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvShow.setText(""+msg);
            }
        });
    }

}
