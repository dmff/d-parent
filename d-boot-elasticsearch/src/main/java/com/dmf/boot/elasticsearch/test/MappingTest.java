package com.dmf.boot.elasticsearch.test;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.template.delete.DeleteIndexTemplateRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import java.io.File;
import java.io.InputStream;


/**
 * 1.获取mapping、
 * 2.通过XContentBuilder手动创建mapping、通过加载json文件创建mapping
 * 3.生成mapping动态模板
 * 4.去除空格及换行：s = s.replaceAll("\\s*|\\t|\\r|\\n", "");
 * @author dmf
 * @date 2018/7/26
 */
public class MappingTest extends ElasticsearchClientBase {

    /**
     * 获取mapping
     */
    @Test
    public void getMapping() {
        ImmutableOpenMap<String, MappingMetaData> mappings = client.admin().cluster().prepareState().execute().actionGet()
                .getState().getMetaData().getIndices().get("open").getMappings();
        //CompressedXContent account = mappings.get("account").source();
        //System.out.println(account.toString());
    }

    /**
     * 手动创建mapping
     */
    @Test
    public void createMapping1() throws Exception{
        //client.admin().indices().prepareCreate("open5").execute().actionGet();
        client.admin().indices().prepareCreate("test").setSettings().execute().actionGet();
        XContentBuilder builder= XContentFactory.jsonBuilder()
                .startObject()
                .startObject("test1")
                .startObject("properties")
                .startObject("filename").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("userid").field("type", "string").field("index", "not_analyzed").endObject()
                .endObject()
                .endObject()
                .endObject();
        String string = builder.string();
        System.out.println(string);
    }

    /**
     * 通过加载json文件创建mapping
     */
    @Test
    public void createMapping2() throws Exception {
        String s = FileUtils.readFileToString(new File("D:\\workspace\\demo\\src\\main\\resources\\beilie-template.json"), "UTF-8");
        PutMappingRequest mapping = Requests
                .putMappingRequest("test")
                .type("applicants")
                .updateAllTypes(true)
                .source(s,XContentType.JSON);
        client.admin().indices().putMapping(mapping).actionGet();
    }


    /**
     * 通过加载json文件创建mapping模板
     */
    @Test
    public void createTemplate() throws Exception{
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("beilie-template.json");
        System.out.println(IOUtils.toString(in,"UTF-8"));
        //创建模板
        /*String s1 = FileUtils.readFileToString(new File("D:\\workspace\\demo\\src\\main\\resources\\beilie-template.json"), "UTF-8");
        PutIndexTemplateRequest putIndexTemplateRequest = new PutIndexTemplateRequest("open5");
        putIndexTemplateRequest.source(s1,XContentType.JSON);
        client.admin().indices().putTemplate(putIndexTemplateRequest).actionGet();*/
    }

    /**
     * 删除mapping模板
     */
    @Test
    public void deleteTemplate() throws Exception{
        client.admin().indices().deleteTemplate(new DeleteIndexTemplateRequest("open"));
    }



}
