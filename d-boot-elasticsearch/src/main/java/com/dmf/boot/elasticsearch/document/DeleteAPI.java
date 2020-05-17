package com.dmf.boot.elasticsearch.document;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.junit.Test;

/**
 * @author dmf
 * @date 2018/7/24
 */
public class DeleteAPI extends ElasticsearchClientBase {

    @Test
    public void testForDeleteAPI() throws Exception {
        DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").get();
        System.out.println("删除成功！");
    }

    @Test
    public void testForDeleteByQuery() throws Exception {
       /* MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        SearchResponse searchResponse = client.prepareSearch("twitter").setTypes("tweet").setQuery(matchAllQueryBuilder).get();
        Utils.println(searchResponse);*/
        //DeleteByQueryAction.INSTANCE.newRequestBuilder(client).filter(QueryBuilders.termQuery("id", 2)).execute().actionGet();

        DeleteByQueryAction.INSTANCE.newRequestBuilder(client).source("twitter").filter(QueryBuilders.termQuery("id", "2")).execute().actionGet();
        System.out.println("删除成功！");
    }

    /*@Test
    public void testForAsyncDeleteByQuery() throws Exception {
        *//**
         * 删除时间过长，使用异步操作
         *//*
        DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .source("twitter")
                .filter(QueryBuilders.matchAllQuery())
                .execute(new ActionListener<BulkByScrollResponse>() {

                    @Override
                    public void onResponse(BulkByScrollResponse bulkByScrollResponse) {
                        long deleted = bulkByScrollResponse.getDeleted();
                        System.out.println("异步删除成功！" + deleted);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        System.out.println("异步删除失败:" + e.getMessage());
                    }
                });
    }*/

}
