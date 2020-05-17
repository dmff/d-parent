package com.dmf.boot.elasticsearch.base;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;

import java.net.InetAddress;

/**
 * @author dmf
 * @date 2018/8/29
 */
public class BaseTest {

    protected TransportClient client;

    @Before
    public void setUp() throws Exception {
        Settings esSettings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", true)
                .build();


        client = new PreBuiltTransportClient(esSettings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.5.193"), 9300));

    }

    @After
    public void tearDown() throws Exception {
        if (client != null) {
            client.close();
        }
    }

    protected SearchResponse search(String index, String type, QueryBuilder queryBuilder){
        SearchResponse response = client.prepareSearch(index).setTypes(type)
                .setQuery(queryBuilder).execute().actionGet();
        return response;
    }
}
