package com.serveme.service.searching.domain;


/**
 * Created by Edgar on 1/8/2016.
 */
public class DefaultSearchObject {
    public Sort sort;
    public Query query;
    public int size = 10;

    public DefaultSearchObject(Location location) {
        sort = new Sort(location);
        query = new Query();
    }

    public static DefaultSearchObject buildQuery(Location location, boolean isTestUser) {
        if (isTestUser)
            return new DefaultSearchObject(location);
        else
            return new DefaultLiveSearchObject(location);
    }

    private static class DefaultLiveSearchObject extends DefaultSearchObject {
        public Filter filter;

        public DefaultLiveSearchObject(Location location) {
            super(location);
            filter = new Filter();
        }

        private class Filter {
            Term term = new Term();
        }

        private class Term {
            boolean liveRestaurant = true;
        }
    }

    private class Sort {
        public GeoDistance _geo_distance;

        public Sort(Location location) {
            this._geo_distance = new GeoDistance(location);
        }

        private class GeoDistance {
            public Location location;
            public String order = "asc";
            public String unit = "km";

            public GeoDistance(Location location) {
                this.location = location;
            }
        }
    }

    private class Query {
        public Multimatch match_all;

        public Query() {
            match_all = new Multimatch();
        }

        private class Multimatch {
        }
    }
}
