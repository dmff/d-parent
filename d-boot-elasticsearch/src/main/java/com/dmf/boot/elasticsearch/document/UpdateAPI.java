package com.dmf.boot.elasticsearch.document;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.elasticsearch.action.update.UpdateRequest;
import org.junit.Test;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author dmf
 * @date 2018/7/24
 */
public class UpdateAPI extends ElasticsearchClientBase {

    /**
     * 使用 UpdateRequest 操作
     */
    @Test
    public void testForUpdateRequest() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("twitter");
        updateRequest.type("tweet");
        updateRequest.id("10");
        updateRequest.doc(jsonBuilder()
                .startObject()
                .field("user", "http://quanke.name")
                .endObject());
        client.update(updateRequest).get();
    }

    /**
     * 使用prepareUpdate
     */
    @Test
    public void testForPrepareUpdate() throws Exception {

        //存在问题
        /*client.prepareUpdate("twitter", "tweet", "10")
                .setScript(new Script(ScriptType.INLINE, "ctx._source.user = \"quanke.name1\"", null, null))
                .get();*/

        /*client.prepareUpdate("twitter", "tweet", "10")
                .setDoc(jsonBuilder()
                        .startObject()
                        .field("user", "quanke.name")
                        .endObject())
                .get();*/

    }
}
