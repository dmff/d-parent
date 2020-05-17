package com.dmf.boot.learn.problem.disruptor;

import com.lmax.disruptor.RingBuffer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dmf
 * @date 2020/3/8
 */
@Slf4j
@AllArgsConstructor
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;

    public void sendData(long value){
       ringBuffer.publishEvent((e, s, bb)->e.setValue(value),value);
    }

}
