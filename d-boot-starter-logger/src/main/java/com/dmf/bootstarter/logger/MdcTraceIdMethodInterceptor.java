package com.dmf.bootstarter.logger;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author dengmingfeng
 * @date 2020/4/20
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MdcTraceIdMethodInterceptor implements MethodInterceptor {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());



    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (MDC.get(AbstractMyThreadContext.MDC_TRACE_ID) != null) {
            return invocation.proceed();
        }

        try {
            String traceId = AbstractUUIDShort.generate();
            AbstractMyThreadContext.setTraceId(traceId);
            MDC.put(AbstractMyThreadContext.MDC_TRACE_ID,traceId);
            return invocation.proceed();
        } catch (Throwable e) {
            log.warn("MdcTraceIdMethodInterceptor error {}", e.getMessage());
            throw e;
        } finally {
            MDC.remove(AbstractMyThreadContext.MDC_TRACE_ID);
            AbstractMyThreadContext.removeTraceId();
        }
    }
}
