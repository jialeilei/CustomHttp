package com.http.lei.customhttp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.http.lei.customhttp.R;
import com.http.lei.customhttp.http.DownloadCallback;
import com.http.lei.customhttp.http.DownloadManager;
import com.http.lei.customhttp.http.HttpManager;
import com.http.lei.customhttp.utils.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * create by lei
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private static final String REQUEST_URL = "http://www.imooc.com";
    private final String TAG = "MainActivity";
    private TextView tvContent;
    private Button btnShow,btnSync,btnAsync;
    private ImageView imgShow;
    private ProgressBar mProgressBar;

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
        imgShow = (ImageView) findViewById(R.id.imgShow);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);

        /*File file = FileStorageManager.getInstance().getFileByName("http://www.imooc.com");
        Logger.d(TAG,"file path "+file.getAbsolutePath());*/

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnShow:

                //test();
                /*getHttp();

                postHttp();

                multipartHttp();*/

                //asyncRequest();

                //download();

                testCustom();

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

    private void testCustom() {

//        OkHttpClient client = new OkHttpClient();
//        OkHttpRequest request = new OkHttpRequest(client, HttpMethod.GET,"http://www.baidu.com");
//        HttpResponse response = request.execute();
//
//        String content = null;
//        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()));
//        while ((content = reader.readLine()) != null){
//            System.out.println(content);
//        }
//        response.close();



    }

    private void test() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,60, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(10));
    }

    private int count = 0;
    /**
     * 多线程下载
     */
    private void download() {
        DownloadManager.Holder.getInstance()
        //DownloadManager.getInstance()
                //.download("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493821245&di=c3e0379c7e9c509739a9f75943b30461&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.jlonline.com%2Fd%2Ffile%2Fyule%2Fdianying%2F20170405%2F2e97cd958637abd4bfffdceb772c40d2.jpg",
                .download("http://msoftdl.360.cn/mobilesafe/shouji360/360safe/500192/360MobileSafe.apk",
                        new DownloadCallback() {
                            @Override
                            public void success(File file) {

                                if (count < 1) {
                                    count++;
                                    return;
                                }
                                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //imgShow.setImageBitmap(bitmap);
                                        tvContent.setText("success");
                                    }
                                });
                                Logger.d(TAG, "success " + file.getAbsolutePath());
                            }

                            @Override
                            public void fail(int errorCode, String errorMessage) {
                                Logger.d(TAG, "fail   errorCode:" + errorCode + " errorMessage:" + errorMessage);
                            }

                            @Override
                            public void progress(int progress) {
                                Logger.d(TAG, "progress " + progress);
                                mProgressBar.setProgress(progress);
                            }
                        });
    }

    /**
     * 异步请求
     */
    private void asyncRequest() {

        HttpManager.Holder.getInstance().asyncRequest("http://img.mukewang.com/567ca60000011fae26501720-200-200.jpg", new DownloadCallback() {
                    @Override
                    public void success(File file) {
                        //Logger.d(TAG, "success " + file.getAbsolutePath());
                        final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgShow.setImageBitmap(bitmap);
                            }
                        });
                    }

                    @Override
                    public void fail(int errorCode, String errorMessage) {
                        Logger.d(TAG, "fail " + errorCode + "  " + errorMessage);
                    }

                    @Override
                    public void progress(int progress) {



                    }
                });

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
      /*  HttpUrl httpUrl = HttpUrl.parse("http://api.heweather.com/x3/weather").newBuilder()
                .addQueryParameter("city","beijing")
                .addQueryParameter("key","")
                .host("http://api.heweather.com/")
                .port(8080)
                .build();
*/
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
