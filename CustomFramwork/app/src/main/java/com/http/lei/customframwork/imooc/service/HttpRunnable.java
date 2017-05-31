package com.http.lei.customframwork.imooc.service;

import com.http.lei.customframwork.imooc.http.HttpRequest;
import com.http.lei.customframwork.imooc.http.HttpResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author nate
 */

public class HttpRunnable implements Runnable {

    private HttpRequest mHttpRequest;

    private MoocRequest mRequest;

    private WorkStation mWorkStation;

    public HttpRunnable(HttpRequest httpRequest, MoocRequest request, WorkStation workStation) {
        this.mHttpRequest = httpRequest;
        this.mRequest = request;
        this.mWorkStation = workStation;
        System.out.println("runnable");
    }

    @Override
    public void run() {
        System.out.println("http run");
        try {
            mHttpRequest.getBody().write(mRequest.getData());
            HttpResponse response = mHttpRequest.execute();
            System.out.println("finish execute");
            String contentType = response.getHeaders().getContentType();
            mRequest.setContentType(contentType);
            if (response.getStatus().isSuccess()) {
                if (mRequest.getResponse() != null) {
                    System.out.println("1");
                    mRequest.getResponse().success(mRequest, new String(getData(response)));
                }else {
                    System.out.println("2");
                    mRequest.getResponse().success(mRequest, "null");
                }
            }else {
                mRequest.getResponse().fail(103,response.getStatusMsg());
                System.out.println("http fail "+response.getStatusMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mWorkStation.finish(mRequest);
        }

    }

    public byte[] getData(HttpResponse response) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream((int) response.getContentLength());
        int len;
        byte[] data = new byte[512];
        try {
            while ((len = response.getBody().read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
