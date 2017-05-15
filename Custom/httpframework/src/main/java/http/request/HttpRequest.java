package http.request;


import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import http.HttpMethod;
import http.response.HttpResponse;


/**
 * Created by lei on 2017/5/11.
 */
public interface HttpRequest {

    HttpMethod getMethod();

    URI getUri();

    OutputStream getBody();

    HttpResponse execute() throws IOException;

}
