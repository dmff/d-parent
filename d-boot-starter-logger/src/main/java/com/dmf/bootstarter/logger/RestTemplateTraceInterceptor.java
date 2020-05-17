package com.dmf.bootstarter.logger;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @author dengmingfeng
 * @date 2020/4/20
 */
public class RestTemplateTraceInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        //ip 和 port
        HttpHeaders headers = httpRequest.getHeaders();
        headers.add("X-TRACE-ID", MDC.get(AbstractMyThreadContext.MDC_TRACE_ID));
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
