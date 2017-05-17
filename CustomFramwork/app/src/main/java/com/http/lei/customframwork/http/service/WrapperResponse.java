package com.http.lei.customframwork.http.service;


import com.http.lei.customframwork.http.service.convert.Convert;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lei on 2017/5/17.
 */
public class WrapperResponse extends FrameworkResponse<String> {

    private FrameworkResponse mResponse;
    private List<Convert> mConvert;


    public WrapperResponse(FrameworkResponse response,List<Convert> converts){
        this.mResponse = response;
        this.mConvert = converts;
    }

    @Override
    public void success(FrameworkRequest request, String data) {
        for (Convert convert : mConvert) {
            if (convert.isCanParse(request.getContentType())){
                try {
                    Object object = convert.parse(data, getType());
                    mResponse.success(request,object);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }


    private Type getType(){
        Type type = mResponse.getClass().getGenericSuperclass();
        Type[] paramType = ((ParameterizedType)type).getActualTypeArguments();
        return paramType[0];
    }


    @Override
    public void fail(int errorCode, String errorMsg) {
        mResponse.fail(errorCode, errorMsg);
    }
}
