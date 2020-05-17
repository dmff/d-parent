package com.dmf.boot.learn.problem.disruptor;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dmf
 * @date 2020/3/8
 */
@Slf4j
public class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        log.info("消费 Event=[{}]",longEvent.getValue()) ;
    }
}
