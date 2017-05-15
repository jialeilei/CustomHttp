package com.http.lei.customframwork.http.header;

import java.util.Map;

/**
 * Created by lei on 2017/5/10.
 */
public interface NameValueMap<K,V> extends Map<K,V>{

    String get(String key);

    void set(String key, String value);

    void setAll(Map<String, String> map);

}
