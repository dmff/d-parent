package com.open.api.filter;

import com.alibaba.fastjson.JSON;
import com.open.api.enums.ApiExceptionEnum;
import com.open.api.model.CommonReq;
import com.open.api.model.ResultModel;
import com.open.api.util.AppContextStoreUtil;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpServletRequestReplacedFilter implements Filter {


    private static final String UTF8_ENCODE = "UTF-8";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
        BodyReaderHttpServletResponseWrapper responseWrapper = new BodyReaderHttpServletResponseWrapper((HttpServletResponse) response);

        //获取body
        String content = requestWrapper.getContent();
        CommonReq commonReq = JSON.parseObject(content, CommonReq.class);
        if (commonReq == null || commonReq.getContent() == null) {
            String json = buildResEnumToJson(ApiExceptionEnum.SYSTEM_ERROR);
            this.writeResponse(response, json);
            return;
        }

        requestWrapper.setRequestBody(commonReq.getContent().getBytes(StandardCharsets.UTF_8));
        AppContextStoreUtil.set("ReqServiceId",commonReq.getApp_id());
        //todo 在请求前可以完成解密验签处理
        chain.doFilter(requestWrapper, responseWrapper);
        //todo 在响应前可以完成加密加签处理
        String responseContent = responseWrapper.getContent();
        AppContextStoreUtil.removeKey("ReqServiceId");
        this.writeResponse(response, responseContent);


    }

    private String buildResEnumToJson(ApiExceptionEnum respCodeEnum) {
        ResultModel commonResp = new ResultModel(respCodeEnum.getCode(), respCodeEnum.getMsg());
        return JSON.toJSONString(commonResp);
    }


    private void writeResponse(ServletResponse response, String json) throws IOException {
        response.setCharacterEncoding(UTF8_ENCODE);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().print(json);
        response.flushBuffer();
    }

}
