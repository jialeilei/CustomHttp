package com.http.lei.practiceframework.http.request;


import com.http.lei.practiceframework.http.header.HttpHeader;
import com.http.lei.practiceframework.http.response.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by lei on 2017/5/22.
 */
public abstract class AbstractHttpRequest implements HttpRequest {

    private HttpHeader mHeader = new HttpHeader();
    private ZipOutputStream mZip;
    private static final String GZIP = "gzip";

    public HttpHeader getHeader(){
        return mHeader;
    }

    @Override
    public OutputStream getBody() {
        OutputStream stream = getBodyOutputStream();
        if (isGzip()){
            return getGzipOutputStream(stream);
        }
        return null;
    }

    private OutputStream getGzipOutputStream(OutputStream stream) {
        if (mZip == null){
            mZip = new ZipOutputStream(stream);
        }
        return mZip;
    }

    private boolean isGzip() {
        String type = getHeader().getContentEncoding();
        if (type.equals(GZIP)){
            return true;
        }
        return false;
    }

    protected abstract OutputStream getBodyOutputStream();

    private boolean executed = false;

    @Override
    public HttpResponse execute() throws IOException {
        if (mZip != null){
            mZip.close();
        }
        HttpResponse response = executeInternal(mHeader);
        executed = true;
        return response;
    }

    protected abstract HttpResponse executeInternal(HttpHeader header) throws IOException;

}
