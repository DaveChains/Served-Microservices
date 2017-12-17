package com.serveme.service.searching.httpService;

import com.serveme.service.searching.domain.RestaurantDomain;
import com.serveme.service.searching.util.StringUtil;
import com.serveme.service.searching.util.es.ElasticSearchArrayResult;
import com.serveme.service.searching.util.es.ElasticSearchResultPut;
import com.serveme.service.searching.util.http.HttpServiceBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * Created by Davids-iMac on 23/10/15.
 */
@Component
public class HttpServiceFactory extends HttpServiceBase {

    @Value("${spring.data.elasticsearch.host}")
    private String host;

    @Value("${spring.data.elasticsearch.port}")
    private int port;

    @Value("${spring.data.elasticsearch.index}")
    private String index;

    @Value("${spring.data.elasticsearch.type}")
    private String type;

    //SERVICES PATH
    protected static String GET_RESTAURANT = "/test2/restaurant/{id}";

    protected static String PUT_RESTAURANT = "/test2/restaurant/{id}";

    private String buildPath(String index, String type, String rest) {
        return "/" + index + "/" + type + (!StringUtil.nullEmpty(rest) ? rest : "");
    }

    public HttpServiceBase getRestaurantService(long id) {

        return new HttpServiceBase()
                .setUrl("http://" + host + ":" + port)
                .setHttpMethod(HttpMethod.GET)
                .setPath(buildPath(index, type, "/{id}"))
                .setPathParam("id", String.valueOf(id))
                .setReturnedClass(RestaurantDomain.class);

    }

    public HttpServiceBase createRestaurant(RestaurantDomain restaurant) {
        return new HttpServiceBase()
                .setUrl("http://" + host + ":" + port)
                .setHttpMethod(HttpMethod.PUT)
                .setPath(buildPath(index, type, "/{id}"))
                .setPathParam("id", String.valueOf(restaurant.getId()))
                .setBody(restaurant)
                .setReturnedClass(ElasticSearchResultPut.class);
    }

    public HttpServiceBase findRestaurant(Object searchObject) {

        HttpServiceBase httpServiceBase = new HttpServiceBase()
                .setUrl("http://" + host)
                .setPort(port)
                .setHttpMethod(HttpMethod.POST)
                .setPath(buildPath(index, type, "/_search"))
                .setBody(searchObject)
                .setReturnedClass(ElasticSearchArrayResult.class);

        System.out.println("/_search-->  "  + httpServiceBase.toString());
        return httpServiceBase;
    }
}
