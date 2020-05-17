package com.open.api.filter;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

/**
 * 重写HttpServletRequestWrapper方法
 * @author Oliver
 * @version 20161214
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private byte[] requestBody = null;

    private final static Logger logger = LoggerFactory.getLogger(BodyReaderHttpServletRequestWrapper.class);

    private ReadListener readListener;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) {

        super(request);

        //缓存请求body
        try {
            requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    public void setRequestBody(byte[] body) {
        this.requestBody = body;
    }

    public String getContent() throws IOException {
        String content = null;
        if(requestBody != null){
            String charEncoding = getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = "UTF-8";
            }
            content = new String(requestBody, charEncoding);
        }
        return content;
    }
    /**
     * 重写 getInputStream()
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if(requestBody == null){
            requestBody= new byte[0];
        }
        boolean beReady = false;
        boolean beFinished = false;
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return beFinished;
            }

            @Override
            public boolean isReady() {
                return beReady;
            }

            @Override
            public void setReadListener(ReadListener read) {
                readListener = read;
            }
        };
    }

    /**
     * 重写 getReader()
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public String getHeader(String name) {
        return super.getHeader(name);
    }
}
