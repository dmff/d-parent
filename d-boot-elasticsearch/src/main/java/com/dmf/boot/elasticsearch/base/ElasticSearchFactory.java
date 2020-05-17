package com.dmf.boot.elasticsearch.base;

import com.dmf.boot.elasticsearch.util.Utils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author dmf
 * @date 2018/7/24
 */
public class ElasticSearchFactory {

    private static TransportClient client;

    public static TransportClient getClient() {
        if (client != null) {
            return client;
        }

        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", true)  //自动探测集群的节点，如果指定集群名，不需要添加节点，没指定需要添加一个节点就可以
                .build();
        try {
            client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.5.105"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return client;
    }

    public static void main(String[] args) {
        //System.out.println(getClient());
        for(int i=0;i<100;i++){
            index();
            search();
        }
    }

    public static void search() {
        TransportClient client = getClient();
        SearchResponse response = client.prepareSearch("users")
                    /*		.setTypes("user")
                            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					        .setQuery(QueryBuilders.termQuery("multi", "test"))
					        .setPostFilter(QueryBuilders.rangeQuery("age").from(0).to(18))  */
                .setFrom(0).setSize(60).setExplain(true)
                .get();
        //System.out.println(response);
        Utils.println(response);
    }

    public static void index() {
        TransportClient client = ElasticSearchFactory.getClient();
        Map<String, Object> map = new HashMap();
        Random ran = new Random();
        map.put("nickname", "测试" + ran.nextInt(100));
        map.put("sex", ran.nextInt(100));
        map.put("age", ran.nextInt(100));
        map.put("mobile", "15014243232");
        IndexResponse response = client.prepareIndex("users", "user").setSource(map).get();
        System.out.println(response);
    }

}
