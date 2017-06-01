package com.http.lei.packagehttptool.http;


import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.http.lei.packagehttptool.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lei on 2017/5/31.
 */
public class HttpProvider {

    private static HttpProvider mInstance;
    private OkHttpClient mClient;
    private Gson mGson;
    private Handler mHandler;


    private static HttpProvider getInstance(){
        if (mInstance == null){
            synchronized (HttpProvider.class){
                if (mInstance == null){
                    mInstance = new HttpProvider();
                }
            }
        }
        return mInstance;
    }

    private HttpProvider(){
        mClient = new OkHttpClient();
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    //public
    public static void getAsync(String url, ResultCallback callback) {
        getInstance()._getAsync(url, callback);
    }

    public static void getAsync(String url, Map<String,String>params, ResultCallback callback) {
        getInstance()._getAsync(url, params, callback);
    }

    public static void postAsync(String url,Map<String,String>params,ResultCallback callback){
        getInstance()._postAsync(url, callback, params);
    }

    public static void displayImage(ImageView img,String url){
        getInstance()._displayImage(img, url, R.mipmap.ic_launcher);
    }




    //private

    private void _displayImage(final ImageView img, final String url, final int errorResId) {
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                setErrorResId(img, errorResId);
            }

            @Override
            public void onResponse(Call call, Response response){

                InputStream stream = null;
                try {
                    stream = response.body().byteStream();
                    ImageUtils.ImageSize actualImageSize = ImageUtils.getImageSize(stream);
                    ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(view);
                    int inSampleSize = ImageUtils.calculateInSampleSize(actualImageSize, imageViewSize);
                    stream.reset();
                } catch (IOException e) {
                    try {
                        response = _getAsync(url);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });


    }

    private void setErrorResId(final ImageView img, final int errorResId) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                img.setImageResource(errorResId);
            }
        });
    }

    /**
     * get
     * @param url
     * @param callback
     */
    private void _getAsync(String url,final ResultCallback callback){
        Request request = new Request.Builder().url(url).build();
        deliverResult(request, callback);
    }

    private void _getAsync(String url,Map<String,String>maps,final ResultCallback callback){
        Param[] params = map2Params(maps);
        Request request = buildGetRequest(url, params);
        deliverResult(request, callback);
    }

    private Response _getAsync(String url) throws IOException{
        Request request = new Request.Builder().url(url).build();
        return mClient.newCall(request).execute();
    }

    private void _postAsync(String url,ResultCallback callback,Map<String,String> maps){
        Param[] params = map2Params(maps);
        Request request = buildPostRequest(url, params);
        deliverResult(request, callback);
    }

    private void deliverResult(final Request request, final ResultCallback callback) {
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                failCallback(request, callback, e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String str = response.body().string();
                    if (callback.mType == str.getClass()) {
                        successCallback(str, callback);
                    } else {
                        Object o = mGson.fromJson(str, callback.mType);
                        successCallback(o, callback);
                    }
                } catch (IOException e) {
                    failCallback(response.request(), callback, e);
                } catch (JsonParseException e) {
                    failCallback(response.request(), callback, e);
                }
            }
        });
    }

    private void failCallback(final Request request, final ResultCallback callback,final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFail(request, e);
                }
            }
        });
    }

    private void successCallback(final Object object,final ResultCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }

   /* private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();

        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }*/

    //is right?
    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    private Request buildGetRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .method("GET", requestBody)
                .build();
    }

    private Param[] map2Params(Map<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }


}
