package com.dmf.bootstarter.logger;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author dengmingfeng
 * @date 2020/4/20
 */
public class MdcFilter extends MDCInsertingServletFilter {

    private static final String HEARDER_FOR_TRACE_ID = "X-TRACE-ID";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            return;
        }
        /**如前端传递了唯一标识ID 拿出来直接用 根据业务这段也可以删除 然后去掉if判断*/
        String traceId = ((HttpServletRequest) request).getHeader(HEARDER_FOR_TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = AbstractUUIDShort.generate();
        }
        AbstractMyThreadContext.setTraceId(traceId);
        MDC.put(AbstractMyThreadContext.MDC_TRACE_ID,traceId);
        try {
            //从新调动父类的doFilter方法
            super.doFilter(request, response, chain);
        } finally {
            //资源回收
            MDC.remove(AbstractMyThreadContext.MDC_TRACE_ID);
            AbstractMyThreadContext.removeTraceId();
        }
    }

}
