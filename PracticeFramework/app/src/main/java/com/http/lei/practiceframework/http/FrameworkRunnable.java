package com.http.lei.practiceframework.http;

import com.http.lei.practiceframework.http.request.HttpRequest;
import com.http.lei.practiceframework.http.response.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by lei on 2017/5/19.
 */
public class FrameworkRunnable implements Runnable {

    private HttpRequest httpRequest;
    private Request request;
    private WorkStation station;

    public FrameworkRunnable(Request request,HttpRequest httpRequest,WorkStation station){
        this.httpRequest = httpRequest;
        this.request = request;
        this.station = station;
    }

    @Override
    public void run() {
        try {
            httpRequest.getBody().write(request.getData());
            HttpResponse response = httpRequest.execute();
            String contentType = response.getHeader().getContentType();
            request.setContentType(contentType);

            if (response.getStatus().isSuccess()) {
                if (response.getBody() != null)
                request.getResponse().success(request,new String(getData(response)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            station.finish(request);
        }

    }

    private byte[] getData(HttpResponse response) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream((int) response.getContentLength());
        int length;
        byte[] data = new byte[512];
        while ( (length = response.getBody().read(data)) != -1 ){
            stream.write(data,0,length);
        }
        return stream.toByteArray();
    }

}
