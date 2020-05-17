package com.dmf.boot.elasticsearch.test;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import java.io.IOException;

/**
 * @author dmf
 * @date 2018/8/9
 *
 * es客户端版本：5.3.0
 * 父子文档测试：通过父子关系可以轻松的建立一对多之间的关系，修改自己的文档不会影响父文档或者子文档
 */
public class ParentChildMappingTest extends ElasticsearchClientBase {

    //ScoreMode:表示将多个孩子命中的分数转化为父分数

    /**
     * hasChild：查询在公司员工中dob大于1980-01-01的公司
     */
    @Test
    public void hasChildTest() {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        QueryBuilder childQB = QueryBuilders.rangeQuery("dob").gte("1980-01-01");
        queryBuilder.must(QueryBuilders.hasChildQuery("employee",childQB, ScoreMode.Total));
        branchPrepareSearch(queryBuilder);
    }

    /**
     * hasChild：查询在公司员工中name包含Alice Smith的公司
     */
    @Test
    public void hasChildTest2() {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        QueryBuilder childQB = QueryBuilders.matchQuery("name","Alice Smith");
        queryBuilder.must(QueryBuilders.hasChildQuery("employee",childQB, ScoreMode.Max));
        branchPrepareSearch(queryBuilder);
    }

    /**
     * hasChild：查询在最少有两个员工的公司
     */
    @Test
    public void hasChildTest3() {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.hasChildQuery("employee",QueryBuilders.matchAllQuery(), ScoreMode.Max).minMaxChildren(2,Integer.MAX_VALUE));
        branchPrepareSearch(queryBuilder);
    }


    /**
     * hasParent：查询在英国工作的员工
     * score：父类集中的分数是否传递给child
     */
    @Test
    public void hasParentTest() {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        MatchQueryBuilder parentQB = QueryBuilders.matchQuery("country", "UK");
        queryBuilder.must(QueryBuilders.hasParentQuery("branch",parentQB,true));
        employeePrepareSearch(queryBuilder);
    }

    /**
     * 添加子节点测试
     */
    @Test
    public void addChildTest() throws IOException {
        //{ "name": "Adrien Grand", "dob": "1987-05-11", "hobby": "horses" }
        XContentBuilder source = XContentFactory.jsonBuilder()
                .startObject()
                .field("name", "dmf")
                .field("dob", "1905-01-01")
                .field("hobby", "play")
                .endObject();

        IndexRequest indexRequest = Requests.indexRequest("company")
                .type("employee")
                .source(source)
                .id("5")
                .parent("london");

        IndexResponse indexResponse = client.index(indexRequest).actionGet();
        System.out.println(indexResponse.getResult().toString());
    }


}
