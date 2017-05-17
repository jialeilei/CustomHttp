package com.http.lei.customframwork.http.request;


import com.http.lei.customframwork.http.header.HttpHeader;
import com.http.lei.customframwork.http.response.HttpResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by lei on 2017/5/11.
 */
public abstract class BufferHttpRequest extends AbstractHttpRequest{


    private ByteArrayOutputStream mBiteArray = new ByteArrayOutputStream();

    @Override
    protected OutputStream getBodyOutputStream() {
        return mBiteArray;
    }

    @Override
    protected HttpResponse executeInternal(HttpHeader mHeader) throws IOException {
        byte[] data = mBiteArray.toByteArray();
        return executeInternal(mHeader,data);
    }

    protected abstract HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException;


}
