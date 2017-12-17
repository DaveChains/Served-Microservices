package com.serveme.service.searching.util.es;

/**
 * Created by Davids-iMac on 28/10/15.
 */
public class ElasticSearchResultGet extends ElasticSearchResult {

    public String found;

    @Override
    public String toString() {
        return "util.ElasticSearchResult{" +
                "_index='" + _index + '\'' +
                ", _type='" + _type + '\'' +
                ", _id='" + _id + '\'' +
                ", _version=" + _version +
                ", found='" + found + '\'' +
                '}';
    }

}
