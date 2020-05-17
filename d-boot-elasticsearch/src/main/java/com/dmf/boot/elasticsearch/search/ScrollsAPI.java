package com.dmf.boot.elasticsearch.search;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.elasticsearch.action.search.ClearScrollRequestBuilder;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

/**
 * @author dmf
 * @date 2018/7/24
 */
public class ScrollsAPI extends ElasticsearchClientBase {

    private String scrollId;

    @Test
    public void testScrolls() throws Exception {
        SearchResponse searchResponse = client.prepareSearch("bank").addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(60000))  //为了使用 scroll，初始搜索请求应该在查询中指定 scroll 参数，告诉 Elasticsearch 需要保持搜索的上下文环境多长时间（滚动时间）
                .setQuery(QueryBuilders.termQuery("age", "36"))
                .setSize(5).get();

        scrollId = searchResponse.getScrollId();
        do{
            for (SearchHit searchHitFields : searchResponse.getHits().getHits()) {
                System.out.println(searchHitFields.getSource().toString());
            }

            searchResponse = client.prepareSearchScroll(scrollId).setScroll(new TimeValue(60000)).execute().actionGet();

        }while (searchResponse.getHits().getHits().length !=0);
    }

    @Override
    public void tearDown() throws Exception {
        ClearScrollRequestBuilder prepareClearScroll = client.prepareClearScroll();
        prepareClearScroll.addScrollId(scrollId);
        ClearScrollResponse response = prepareClearScroll.get();
        if(response.isSucceeded()){
            System.out.println("清除成功");
        }

        super.tearDown();
    }
}
