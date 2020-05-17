package com.dmf.boot.elasticsearch.query;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

/**
 * @author dmf
 * @date 2018/7/24
 */
public class CompoundQueries extends ElasticsearchClientBase {


    @Test
    public void testConstantScoreQuery() throws Exception{
        ConstantScoreQueryBuilder queryBuilder = QueryBuilders.constantScoreQuery(
                QueryBuilders.termQuery("firstname", "Rachelle")).boost(2.0f);
        twitterPrepareSearch(queryBuilder);
    }
}
