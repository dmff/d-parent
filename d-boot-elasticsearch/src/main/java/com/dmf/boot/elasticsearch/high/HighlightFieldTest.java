package com.dmf.boot.elasticsearch.high;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;

import java.util.Map;

/**
 * 高亮测试
 * @author dmf
 * @date 2018/7/26
 */
public class HighlightFieldTest extends ElasticsearchClientBase {

    @Test
    public void highTest() {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("address");
        SearchResponse searchResponse = client.prepareSearch("bank").setTypes("account")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery("address", "Avenue")).highlighter(highlightBuilder)
                .setFrom(0).setSize(5).get();

      /*  System.out.println(searchResponse.getHits().totalHits);
        Arrays.stream(
                searchResponse.getHits().getHits())
                .forEach(hit -> System.out.println(JSON.toJSONString(hit.getSource(), SerializerFeature.PrettyFormat)));*/

        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, HighlightField> fields = hit.getHighlightFields();
            System.out.println(fields.toString());
        }
    }

}
