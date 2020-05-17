package com.dmf.boot.elasticsearch.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

/**
 * Created by http://quanke.name on 2017/11/10.
 */
public class Utils {
    public static void println(SearchResponse searchResponse) {
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println(JSON.toJSONString(searchHit.getSource(), SerializerFeature.PrettyFormat));
        }
    }

}
