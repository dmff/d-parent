package com.dmf.bootstarter.common.config.cors;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**跨域的自动配置
 * @author dmf
 * @date 2019/7/15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(value = {CrossOriginConfiguration.class})
public @interface EnableCROS {
}
