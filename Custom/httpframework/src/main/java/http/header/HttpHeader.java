package http.header;


import android.support.annotation.NonNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lei on 2017/5/10.
 */
public class HttpHeader implements NameValueMap<String, String> {


    private Map<String,String> mMap = new HashMap<>();

    public final static String ACCEPT = "Accept";
    public final static String PRAGMA = "Pragma";
    public final static String USER_AGENT = "User-Agent";
    public final static String PROXY_CONNECTION = "Proxy-Connection";//proxy
    public final static String ACCEPT_ENCODING = "Accept-Encoding";
    public final static String CACHE_CONTROL = "Cache-Control";
    public final static String CONTENT_ENCODING = "Content-Encoding";
    public final static String CONTENT_LENGTH = "Content-Length";
    public final static String CONNECTION = "Connection";


    public String getConnection(){
        return get(CONNECTION);
    }

    public void setConnection(String value){
        set(CONNECTION,value);
    }

    public String getContentLength(){
        return get(CONTENT_LENGTH);
    }

    public void setContentLength(String value){
        set(CONTENT_LENGTH,value);
    }

    public String getContentEncoding(){
        return get(CONTENT_ENCODING);
    }

    public void setContentEncoding(String value){
        set(CONTENT_ENCODING,value);
    }

    public String getCacheControl(){
        return get(CACHE_CONTROL);
    }

    public void setCacheControl(String value){
        set(CACHE_CONTROL, value);
    }

    public String getAcceptEncoding(){
        return get(ACCEPT_ENCODING);
    }

    public void setAcceptEncoding(String value){
        set(ACCEPT_ENCODING,value);
    }

    public String getProxyConnection(){
        return get(PROXY_CONNECTION);
    }

    public void setProxyConnection(String value){
        set(PROXY_CONNECTION,value);
    }

    public String getUserAgent(){
        return get(USER_AGENT);
    }

    public void setUserAgent(String value){
        set(USER_AGENT,value);
    }

    public String getPragma(){
        return get(PRAGMA);
    }

    public void setPragma(String value){
        set(PRAGMA,value);
    }

    public String getAccept(){
        return get(ACCEPT);
    }

    public void setAccept(String value){
        set(ACCEPT, value);
    }

    public String get(String key) {
        return mMap.get(key);
    }

    @Override
    public void set(String key, String value) {
        mMap.put(key,value);
    }

    @Override
    public void setAll(Map<String, String> map) {
        mMap.putAll(map);
    }

    @Override
    public int size() {
        return mMap.size();
    }

    @Override
    public boolean isEmpty() {
        return mMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return mMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return mMap.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return mMap.get(key);
    }

    @Override
    public String put(String key, String value) {
        return mMap.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return mMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        mMap.putAll(m);
    }

    @Override
    public void clear() {
        mMap.clear();
    }

    @NonNull
    @Override
    public Set<String> keySet() {
        return mMap.keySet();
    }

    @NonNull
    @Override
    public Collection<String> values() {
        return mMap.values();
    }

    @NonNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return mMap.entrySet();
    }

}
