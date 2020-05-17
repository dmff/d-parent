package com.dmf.boot.elasticsearch.document;

import com.alibaba.fastjson.JSONArray;
import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import java.io.IOException;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author dmf
 * @date 2018/7/24
 */
public class InsertAPI extends ElasticsearchClientBase {

    /**
     * 使用json字符串来构造文档内容
     */
    @Test
    public void testForUseStr() throws Exception {
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2019-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        List<String> list = Arrays.asList("a","b","c");
        String jsonString = JSONArray.toJSONString(list);
        System.out.println(jsonString);
        /*client.prepareIndex("twitter", "tweet", "1")
                .setSource(jsonString, XContentType.JSON)
                .get();*/
        System.out.println("testForUseStr twitter 创建成功");
    }

    /**
     * 使用map来构造文档内容
     *
     * @throws Exception
     */
    @Test
    public void testForUseMap() throws Exception {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user", "kimchy");
        json.put("postDate", "2017-10-10");
        json.put("message", "trying out Elasticsearch");
        IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
                .setSource(json)
                .get();

        System.out.println(response.getResult());
        System.out.println("testForUseMap twitter 创建成功");
    }

    /**
     * 使用elasticsearch官方提供的json构造器来构造文档内容
     *
     * @throws Exception
     */
    @Test
    public void testForUseXContentBuilder() throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("user", "kimchy")
                .field("postDate", new Date())
                .field("age", 10)
                .field("gender", "male")
                .field("message", "trying out Elasticsearch")
                .endObject();
        client.prepareIndex("twitter", "tweet", "1")
                .setSource(builder)
                .get();
        System.out.println("testForUseXContentBuilder twitter 创建成功");
    }


    /**
     * 批量插入
     *
     * @throws IOException
     */
    @Test
    public void testPrepareBulk() throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.add(client.prepareIndex("twitter", "tweet", "10")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
        );

        bulkRequest.add(client.prepareIndex("twitter", "tweet", "11")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post")
                        .endObject()
                )
        );

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            System.out.println("失败：" + bulkResponse.toString());
        }
    }
}
