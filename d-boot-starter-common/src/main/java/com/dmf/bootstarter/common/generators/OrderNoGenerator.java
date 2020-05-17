package com.dmf.bootstarter.common.generators;

/**
 * @author dmf
 * @date 2019/12/2
 */
public class OrderNoGenerator extends EFGenerator {

    @Override
    public String generatorKey() {
        StringBuffer stringBuffer = new StringBuffer();
        return stringBuffer.append("EF")
                .append(this.getTimestamp17())
                .append(generateCharacter(8))
                .append(generateNumbers(3)).toString();

    }
}
