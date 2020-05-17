package com.dmf.bootstarter.logger;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dmf
 * @descriptionlogback全局日志交易id拦截器配置 <br/>
 * 主要针对例如 定时任务 MQ等 非HTTP发起的请求没有traceId 配置需要拦截过滤的地址使用 <br/>
 * 配置demo ：<br/>
 * log.traceId.pointcutExpression=execution(* com.test.service.rabbitmq..*.*(..)) || execution(* com.test.job..*.*(..))
 */
@Configuration
@ConditionalOnProperty(name = "log.traceId.pointcutExpression")
public class MdcTraceIdConfiguration {

    @Value("${log.traceId.pointcutExpression}")
    private String POINTCUT_EXPRESSION;

    @Bean("MdcTraceIdMethodInterceptor")
    public MdcTraceIdMethodInterceptor mdcTraceIdMethodInterceptor() {
        return new MdcTraceIdMethodInterceptor();
    }

    @Bean("MdcTraceIdAdvisor")
    public Advisor MdcTraceIdAdvisor(MdcTraceIdMethodInterceptor mdcTraceIdMethodInterceptor) {
        AspectJExpressionPointcut cut = new AspectJExpressionPointcut();
        cut.setExpression(POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(cut, mdcTraceIdMethodInterceptor);
    }

}
