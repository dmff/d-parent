package com.dmf.boot.elasticsearch.test;

import com.dmf.boot.elasticsearch.base.BaseTest;
import com.dmf.boot.elasticsearch.util.Utils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.junit.Test;

/**
 * @author dmf
 * @date 2018/8/29
 */
public class WildcardTest extends BaseTest {

    @Test
    public void testKeyWord() {
        //证明keyword类型也是可以进行模糊查询的
        QueryBuilder queryBuild = QueryBuilders.wildcardQuery("author","张*");
        SearchResponse response = search("book", "novel", queryBuild);
        Utils.println(response);
    }

    @Test
    public void deleteType(){
        DeleteByQueryAction.INSTANCE.newRequestBuilder(client).source("open")
                .filter(QueryBuilders.termQuery("id", "2")).execute().actionGet();
    }

}
