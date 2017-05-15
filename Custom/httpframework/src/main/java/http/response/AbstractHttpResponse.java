package http.response;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;


/**
 * Created by lei on 2017/5/11.
 */
public abstract class AbstractHttpResponse implements HttpResponse {


    private static final String GZIP = "gzip";
    private InputStream mGzipInputStream;


    @Override
    public void close() throws IOException {
        if (mGzipInputStream != null){
            mGzipInputStream.close();
        }
        closeInternal();
    }

    private boolean isGzip(){
        String contentEncoding = getHeader().getContentEncoding();
        if (GZIP.equals(contentEncoding)){
            return true;
        }
        return false;
    }

    @Override
    public InputStream getBody() throws IOException {
        InputStream body = getBodyInternal();
        if (isGzip()){
            return getBodyGzip(body);
        }
        return body;
    }

    private InputStream getBodyGzip(InputStream body) throws IOException {
        if (this.mGzipInputStream == null){
            mGzipInputStream = new GZIPInputStream(body);
        }
        return mGzipInputStream;
    }

    protected abstract InputStream getBodyInternal();

    protected abstract void closeInternal();

}
