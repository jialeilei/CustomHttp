package com.http.lei.customframwork.http.request;


import com.http.lei.customframwork.http.header.HttpHeader;
import com.http.lei.customframwork.http.response.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;


/**
 * Created by lei on 2017/5/11.
 */
public abstract class AbstractHttpRequest implements HttpRequest {


    private HttpHeader mHeader = new HttpHeader();
    private ZipOutputStream mZip;
    private final static String GZIP = "gzip";
    private boolean executed = false;


    public HttpHeader getHeader() {
        return mHeader;
    }

    @Override
    public OutputStream getBody() {

        OutputStream body = getBodyOutputStream();
        if (isGzip()){
            return getGzipOutputStream(body);
        }
        return null;
    }

    private OutputStream getGzipOutputStream(OutputStream body) {
        if (this.mZip == null){
            mZip = new ZipOutputStream(body);
        }
        return mZip;
    }

    protected abstract OutputStream getBodyOutputStream();

    private boolean isGzip(){
        String contentEncoding = getHeader().getContentEncoding();
        if (contentEncoding.equals(GZIP)){
            return true;
        }
        return false;
    }

    @Override
    public HttpResponse execute() throws IOException {
        if (mZip!=null){
            mZip.close();
        }

        HttpResponse response = executeInternal(mHeader);
        executed = true;
        return response;
    }

    protected abstract HttpResponse executeInternal(HttpHeader mHeader) throws IOException;

}
