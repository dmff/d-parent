package com.dmf.boot.elasticsearch.test;

import com.dmf.boot.elasticsearch.util.Utils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author dmf
 * @date 2018/8/14
 * es数组查询
 *
 */
public class TermsTest {

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
    public void test0() throws Exception{
        RangeQueryBuilder build = QueryBuilders.rangeQuery("refreshDate").gte(System.currentTimeMillis());
        SearchResponse response = client.prepareSearch("open").setTypes("applicants")
                .setQuery(build).execute().actionGet();
        Utils.println(response);
    }


    @Test
    public void test2() throws Exception{

        TermsQueryBuilder build = QueryBuilders.termsQuery("industryCodes", "1007", "1004");
        //in 查询，可以在数组或者字段中进行多值包含匹配
        //TermsQueryBuilder build = QueryBuilders.termsQuery("companyId", "463822095860629504","465942052111122432");
        SearchResponse response = client.prepareSearch("open").setTypes("positions")
                .setQuery(build).execute().actionGet();
        Utils.println(response);
    }

    @Test
    public void hasChildTest3_1() {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        TermsQueryBuilder build = QueryBuilders.termsQuery("createBy", "15107764638@test5");
        queryBuilder.must(QueryBuilders.hasChildQuery("talent_pool",build,ScoreMode.Total));
        SearchResponse response = client.prepareSearch("open5").setTypes("applicants")
                .setQuery(queryBuilder).execute().actionGet();
        Utils.println(response);
    }

    @Test
    public void addChildTest() throws IOException {
        XContentBuilder source = XContentFactory.jsonBuilder()
                .startObject()
                .field("applicantName", "478596547798368256@OPEN_OC")
                .field("createBy", "15907278602@test12")
                .endObject();

        IndexRequest indexRequest = Requests.indexRequest("open5")
                .type("talent_pool")
                .source(source)
                .id("1")
                .parent("478596547798368256@OPEN_OC");

        IndexResponse indexResponse = client.index(indexRequest).actionGet();
        System.out.println(indexResponse.getResult().toString());
    }

    @Test
    public void addChildTest1() throws IOException {
        XContentBuilder source = XContentFactory.jsonBuilder()
                .startObject()
                .field("applicantName", "479359084349358080@OPEN_OC")
                .field("createBy", "15907278602@test12")
                .endObject();

        IndexRequest indexRequest = Requests.indexRequest("open")
                .type("preview_applicant")
                .source(source)
                .id("3")
                .parent("479359084349358080@OPEN_OC");

        IndexResponse indexResponse = client.index(indexRequest).actionGet();
        System.out.println(indexResponse.getResult().toString());
    }
}
