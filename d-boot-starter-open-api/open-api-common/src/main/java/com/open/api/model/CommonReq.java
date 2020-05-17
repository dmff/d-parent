package com.open.api.model;

import lombok.Data;

/**
 * @author dmf
 * @date 2020/3/11
 */
@Data
public class CommonReq {

    private String app_id;
    private String version;
    private String api_request_id;
    private String charset;
    private String sign_type;
    private String sign;
    private String content;

}
