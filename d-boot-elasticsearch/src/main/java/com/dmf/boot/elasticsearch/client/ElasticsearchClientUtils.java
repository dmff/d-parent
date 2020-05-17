package com.dmf.boot.elasticsearch.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author dmf
 * @date 2018/9/26
 *
 * es客户端工具类
 */
public class ElasticsearchClientUtils {

    protected static volatile TransportClient client;

    private static TransportClient initTransportClient(){
        Settings esSettings = Settings.builder()
                .put("cluster.name", "elasticsearch")
                //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                .put("client.transport.sniff", true)
                .build();

        try {
            /**
             * 1. java客户端的方式是以tcp协议在9300端口上进行通信
             * 2. http客户端的方式是以http协议在9200端口上进行通信
             */
           return new PreBuiltTransportClient(esSettings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.5.193"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("获取es客户端失败...");
        }

        return null;
    }


    /**
     * 保证es客户端单例
     * @return
     * @throws UnknownHostException
     */
    public static TransportClient getClient() throws UnknownHostException {
        if (client == null){
            synchronized (ElasticsearchClientUtils.class){
                if (client == null){
                    client = initTransportClient();
                }
            }
        }
        return client;
    }
}
