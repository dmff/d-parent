package com.open.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.open.api.bo.Test1BO;
import com.open.api.util.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dmf
 * @date 2020/3/11
 */
@Slf4j
@RestController
public class TestController {


    @PostMapping(value = "/api/test")
    public String test(@RequestBody Test1BO test1BO){
        ValidateUtils.validate(test1BO);
        log.info("成功处理请求，param={}", JSONObject.toJSONString(test1BO));
        return "SUCCESS";
    }


}
