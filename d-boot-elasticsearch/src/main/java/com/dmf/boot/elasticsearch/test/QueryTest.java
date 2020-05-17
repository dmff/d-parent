package com.dmf.boot.elasticsearch.test;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author dmf
 * @date 2018/7/24
 */
public class QueryTest {


    public void connect() throws Exception {
        //Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //集群可以添加多个客户端
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.5.105"), 9300));
        System.out.println(client);
        //设置index参数
       // XContentBuilder mapping = getIndexMapping();
        //CreateIndexResponse employee = client.admin().indices().prepareCreate("employee").setSettings(mapping).get();
        //System.out.println(employee.index());
    }

    private XContentBuilder getIndexMapping() throws IOException {
        XContentBuilder mapping = XContentFactory.jsonBuilder();
        mapping.startObject()
                .startObject("employee")
                    .startObject("properties")
                        .startObject("id").field("type", "integer")
                        .startObject("name").field("type","string")
                        .startObject("age").field("type","integer")
                        .startObject("hobby").field("type","string").field("analyzer","english")
                        .startObject("birthday")
                            .field("type","date")
                            .field("format","dateOptionalTime")
                            .field("index","not_analyzed");
        return mapping;
    }

}
