package com.dmf.bootstarter.switcher;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author dengmingfeng
 * @date 2020/3/24
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = SwitcherContainer.SWITCHER_PREFIX)
public class SwitcherContainer {

    private Map<String, SwitcherProperties> configMaps;

    public static final String SWITCHER_PREFIX = "switcher";
    public static final String SWITCHER_SPILT = ".";

    private static final String FLAG_PROPERTIES_SUFFER = "enable";
    private static final String START_PROPERTIES_SUFFER = "startTime";
    private static final String END_PROPERTIES_SUFFER = "endTime";


    @Value(value = "#{'${switcher.namespace:application}'.split(',')}")
    private List<String> switcherNameSpace;

//    @ApolloConfigChangeListener(value = NAME_SPACE)
    @PostConstruct
    private void changeHandler() {
        switcherNameSpace.forEach(nameSpace->{
            Config config = ConfigService.getConfig(nameSpace);
            config.addChangeListener(changeEvent->{
                for (String changedKey : changeEvent.changedKeys()) {
                    if (changedKey.startsWith(SWITCHER_PREFIX)) {
                        ConfigChange change = changeEvent.getChange(changedKey);
                        //获取id和配置
                        String[] keys = changedKey.split("\\" + SWITCHER_SPILT);
                        if (keys.length >= 3) {
                            String properties = keys[keys.length - 1];
                            String switcherId = keys[keys.length - 2];
                            SwitcherProperties switcherProperties = configMaps.get(switcherId);
                            if (switcherProperties == null) {
                                switcherProperties = new SwitcherProperties();
                                configMaps.put(switcherId, switcherProperties);
                            }

                            setSwitcherProperties(switcherProperties, properties, change.getNewValue(), change.getOldValue(), switcherId);
                            if (configMaps.get(switcherId).equals(new SwitcherProperties())) {
                                configMaps.remove(switcherId);
                            }
                        }
                    }
                }
            });
        });

    }

    private void setSwitcherProperties(SwitcherProperties switcherProperties, String properties, String newValue, String oldValue, String switcherId) {
        log.info("{}开关配置开始调整{}，旧值：{}，新值：{}", switcherId, properties, oldValue, newValue);
        if (properties.contains(FLAG_PROPERTIES_SUFFER)) {
            switcherProperties.setEnable(Boolean.valueOf(newValue));
        }

        if (properties.contains(START_PROPERTIES_SUFFER)) {
            switcherProperties.setStartTime(newValue);
        }

        if (properties.contains(END_PROPERTIES_SUFFER)) {
            switcherProperties.setEndTime(newValue);
        }

        log.info("{}开关配置结束调整{}，旧值：{}，新值：{}", switcherId, properties, oldValue, newValue);

    }
}
