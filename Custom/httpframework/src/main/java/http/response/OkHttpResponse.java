package http.response;

import java.io.InputStream;

import http.HttpStatus;
import http.header.HttpHeader;
import http.response.AbstractHttpResponse;
import okhttp3.Response;

/**
 * Created by lei on 2017/5/11.
 */
public class OkHttpResponse extends AbstractHttpResponse {


    private Response mResponse;
    private HttpHeader mHeader;

    public OkHttpResponse(Response response){
        this.mResponse = response;
    }

    @Override
    protected InputStream getBodyInternal() {
        return mResponse.body().byteStream();
    }

    @Override
    protected void closeInternal() {
        mResponse.body().close();
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.getValue(mResponse.code());
    }

    @Override
    public String getStatusMsg() {
        return mResponse.message();
    }

    @Override
    public HttpHeader getHeader() {
        if (mHeader == null){
            mHeader = new HttpHeader();
        }
        for (String name : mResponse.headers().names()){
            mHeader.set(name,mResponse.headers().get(name));
        }
        return mHeader;
    }
}
