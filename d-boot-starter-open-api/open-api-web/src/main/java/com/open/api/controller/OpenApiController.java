package com.open.api.controller;

import com.alibaba.fastjson.JSON;
import com.open.api.bo.Test1BO;
import com.open.api.bo.Test2BO;
import com.open.api.model.CommonReq;
import com.open.api.model.ResultModel;
import com.open.api.support.ApiClient;
import com.open.api.util.SystemClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统一网关
 */
@RestController
@RequestMapping("/open")
public class OpenApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiController.class);

    @Autowired
    private ApiClient apiClient;


    /**
     * 统一网关入口
     */
    @PostMapping("/gateway")
    public ResultModel gateway(@RequestBody CommonReq commonReq) throws Throwable {
        LOGGER.info("【{}】>> 网关执行开始 >>  params = {}", JSON.toJSONString(commonReq));
        long start = SystemClock.millisClock().now();
        //todo 加解密延签，可以使用一个拦截器
//        apiClient.checkSign(params, apiRequestId, charset, signType);
        ResultModel result = apiClient.invoke(commonReq.getApp_id(), commonReq.getContent());
        LOGGER.info("【{}】>> 网关执行结束 >> method={},result = {}, times = {} ms", JSON.toJSONString(result), (SystemClock.millisClock().now() - start));
        return result;
    }


    @PostMapping("/test")
    public void test(@RequestBody Test1BO test1BO){

    }

    @PostMapping("/test2")
    public void test2(@RequestBody Test2BO test1BO){

    }

}
