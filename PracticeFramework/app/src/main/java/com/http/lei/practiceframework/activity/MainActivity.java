package com.http.lei.practiceframework.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.http.lei.practiceframework.R;
import com.http.lei.practiceframework.http.provider.ApiProvider;
import com.http.lei.practiceframework.http.Request;
import com.http.lei.practiceframework.http.Response;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://www.baidu.com/";
    private TextView tvShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvShow = (TextView) findViewById(R.id.tvShow);
        initEvent();

    }

    private void initEvent() {

        Map<String,String> param = new HashMap<>();
        param.put("name","lei");
        param.put("uid", "13");
        ApiProvider.post(URL, param, new Response<Person>() {
            @Override
            public void success(Request request, Person data) {

            }

            @Override
            public void fail(int errorCode, String errorMsg) {

            }
        });

    }
}
