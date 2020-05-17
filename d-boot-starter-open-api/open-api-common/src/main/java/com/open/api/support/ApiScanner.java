package com.open.api.support;


import com.open.api.annotation.OpenApi;
import com.open.api.annotation.OpenApiService;
import com.open.api.util.ApplicationContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Api接口扫描器
 */
@Component
public class ApiScanner implements CommandLineRunner {


    private static final Logger LOGGER = LoggerFactory.getLogger(ApiScanner.class);

    /**
     * 统计扫描次数
     */
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Resource
    private ApiContainer apiContainer;

    @Override
    public void run(String... var1) throws Exception {
        //扫描所有使用@OpenApiService注解的类
        Map<String, Object> openApiServiceBeanMap = ApplicationContextHelper.getBeansWithAnnotation(OpenApiService.class);

        if (null == openApiServiceBeanMap || openApiServiceBeanMap.isEmpty()) {
            LOGGER.info("open api service bean map is empty");
            return;
        }

        for (Map.Entry<String, Object> map : openApiServiceBeanMap.entrySet()) {
            //获取扫描类下所有方法
            Method[] methods = ReflectionUtils.getAllDeclaredMethods(map.getValue().getClass());
            for (Method method : methods) {
                atomicInteger.incrementAndGet();
                //找到带有OpenApi 注解的方法
                OpenApi openApi = AnnotationUtils.findAnnotation(method, OpenApi.class);
                if (null == openApi) {
                    continue;
                }


                Class<?>[] parameterTypes = method.getParameterTypes();
                Class<?> clazz = parameterTypes != null ? parameterTypes[0] : Void.class;


                //把 api 和method和bean关联上
                apiContainer.put(openApi.method(), new ApiModel(map.getKey(), method,clazz));
                LOGGER.info("Api接口加载成功 >> method = {} , desc={}", openApi.method(), openApi.desc());
            }
        }
        LOGGER.info("Api接口容器加载完毕 >> size = {} loopTimes={}", apiContainer.size(), atomicInteger.get());
    }

}
