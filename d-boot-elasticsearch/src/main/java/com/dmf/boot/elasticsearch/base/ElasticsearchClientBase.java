package com.dmf.boot.elasticsearch.base;

import com.dmf.boot.elasticsearch.util.Utils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;


public abstract class ElasticsearchClientBase extends ElasticsearchClient {

    protected SearchResponse twitterPrepareSearch(QueryBuilder qb) {
        SearchResponse response = client.prepareSearch("bank")//可以是多个index
                .setTypes("account")//可以是多个类型
                .setQuery(qb)    // Query 查询条件
                .get();

        println(response);
        return response;
    }

    protected SearchResponse branchPrepareSearch(QueryBuilder qb) {
        SearchResponse response = client.prepareSearch("company")
                .setTypes("branch")
                .setQuery(qb)
                .get();

        println(response);
        return response;
    }

    protected SearchResponse employeePrepareSearch(QueryBuilder qb) {
        SearchResponse response = client.prepareSearch("company")
                .setTypes("employee")
                .setQuery(qb)
                .get();

        println(response);
        return response;
    }

    protected void println(SearchResponse searchResponse) {
        Utils.println(searchResponse);
    }

}
