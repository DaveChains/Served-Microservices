package com.serveme.service.searching.util.es;

/**
 * Created by Davids-iMac on 28/10/15.
 */
public class ElasticSearchResult {

    public String _index;
    public String _type;
    public String _id;
    public int _version;

    @Override
    public String toString() {
        return "ElasticSearchResult{" +
                "_index='" + _index + '\'' +
                ", _type='" + _type + '\'' +
                ", _id='" + _id + '\'' +
                ", _version=" + _version +
                '}';
    }
}
