package com.http.lei.packagehttptool.http;

import com.google.gson.internal.$Gson$Types;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import okhttp3.Request;

/**
 * Created by lei on 2017/5/31.
 */
public abstract class ResultCallback<T> {

    Type mType;

    public ResultCallback(){
        mType = getSuperclassTypeParameter(getClass());
    }

    private static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public abstract void onResponse(T response);

    public abstract void onFail(Request request,Exception e);

}
