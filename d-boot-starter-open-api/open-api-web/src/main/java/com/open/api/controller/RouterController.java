package com.open.api.controller;


import com.open.api.config.ReqUrlConfig;
import com.open.api.enums.ApiExceptionEnum;
import com.open.api.exception.BusinessException;
import com.open.api.util.AppContextStoreUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dengmingfeng
 * @date 2019/11/15
 */
@ApiIgnore
@Slf4j
@Controller
public class RouterController {

    @Autowired
    private ReqUrlConfig reqUrlConfig;

    @RequestMapping(value = "/api")
    public String router(HttpServletRequest request, HttpServletResponse response) {
        String reqServiceId = (String) AppContextStoreUtil.get("ReqServiceId");
        String url = reqUrlConfig.findURL(reqServiceId);
        if (StringUtils.isEmpty(url)){
            throw new BusinessException(ApiExceptionEnum.API_NOT_EXIST);
        }

        return "forward:" + url;
    }


}
