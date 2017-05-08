package com.http.lei.customhttp.http;

import android.content.Context;
import com.http.lei.customhttp.file.FileStorageManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lei on 2017/4/26.
 */
public class HttpManager {

    public static final int NETWORK_ERROR_CODE = 1;

    public static final int CONTENT_LENGTH_ERROR_CODE = 2;

    public static final int TASK_RUNNING_ERROR = 3;

    private Context mContext;

    private OkHttpClient mClient;

    private static HttpManager sManager;

   /* public static HttpManager getInstance(){
        if (sManager == null){
            synchronized (HttpManager.class){
                if (sManager == null){
                    sManager = new HttpManager();
                }
            }
            return sManager;
        }
        return sManager;
    }*/

    /**
     *
     */
    public static class Holder{
        private static HttpManager sManager = new HttpManager();
        public static HttpManager getInstance(){
            return sManager;
        }
    }

    private HttpManager(){
        mClient = new OkHttpClient();
    };

    public void init(Context context){
        this.mContext = context;
    }

    /**
     * 同步请求
     * @param url
     * @return
     */
    public Response syncRequest(String url){
        Request request = new Request.Builder().url(url).build();
        Response response;

        try {
            response = mClient.newCall(request).execute();
            if (response.isSuccessful()){
                return response;
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 同步请求
     * 请求头中加入Range
     * @param url
     * @return
     */
    public Response syncRequestByRange(String url,long start,long end){
        Request request = new Request.Builder().url(url).addHeader("Range", "bytes=" + start + "-" + end).build();
        Response response;

        try {
            response = mClient.newCall(request).execute();
            if (response.isSuccessful()){
                return response;
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 异步下载文件
     * @param url
     * @param callback
     */
    public void asyncRequest(final String url, final DownloadCallback callback){
        final Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful() && callback != null){
                    callback.fail(NETWORK_ERROR_CODE,"network error");
                }

                File file = FileStorageManager.getInstance().getFileByName(url);

                byte[] buffer = new byte[1024*500];//500kb
                int len;
                FileOutputStream outputStream = new FileOutputStream(file);
                InputStream inputStream = response.body().byteStream();
                while ((len = inputStream.read(buffer,0,buffer.length)) != -1){
                    outputStream.write(buffer,0,len);
                    outputStream.flush();
                }

                callback.success(file);
            }
        });
    }


    /**
     * 进行多线程下载
     * @param url
     * @param callback
     */
    public void asyncRequest(final String url, final Callback callback){
        final Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callback.onFailure(call,e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful() && callback != null){
                    //callback.onFailure(call,response);
                }
                callback.onResponse(call,response);
            }
        });
    }



}
