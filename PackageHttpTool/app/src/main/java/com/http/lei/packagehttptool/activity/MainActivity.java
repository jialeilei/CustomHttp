package com.http.lei.packagehttptool.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.http.lei.packagehttptool.R;
import com.http.lei.packagehttptool.http.HttpProvider;
import com.http.lei.packagehttptool.http.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String URL = "http://10.0.0.27/bbsServer/login.php";
    private Button btnGet,btnPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnGet = (Button) findViewById(R.id.btnGet);
        btnGet.setOnClickListener(this);
        btnPost = (Button) findViewById(R.id.btnPost);
        btnPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnPost:
                Map<String,String> params = new HashMap<>();
                params.put("username","lei");
                params.put("pwd","123");
                HttpProvider.postAsync(URL,params,new ResultCallback<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Main","response "+response.toString());
                    }

                    @Override
                    public void onFail(Request request, Exception e) {
                        Log.i("main","fail");
                    }
                });
                break;
            case R.id.btnGet:
                break;
            default:
                break;
        }
    }
}
