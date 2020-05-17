package com.dmf.boot.elasticsearch.document;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import com.dmf.boot.elasticsearch.util.Utils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

/**
 * @author dmf
 * @date 2018/7/24
 */
public class GetAPI extends ElasticsearchClientBase {

    @Test
    public void testForGetApi() throws Exception {
        GetResponse response = client.prepareGet("twitter", "tweet", "10").get();

        /*GetResponse response = client.prepareGet("twitter", "tweet", "1")
                .setOperationThreaded(false)  //`true` 是在不同的线程里执行此次操作
                .get();*/

        if (response.isExists()) {
            System.out.println("GetApi 有此文档：" + response.toString());
        } else {
            System.out.println("GetApi 没有此文档：" + response.toString());
        }
    }


    @Test
    public void testForPrepareMultiGet()  throws Exception{
        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                .add("twitter", "tweet", "1") //一个id的方式
                .add("twitter", "tweet", "10", "11", "4") //多个id的方式
                .add("another", "type", "foo")  //可以从另外一个索引获取
                .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) { //迭代返回值
            GetResponse response = itemResponse.getResponse();
            if (response != null && response.isExists()) {      //判断是否存在
                String json = response.getSourceAsString(); //_source 字段
                System.out.println("_source 字段:" + json);
            }
        }
    }

    @Test
    public void testFindAll() throws Exception{
        MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        SearchResponse searchResponse = client.prepareSearch("twitter").setTypes("tweet").setQuery(queryBuilder).get();
        Utils.println(searchResponse);
    }



}
