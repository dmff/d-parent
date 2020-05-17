package com.dmf.bootstarter.common.generators;

import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;

/**
 * @author dmf
 * @date 2019/12/2
 */
@Getter
@Setter
public class SnowflakeGenerator implements KeyGenerator<Long> {

    private SnowFlake snowFlake;

    @Override
    public Long generatorKey() {
        Assert.assertNotNull(this.snowFlake);
        return this.snowFlake.nextId();
    }

    public String generatorStrKey() {
        Assert.assertNotNull(this.snowFlake);
        return String.valueOf(this.snowFlake.nextId());
    }

    public String generatorPreStrKey(String prefix) {
        Assert.assertNotNull(prefix);
        Assert.assertNotNull(this.snowFlake);
        return prefix + String.valueOf(this.snowFlake.nextId());
    }
}
