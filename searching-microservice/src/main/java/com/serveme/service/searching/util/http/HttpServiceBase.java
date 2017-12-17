package com.serveme.service.searching.util.http;

import com.google.gson.Gson;
import com.serveme.service.searching.util.es.ElasticSearchResultGet;
import com.serveme.service.searching.util.es.ElasticSearchResultPut;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Davids-iMac on 23/10/15.
 */
public class HttpServiceBase {


    private String url = "localhost";

    private Integer port = 8080;

    private String path;

    private HttpMethod httpMethod = HttpMethod.GET;

    private String entireUrl;

    private Map<String, String> pathParams;

    private Object body;

    private Map<String, Object> flexibleBody;

    private Class returnedClass;

    private HttpClient httpClient;

    private Map<String, String> headers;
    private boolean nonElastic;

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

    public HttpServiceBase setReturnedClass(Class returnedClass) {
        this.returnedClass = returnedClass;
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


    public Object call() {

        try {
            if (httpClient == null)
                httpClient = getHttpClient();

            entireUrl = url + ":" + port;
            if (!path.startsWith("/"))
                entireUrl += "/";

            this.buildPathParams();
            entireUrl += path;

            if (HttpMethod.GET.equals(this.httpMethod)) {
                return this.GET();

            } else if (HttpMethod.PUT.equals(this.httpMethod)) {
                return PUT();

            } else if (HttpMethod.POST.equals(this.httpMethod)) {
                return POST();

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

//      else if(HttpMethod.POST.equals(this.httpMethod)){
//            return this.POST();
//
//        }if(HttpMethod.DELETE.equals(this.httpMethod)){
//            return this.DELETE();
//
//        }

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

    protected Object GET() throws Exception {
        HttpGet request = new HttpGet(entireUrl);

        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                request.addHeader(header.getKey(), header.getValue());
            }
        }

        HttpResponse response = httpClient.execute(request);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        String json = result.toString();
        if (nonElastic) {
            return new Gson().fromJson(json, returnedClass);
        }

        int sourceIndex = json.indexOf(",\"_source");

        String metadata = sourceIndex > 0 ? json.substring(0, sourceIndex) + "}" : json;

        Gson gson = new Gson();
        ElasticSearchResultGet elasticSearchResult = gson.fromJson(metadata, ElasticSearchResultGet.class);

        if ("true".equals(elasticSearchResult.found)) {
            int lastIndex = json.lastIndexOf("}");
            String source = json.substring(sourceIndex + 11, lastIndex);
            return gson.fromJson(source, returnedClass);
        } else {
            return null;
        }
    }


    protected Object PUT() throws Exception {

        HttpPut putRequest = new HttpPut(entireUrl);
        StringEntity entity = new StringEntity(new Gson().toJson(body), "application/json", "utf-8");
        putRequest.setEntity(entity);
        HttpResponse response = httpClient.execute(putRequest);

        if (response.getStatusLine().getStatusCode() < 200 || response.getStatusLine().getStatusCode() >= 300) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        }

        BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));

        String line;
        StringBuilder responseString = new StringBuilder();
        while ((line = br.readLine()) != null) {
            responseString.append(line);
        }

        ElasticSearchResultPut result = new Gson().fromJson(responseString.toString(), ElasticSearchResultPut.class);

        return result;
    }

    protected Object POST() throws Exception {
        try {
            System.out.println("POST----------------./?> " + entireUrl.toString());
            HttpPost postRequest = new HttpPost(entireUrl);
            StringEntity entity = new StringEntity(new Gson().toJson(body));
            entity.setContentType("application/json");
            postRequest.setEntity(entity);

            System.out.println("entity----------------./?> " + entity.toString());
            System.out.println("entity json-----------./?> " + new Gson().toJson(body));
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
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public HttpServiceBase setHeader(String param, String value) {
        if (headers == null) headers = new HashMap<String, String>();
        headers.put(param, value);
        return this;
    }

    public HttpServiceBase setNonElastic(boolean nonElastic) {
        this.nonElastic = nonElastic;
        return this;
    }

//
//
//    protected Object DELETE(){
//        if(pathParams != null){
//            httpClient.delete(entireUrl, pathParams);
//        }else{
//            httpClient.delete(entireUrl, body);
//        }
//        return null;
//    }
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
                ", body=" + body +
                ", flexibleBody=" + flexibleBody +
                ", returnedClass=" + returnedClass +
                ", httpClient=" + httpClient +
                ", headers=" + headers +
                ", nonElastic=" + nonElastic +
                '}';
    }
}
