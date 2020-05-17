package com.open.api.support;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.api.enums.ApiExceptionEnum;
import com.open.api.exception.BusinessException;
import com.open.api.model.ResultModel;
import com.open.api.util.ApplicationContextHelper;
import com.open.api.util.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Api请求客户端
 *
 * @author 码农猿
 */
@Service
public class ApiClient {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiClient.class);

    /**
     * jackson 序列化工具类
     */
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /**
     * Api本地容器
     */
    private final ApiContainer apiContainer;

    public ApiClient(ApiContainer apiContainer) {
        this.apiContainer = apiContainer;
    }


    /**
     * Api调用方法
     */
    public ResultModel invoke(String apiId, String content) throws Throwable {
        ApiModel apiModel = apiContainer.get(apiId);
        if (null == apiModel) {
            LOGGER.info(" API方法不存在 >> apiId = {}", apiId);
            throw new BusinessException(ApiExceptionEnum.API_NOT_EXIST);
        }

        //获得spring bean
        Object bean = ApplicationContextHelper.getBean(apiModel.getBeanName());
        if (null == bean) {
            LOGGER.warn("【{}】 >> API方法不存在 >>  beanName = {}", apiId, apiModel.getBeanName());
            throw new BusinessException(ApiExceptionEnum.API_NOT_EXIST);
        }

        //处理业务参数
        Object result = JSONObject.parseObject(content, apiModel.getParam());
        ValidateUtils.validate(result);
        try {
            Object obj = apiModel.getMethod().invoke(bean, result);
            return ResultModel.success(obj);
        } catch (Exception e) {
            throw new BusinessException(ApiExceptionEnum.SYSTEM_ERROR);
        }

    }


}
