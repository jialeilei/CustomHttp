package com.http.lei.customframwork.http.service;


import com.http.lei.customframwork.http.request.HttpRequest;
import com.http.lei.customframwork.http.response.HttpResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * Created by lei on 2017/5/16.
 */
public class FrameworkRunnable implements Runnable {

    private HttpRequest mHttpRequest;
    private FrameworkRequest mRequest;
    private WorkStation mWorkStation;


    public FrameworkRunnable(HttpRequest httpRequest,FrameworkRequest request,WorkStation station){
        this.mHttpRequest = httpRequest;
        this.mRequest = request;
        this.mWorkStation = station;
        System.out.println("runnable");
    }

    @Override
    public void run() {
        System.out.println("run");
        try {
            mHttpRequest.getBody().write(mRequest.getData());
            HttpResponse response = mHttpRequest.execute();
            String contentType = response.getHeader().getContentType();
            mRequest.setContentType(contentType);

            if (response.getStatus().isSuccess()){
                if (mRequest.getResponse() != null){
                    mRequest.getResponse().success(mRequest,new String(getData(response)));
                }
            }else {//new add 2017/5/25
                mRequest.getResponse().fail(100,"error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            mWorkStation.finish(mRequest);
        }

    }


    public byte[] getData(HttpResponse response) throws IOException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream((int) response.getContentLength());
        int length;
        byte[] data = new byte[512];
        while ((length = response.getBody().read(data)) != -1){
            stream.write(data,0,length);
        }
        return stream.toByteArray();
    }

}
