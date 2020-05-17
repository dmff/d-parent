package com.dmf.boot.elasticsearch.search;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import com.dmf.boot.elasticsearch.util.Utils;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

/**
 * @author dmf
 * @date 2018/7/24
 */
public class SearchAPI extends ElasticsearchClientBase {

    @Test
    public void testPrepareSearch() throws Exception {
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("age", "36");
        SearchResponse searchResponse = client.prepareSearch("bank").setTypes("account").setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(queryBuilder)
                .setFrom(0).setSize(60)
                .addSort("account_number", SortOrder.ASC)
                .setExplain(true)
                .get();
        Utils.println(searchResponse);
    }

    /**
     * 多条件搜索
     * @throws Exception
     */
    @Test
    public void testMultiSearch() throws Exception {
        SearchRequestBuilder srb1 = client
                .prepareSearch().setQuery(QueryBuilders.queryStringQuery("Street")).setSize(1);

        SearchRequestBuilder srb2 = client
                .prepareSearch().setQuery(QueryBuilders.matchQuery("gender", "F")).setSize(1);

        MultiSearchResponse sr = client.prepareMultiSearch()
                .add(srb1)
                .add(srb2)
                .get();

        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits();
            println(response);
        }
        System.out.println("nbHits:" + nbHits);
    }
}
