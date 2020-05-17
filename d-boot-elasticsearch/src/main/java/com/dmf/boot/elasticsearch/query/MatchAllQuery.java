package com.dmf.boot.elasticsearch.query;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
/**
 * 最简单的查询，它匹配所有文档
 * 官方文档 @see <a href='https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-query-dsl-match-all-query.html'></a>
 * 中文文档 @see <a href='https://es.quanke.name/query-dsl/match-all-query.html'></a>
 * @author dmf
 * @date 2018/7/24
 */
public class MatchAllQuery extends ElasticsearchClientBase {

    @Test
    public void testMatchAllQuery() throws Exception{
        QueryBuilder queryBuilder = matchAllQuery();
        twitterPrepareSearch(queryBuilder);
    }
}
