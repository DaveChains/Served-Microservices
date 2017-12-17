package com.serveme.service.searching.util.es;

/**
 * Created by Davids-iMac on 28/10/15.
 */
public class ElasticSearchResultPut extends ElasticSearchResult {

    public String created;
    public Shard _shards;

    @Override
    public String toString() {
        return "ElasticSearchResultPut{" +
                "created='" + created + '\'' +
                ", _shards=" + _shards +
                '}';
    }

    public class Shard {

        private int total;
        private int successful;
        private int failed;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSuccessful() {
            return successful;
        }

        public void setSuccessful(int successful) {
            this.successful = successful;
        }

        public int getFailed() {
            return failed;
        }

        public void setFailed(int failed) {
            this.failed = failed;
        }
    }

}
