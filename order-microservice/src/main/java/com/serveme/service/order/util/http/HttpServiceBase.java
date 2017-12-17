package com.serveme.service.order.util.http;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Davids-iMac on 23/10/15.
 */
public class HttpServiceBase {


    private String url = "localhost";

    private Integer port = 80;

    private String path;

    private HttpMethod httpMethod = HttpMethod.GET;

    private String entireUrl;

    private Map<String, String> pathParams;

    private Map<String, String> headers;

    private Object body;

    private Map<String, Object> flexibleBody;

    private Type returnedClass;

    private HttpClient httpClient;

    private boolean isList;


    private static HttpClient getHttpClient() {

        HttpClient client = HttpClientBuilder.create().build();
        return client;
    }


    public HttpServiceBase setUrl(String url) {
        this.url = url;
        return this;
    }

    public HttpServiceBase setPort(int port) {
        this.port = port;
        return this;
    }

    public HttpServiceBase setPath(String path) {
        this.path = path;
        return this;
    }

    public HttpServiceBase setHttpMethod(HttpMethod method) {
        this.httpMethod = method;
        return this;
    }

    public HttpServiceBase setReturnedClass(Type returnedClass) {
        this.returnedClass = returnedClass;
        return this;
    }

    public HttpServiceBase setHeader(String param, String value) {
        if (headers == null) headers = new HashMap<String, String>();
        headers.put(param, value);
        return this;
    }

    public HttpServiceBase setPathParam(String param, String value) {
        if (pathParams == null) pathParams = new HashMap<String, String>();
        pathParams.put(param, value);
        return this;
    }

    public HttpServiceBase setBody(Object body) {
        this.body = body;
        return this;
    }

    public HttpServiceBase openBody() {
        flexibleBody = new HashMap<String, Object>();
        return this;
    }


    //Needs to be amplified to several levels
    public HttpServiceBase setBodyValue(String field, Object object) {
        if (flexibleBody == null)
            throw new RuntimeException("Body has to be opened before setting a value");

        flexibleBody.put(field, object);
        return this;
    }


    public HttpServiceBase setIsList(boolean isList) {
        this.isList = isList;
        return this;
    }

    public Object call() {

        if (httpClient == null)
            httpClient = getHttpClient();

        if (port != 80) {
            entireUrl = url + ":" + port;
        } else {
            entireUrl = url;
        }
        if (!path.startsWith("/"))
            entireUrl += "/";

        this.buildPathParams();
        entireUrl += path;


        if (HttpMethod.PUT.equals(this.httpMethod)) {
            return PUT();

        } else if (HttpMethod.DELETE.equals(this.httpMethod)) {
            return this.DELETE();
        } else if (HttpMethod.GET.equals(this.httpMethod)) {
            return this.GET();

        } else if (HttpMethod.POST.equals(this.httpMethod)) {
            return this.POST();
        }


        return null;
    }


    private void buildPathParams() {

        if (pathParams != null && pathParams.size() > 0) {
            Iterator<Map.Entry<String, String>> iter = pathParams.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                path = path.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }


    }

    protected Object GET() {

        try {
            HttpGet request = new HttpGet(entireUrl);

            if (headers != null) {
                Iterator<Map.Entry<String, String>> headerIterator = headers.entrySet().iterator();
                while (headerIterator.hasNext()) {
                    Map.Entry<String, String> header = headerIterator.next();
                    request.addHeader(header.getKey(), header.getValue());

                }
            }

            HttpResponse response = httpClient.execute(request);
            int responseStatus = response.getStatusLine().getStatusCode();


            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            String json = result.toString();
            if (responseStatus >= 200 && responseStatus < 300) {
                return new Gson().fromJson(json, returnedClass);

            } else {

                throw new HttpServiceException(responseStatus, json);
            }


        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getMessage());

        } catch (ClientProtocolException ex) {
            throw new RuntimeException(ex.getMessage());

        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


    }


    protected Object DELETE() {
        try {
            HttpDelete deleteRquest = new HttpDelete(entireUrl);
            HttpResponse response = httpClient.execute(deleteRquest);

            int responseStatus = response.getStatusLine().getStatusCode();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String line;
            StringBuilder responseString = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseString.append(line);
            }


            if (responseStatus >= 200 && responseStatus < 300) {


                return this.returnedClass != null ? new Gson().fromJson(responseString.toString(), this.returnedClass)
                        : null;
            } else {
                throw new HttpServiceException(responseStatus, responseString.toString());
            }

        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getMessage());

        } catch (ClientProtocolException ex) {
            throw new RuntimeException(ex.getMessage());

        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }


    }


    protected Object PUT() {

        try {
            HttpPut putRequest = new HttpPut(entireUrl);
            StringEntity entity = new StringEntity(new Gson().toJson(body));
            entity.setContentType("application/json");
            putRequest.setEntity(entity);
            HttpResponse response = httpClient.execute(putRequest);

            int responseStatus = response.getStatusLine().getStatusCode();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String line;
            StringBuilder responseString = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseString.append(line);
            }

            if (responseStatus >= 200 && responseStatus < 300) {


                Object obj = this.returnedClass != null ? new Gson().fromJson(responseString.toString(), returnedClass)
                        : null;
                return obj;
            } else {
                throw new HttpServiceException(responseStatus, responseString.toString());
            }

        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getMessage());

        } catch (ClientProtocolException ex) {
            throw new RuntimeException(ex.getMessage());

        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    protected Object POST() {

        try {
            HttpPost postRequest = new HttpPost(entireUrl);
            StringEntity entity = new StringEntity(new Gson().toJson(body), Charset.forName("UTF-8"));
            entity.setContentType("application/json");
            postRequest.setEntity(entity);
            HttpResponse response = httpClient.execute(postRequest);

            int responseStatus = response.getStatusLine().getStatusCode();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String line;
            StringBuilder responseString = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseString.append(line);
            }

            if (responseStatus >= 200 && responseStatus < 300) {

                return this.returnedClass != null ? new Gson().fromJson(responseString.toString(), this.returnedClass)
                        : null;
            } else {
                throw new HttpServiceException(responseStatus, responseString.toString());
            }

        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getMessage());

        } catch (ClientProtocolException ex) {
            throw new RuntimeException(ex.getMessage());

        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }


//    protected Object POST(){
//        if(pathParams != null){
//            return httpClient.postForObject(entireUrl, flexibleBody!=null? flexibleBody : body, returnedClass, pathParams);
//        }else{
//            return httpClient.postForObject(entireUrl, flexibleBody!=null? flexibleBody : body, returnedClass);
//        }
//    }
//
//

//
//

//


    @Override
    public String toString() {
        return "HttpServiceBase{" +
                "url='" + url + '\'' +
                ", port=" + port +
                ", path='" + path + '\'' +
                ", httpMethod=" + httpMethod +
                ", entireUrl='" + entireUrl + '\'' +
                ", pathParams=" + pathParams +
                ", headers=" + headers +
                ", body=" + body +
                ", flexibleBody=" + flexibleBody +
                ", returnedClass=" + returnedClass +
                ", httpClient=" + httpClient +
                '}';
    }


}
