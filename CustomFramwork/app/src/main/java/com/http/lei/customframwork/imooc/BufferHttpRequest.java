package com.http.lei.customframwork.imooc;


import com.http.lei.customframwork.imooc.http.HttpHeader;
import com.http.lei.customframwork.imooc.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author nate
 */

public abstract class BufferHttpRequest extends AbstractHttpRequest {

    private ByteArrayOutputStream mByteArray = new ByteArrayOutputStream();

    protected OutputStream getBodyOutputStream() {
        return mByteArray;
    }

    protected HttpResponse executeInternal(HttpHeader header) throws IOException {
        System.out.println("fuck  executeInternal");
        byte[] data = mByteArray.toByteArray();
        return executeInternal(header, data);
    }

    protected abstract HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException;

}
