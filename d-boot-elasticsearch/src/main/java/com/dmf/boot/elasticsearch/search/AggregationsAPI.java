package com.dmf.boot.elasticsearch.search;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import com.dmf.boot.elasticsearch.util.Utils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.junit.Test;

/**
 * @author dmf
 * @date 2018/7/24
 */
public class AggregationsAPI extends ElasticsearchClientBase {

    @Test
    public void testSearchAggregations() throws Exception {
        SearchResponse sr = client.prepareSearch("bank").setTypes("account").setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.terms("agg1").field("age"))
                //.addAggregation(AggregationBuilders.terms("agg2").field("city"))
                .get();


        Terms agg1 = sr.getAggregations().get("agg1");
        //Histogram agg2 = sr.getAggregations().get("agg2");

         Utils.println(sr);
        System.out.println(agg1.getName());
        System.out.println(agg1.getBuckets().toString());

        //注意： 可能会报Fielddata is disabled on text fields by default
        //聚合这些操作用单独的数据结构(fielddata)缓存到内存里了，需要单独开启，官方解释在此fielddata
        //https://www.elastic.co/guide/en/elasticsearch/reference/current/fielddata.html
    }
}
