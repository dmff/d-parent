package com.open.api.config;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author dmf
 * @date 2020/3/11
 */
@Component
public class ReqUrlConfig {



    private Map<String,String> urlMaps;

    @PostConstruct
    public void init(){
        urlMaps = Maps.newHashMap();
        urlMaps.put("api.test","/api/test");
    }

    public String findURL(String req){
        return urlMaps.get(req);
    }


}
