package com.dmf.boot.elasticsearch.test;

import com.dmf.boot.elasticsearch.util.Utils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.net.InetAddress;

/**
 * @author dmf
 * @date 2018/8/21
 * 相识文档测试
 */
public class SimilarDocTest {

    private TransportClient client;

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

    @Test
    public void test1(){
        String[] fields = {"gender","city"};
        String[] texts = {"Laverne","302 Howard Place"};
        //还可以使用XContentBuild构建doc文档
        MoreLikeThisQueryBuilder.Item[] items = new MoreLikeThisQueryBuilder.Item[]{new MoreLikeThisQueryBuilder.Item("bank","account","119")};
        MoreLikeThisQueryBuilder moreLikeThisQueryBuilder =
                QueryBuilders.moreLikeThisQuery(fields, null, items).minTermFreq(1).maxQueryTerms(12);
        SearchResponse response = client.prepareSearch("bank").setTypes("account")
                .setQuery(moreLikeThisQueryBuilder).execute().actionGet();
        Utils.println(response);
    }

    @Test
    public void test2(){
        String[] fields = {"gender","city"};
        String[] texts = {"Laverne","302 Howard Place"};
        //MoreLikeThisQueryBuilder.Item[] items = new MoreLikeThisQueryBuilder.Item[]{new MoreLikeThisQueryBuilder.Item("bank","account","119")};
        MoreLikeThisQueryBuilder moreLikeThisQueryBuilder =
                QueryBuilders.moreLikeThisQuery(fields, null, null).minTermFreq(1).maxQueryTerms(12);
        SearchResponse response = client.prepareSearch("bank").setTypes("account")
                .setQuery(moreLikeThisQueryBuilder).execute().actionGet();
        Utils.println(response);
    }

}
