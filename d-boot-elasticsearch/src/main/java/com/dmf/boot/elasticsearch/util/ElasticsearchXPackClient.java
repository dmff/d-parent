package com.dmf.boot.elasticsearch.util;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.net.InetAddress;

public class ElasticsearchXPackClient {

    protected TransportClient client;

    @Before
    public void setUp() throws Exception {
        Settings esSettings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", true)
                .build();

        client = new PreBuiltXPackTransportClient(esSettings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.5.105"), 9300));

        //CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("aaa","bbb"));
        System.out.println("ElasticsearchClient 连接成功");
    }

    @After
    public void tearDown() throws Exception {
        if (client != null) {
            client.close();
        }
    }

    @Test
    public void test1() throws Exception {
        System.out.println(client);
    }

}
