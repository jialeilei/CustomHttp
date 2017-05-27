package com.http.lei.customframwork.http.request;


import com.http.lei.customframwork.http.HttpMethod;
import com.http.lei.customframwork.http.header.HttpHeader;
import com.http.lei.customframwork.http.response.HttpResponse;
import com.http.lei.customframwork.util.LogTool;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.zip.ZipOutputStream;


/**
 * Created by lei on 2017/5/11.
 */
public abstract class AbstractHttpRequest implements HttpRequest {

    private HttpHeader mHeader = new HttpHeader();
    private ZipOutputStream mZip;
    private final static String GZIP = "gzip";
    private boolean executed = false;


   /* public HttpHeader getHeader() {
        return mHeader;
    }*/

    @Override
    public HttpHeader getHeader() {
        return mHeader;
    }

    @Override
    public OutputStream getBody() {

        OutputStream body = getBodyOutputStream();
        if (isGzip()){
            return getGzipOutputStream(body);
        }
        return body;
    }


    private OutputStream getGzipOutputStream(OutputStream body) {
        if (this.mZip == null){
            this.mZip = new ZipOutputStream(body);
        }
        return mZip;
    }


    private boolean isGzip(){
        String contentEncoding = getHeader().getContentEncoding();
        LogTool.i("contentEncoding: ");
        if (GZIP.equals(contentEncoding)){
            return true;
        }
        return false;
    }

    @Override
    public HttpResponse execute() throws IOException {
        System.out.println("lei AbstractHttpRequest execute");
        if (mZip!=null){
            mZip.close();
        }
        HttpResponse response = executeInternal(mHeader);
        executed = true;
        return response;
    }

    protected abstract HttpResponse executeInternal(HttpHeader mHeader) throws IOException;

    protected abstract OutputStream getBodyOutputStream();

}
