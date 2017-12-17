package com.serveme.service.searching.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edgar on 12/12/2015.
 */
public class SearchObject implements Serializable {

    private Location location;
    private String termsIn = "";
    private boolean showTestRestaurants;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTermsIn() {
        return termsIn;
    }

    public void setTermsIn(String termsIn) {
        this.termsIn = termsIn;
    }

    public boolean isShowTestRestaurants() {
        return showTestRestaurants;
    }

    public void setShowTestRestaurants(boolean showTestRestaurants) {
        this.showTestRestaurants = showTestRestaurants;
    }

    public SearchQuery buildQuery() {
        if (showTestRestaurants) {
            return new SearchQuery(location, termsIn);
        }
        return new LiveSearchQuery(location, termsIn);
    }

    private class SearchQuery {
        public Sort sort;
        public Query query;
        public int size;

        public SearchQuery(Location location, String termsIn) {
            sort = new Sort(location);
            query = new Query(termsIn);
            size = (termsIn != null && termsIn.length() > 0) ? 10 : 10;
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
            public Multimatch multi_match;

            public Query(String termsIn) {
                multi_match = new Multimatch(termsIn);
            }

            private class Multimatch {
                public String query;
                public List<String> fields;

                public Multimatch(String termsIn) {
                    query = termsIn;
                    fields = new ArrayList<>();
                    fields.add("name");
                    fields.add("description");
                    fields.add("menuCategories.dishes.name");
                    fields.add("menuCategories.dishes.description");
                }
            }
        }
    }

    private class LiveSearchQuery extends SearchQuery {
        public Filter filter;

        public LiveSearchQuery(Location location, String termsIn) {
            super(location, termsIn);
            filter = new Filter();
        }

        private class Filter {
            Term term = new Term();
        }

        private class Term {
            boolean liveRestaurant = true;
        }
    }

    @Override
    public String toString() {
        return "SearchObject{" +
                "location=" + location.toString() +
                ", termsIn='" + termsIn + '\'' +
                ", showTestRestaurants=" + showTestRestaurants +
                '}';
    }
}
