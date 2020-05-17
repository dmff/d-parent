package com.dmf.boot.elasticsearch.aggregations;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.junit.Test;

/**
 * @author dmf
 * @date 2018/7/24
 */
public class StructuringAggregations extends ElasticsearchClientBase {

    @Test
    public void testStructuringAggregations() throws Exception {
        /*SearchResponse sr = client.prepareSearch().addAggregation(
                AggregationBuilders.terms("user").field("kimchy")
                        .subAggregation(AggregationBuilders.dateHistogram("by_year")
                                        .field("postDate")
                                        .dateHistogramInterval(DateHistogramInterval.YEAR)
                                        .subAggregation(AggregationBuilders.avg("avg_children").field("children")))
                ).execute().actionGet();
        println(sr);*/
    }

}
