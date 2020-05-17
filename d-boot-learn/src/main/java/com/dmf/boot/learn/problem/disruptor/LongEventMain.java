package com.dmf.boot.learn.problem.disruptor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.*;

/**
 * @author dmf
 * @date 2020/3/8
 */
public class LongEventMain {

    public static void main(String[] args) throws Exception{
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        ThreadFactory consumerThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("consumer-%d")
                .setDaemon(true)
                .build();
        ThreadPoolExecutor comsumerExecutor = new ThreadPoolExecutor(15, 15,
                1, TimeUnit.MILLISECONDS,
                queue,consumerThreadFactory);

        ThreadFactory productThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("product-%d")
                .setDaemon(true)
                .build();
        ThreadPoolExecutor productExecutor = new ThreadPoolExecutor(2, 2,
                1, TimeUnit.MILLISECONDS,
                queue,productThreadFactory);

        // The factory for the event
        LongEventFactory factory = new LongEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 8;

        for (int i = 0; i < 1; i++) {
            // Construct the Disruptor
            //Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);
            Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, comsumerExecutor, ProducerType.SINGLE,
                    new BlockingWaitStrategy());

            //BlockingWaitStrategy
            // Connect the handler
            disruptor.handleEventsWith(new LongEventHandler());

            // Start the Disruptor, starts all threads running
            disruptor.start();

            // Get the ring buffer from the Disruptor to be used for publishing.
            RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

            LongEventProducer producer = new LongEventProducer(ringBuffer);

            for (long l = 0; l < 1000000; l++) {
                //producer.onData(l);
                //Thread.sleep(1000);
                productExecutor.execute(new Work(producer,l));
            }

        }



        productExecutor.shutdown();
        while (!productExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println("线程还在执行。。。");
        }
        System.out.println("main over");
    }

    private static class Work implements Runnable{

        private LongEventProducer producer ;
        private long bb ;

        public Work(LongEventProducer producer,long bb) {
            this.producer = producer;
            this.bb = bb ;
        }

        @Override
        public void run() {
            producer.sendData(bb);
        }
    }
}
