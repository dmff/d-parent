package com.dmf.boot.elasticsearch.query;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.elasticsearch.index.query.CommonTermsQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

/**
 * @author dmf
 * @date 2018/7/24
 */
public class FullTextQueries extends ElasticsearchClientBase {

    /**
     * 用于执行全文查询的标准查询，包括模糊匹配和词组或邻近程度的查询
     * @throws Exception
     */
    @Test
    public void testMatchQuery() throws Exception{
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("address", "Avenue");
        twitterPrepareSearch(queryBuilder);
    }

    @Test
    public void testMultiMatchQuery() throws Exception {
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("Beth", "lastname","email","firstname");
        twitterPrepareSearch(queryBuilder);
    }

    @Test
    public void testCommonTermsQuery() throws Exception {
        CommonTermsQueryBuilder queryBuilder = QueryBuilders.commonTermsQuery("address", "Avenue");
        twitterPrepareSearch(queryBuilder);
    }

}
