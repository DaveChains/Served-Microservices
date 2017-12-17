package com.serveme.service.searching.util.es;

import com.serveme.service.searching.domain.RestaurantDomain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Davids-iMac on 28/10/15.
 */
public class ElasticSearchArrayResult {

    public int took;
    public boolean timed_out;
    public ElasticSearchResultPut.Shard _shards;
    public Hits hits;

    public List<RestaurantDomain> getRestaurants() {
        List<RestaurantDomain> list = new ArrayList<RestaurantDomain>();
        for (Hits.Results result : hits.hits) {
            if (result._source.isActive())
                list.add(result._source);
        }
        RestaurantDomain.determinateOpenRestaurants(list);
        Collections.sort(list, RestaurantDomain.comparator);
        return list;
    }

    /**
     * Note: distance filter currently ignored
     *
     * @param distance in kilometers
     * @return filtered results by distance
     */
    public List<RestaurantDomain> filterByMaxDistance(double distance) {
        List<RestaurantDomain> list = new ArrayList<RestaurantDomain>();
        for (Hits.Results result : hits.hits) {
            if (result._source.isActive() && result.sort.get(0) < distance) {
                list.add(result._source);
            }
        }
        RestaurantDomain.determinateOpenRestaurants(list);
        Collections.sort(list, RestaurantDomain.comparator);
        return list;
    }

    private class Hits {
        public int total;
        public Double max_score;
        public List<Results> hits;

        private class Results extends ElasticSearchResult {
            public Double _score;
            public RestaurantDomain _source;
            public List<Double> sort;
        }
    }

    @Override
    public String toString() {
        return "ElasticSearchArrayResult{" +
                "took=" + took +
                ", timed_out=" + timed_out +
                ", _shards=" + _shards +
                ", hits=" + hits +
                '}';
    }
}
