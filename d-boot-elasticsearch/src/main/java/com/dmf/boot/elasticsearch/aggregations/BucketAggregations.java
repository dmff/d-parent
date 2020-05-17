package com.dmf.boot.elasticsearch.aggregations;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filters.Filters;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.global.Global;
import org.junit.Test;

/**
 * 桶分聚合
 * @author dmf
 * @date 2018/7/24
 */
public class BucketAggregations extends ElasticsearchClientBase {

    @Test
    public void testGlobalAggregation() throws Exception {
        AggregationBuilder aggregation = AggregationBuilders
                .global("agg")
                .subAggregation(AggregationBuilders.terms("ages").field("age"));

        SearchResponse sr = client.prepareSearch("bank")
                .addAggregation(aggregation)
                .get();

        Global agg = sr.getAggregations().get("agg");
        long count = agg.getDocCount();

        System.out.println("global count:" + count);
    }

    @Test
    public void testFilterAggregation() throws Exception {

        AggregationBuilder aggregation = AggregationBuilders.filters("agg",
                                new FiltersAggregator.KeyedFilter("agg1", QueryBuilders.termQuery("age", "36")),
                                new FiltersAggregator.KeyedFilter("agg2", QueryBuilders.termQuery("age", "37")));

        SearchResponse sr = client.prepareSearch("bank")
                .addAggregation(aggregation)
                .get();

        Filters agg = sr.getAggregations().get("agg");

        for (Filters.Bucket entry : agg.getBuckets()) {
            String key = entry.getKeyAsString();
            long docCount = entry.getDocCount();

            System.out.println("global " + key + " count:" + docCount);
        }

    }
}
