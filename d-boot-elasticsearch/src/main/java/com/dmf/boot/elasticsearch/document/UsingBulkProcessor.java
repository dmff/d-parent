package com.dmf.boot.elasticsearch.document;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 给定的大小数量上定时批量自动请求
 *
 * @author dmf
 * @date 2018/7/24
 */
public class UsingBulkProcessor extends ElasticsearchClientBase {

    BulkProcessor bulkProcessor;

    @Test
    public void testBulkProcessor() throws Exception {
        bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {

            @Override
            public void beforeBulk(long l, BulkRequest bulkRequest) {
                //调用bulk之前执行 ，例如你可以通过request.numberOfActions()方法知道numberOfActions
                System.out.println("executionId = [" + l + "], request = [" + bulkRequest + "]");
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                //调用bulk之后执行 ，例如你可以通过request.hasFailures()方法知道是否执行失败
                System.out.println("executionId = [" + l + "], request = [" + bulkRequest + "], response = [" + bulkResponse + "]");
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                //调用失败抛 Throwable
                System.out.println("executionId = [" + l + "], request = [" + bulkRequest + "], failure = [" + throwable + "]");
            }
        }).setBulkActions(1000)
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))  //拆成5m一块
                .setFlushInterval(TimeValue.timeValueSeconds(5))  //无论请求数量多少，每5秒钟请求一次
                .setConcurrentRequests(1)  //设置并发请求的数量。值为0意味着只允许执行一个请求。值为1意味着允许1并发请求。
                //设置自定义重复请求机制，最开始等待100毫秒，之后成倍更加，重试3次，当一次或多次重复请求失败后因为计算资源不够抛出 EsRejectedExecutionException 异常，
                // 可以通过BackoffPolicy.noBackoff()方法关闭重试机制
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();


    }


    @Override
    public void tearDown() throws Exception {
        bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
        super.tearDown();
    }

}
