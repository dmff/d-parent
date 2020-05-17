package com.dmf.boot.learn.problem.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author dmf
 * @date 2020/3/8
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
