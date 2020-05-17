package com.dmf.boot.elasticsearch.aggregations;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.junit.Test;

/**
 * @author dmf
 * @date 2018/7/24
 */
public class MetricsAggregations extends ElasticsearchClientBase {

    @Test
    public void testMinAggregation() throws Exception {
        MinAggregationBuilder aggregation = AggregationBuilders
                .min("agg")
                .field("age");

        SearchResponse sr = client.prepareSearch("bank")
                .addAggregation(aggregation)
                .get();

        Min agg = sr.getAggregations().get("agg");
        String value = agg.getValueAsString();//这个统计的是日期，一般用下面方法获得最小值

        System.out.println("min value:" + value);
    }

    @Test
    public void testMaxAggregation() throws Exception {
        MaxAggregationBuilder aggregation = AggregationBuilders
                .max("agg")
                .field("age");
        SearchResponse sr = client.prepareSearch("bank")
                .addAggregation(aggregation)
                .get();

        Max agg = sr.getAggregations().get("agg");

        String value = agg.getValueAsString();
        System.out.println("max value:" + value);
    }

    @Test
    public void testSumAggregation() throws Exception {
        SumAggregationBuilder aggregation = AggregationBuilders
                .sum("agg")
                .field("age");

        SearchResponse sr = client.prepareSearch("bank")
                .addAggregation(aggregation)
                .get();
        Sum agg = sr.getAggregations().get("agg");

        String value = agg.getValueAsString();
        System.out.println("sum value:" + value);
    }

    @Test
    public void testAvgAggregation() throws Exception {

    }

    @Test
    public void testStatsAggregation() throws Exception {

    }

    @Test
    public void testExtendedStatsAggregation() throws Exception {

    }

    @Test
    public void testValueCountAggregation() throws Exception {

    }

    @Test
    public void testPrepareAggregation() throws Exception {

    }

    @Test
    public void testPrepareRanksAggregation() throws Exception {

    }

    @Test
    public void testCardinalityAggregation() throws Exception {

    }

    @Test
    public void testGeoBoundsAggregation() throws Exception {

    }

    @Test
    public void testTopHitsAggregation() throws Exception {

    }

    @Test
    public void testScriptedMetricAggregation() throws Exception {

    }

}
