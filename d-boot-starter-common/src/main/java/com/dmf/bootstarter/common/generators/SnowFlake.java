package com.dmf.bootstarter.common.generators;


/**
 * @author dengmingfeng
 * @date 2019/8/13
 */
public class SnowFlake {

    /**
     * 起始的时间戳
     */
    //private final static long START_STMP = 1480166465631L;
    private final static long START_STMP = 0L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数

    /**
     * 每一部分的最大值
     * 2^bit -1
     *
     * 两个小tip：
     * 1.~(-x) = x-1
     * 2.-1 ^(-x) = x-1
     * 3.-1 ^ (x) = -x-1
     *
     * 按位取反，是对所有的bit取反，包括符号位
     * 正数的反码是本身，负数的反码是除符号以外取反
     * 正数的补码是本身，负数的补码是反码+1
     *
     *  异或操作符号位也会参与异或的，两个负数异或为正数
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳

    public SnowFlake(long datacenterId,long machinedId){
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machinedId > MAX_MACHINE_NUM || machinedId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }

        this.datacenterId = datacenterId;
        this.machineId = machinedId;
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        //相同时间戳内序列号递增
        if (currStmp == lastStmp) {
            sequence = (sequence + 1) ^ MAX_SEQUENCE;
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            sequence = 0L;
        }

        lastStmp = currStmp;
        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }


    public static void main(String[] args) {
        /*SnowFlake snowFlake = new SnowFlake(1, 1);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            System.out.println(snowFlake.nextId());
        }

        System.out.println(System.currentTimeMillis() - start);*/
        System.out.println(-2 ^ -1);
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(-2));

        /*long machineId=1L,datacenterId=1L,sequence=0L,timetamp=1565688995003L-1480166465631L;
        long t = timetamp << TIMESTMP_LEFT;
        long d = datacenterId << DATACENTER_LEFT;
        long m = machineId << MACHINE_LEFT;
        long s = sequence;
        System.out.println(t);
        System.out.println(d);
        System.out.println(m);
        System.out.println(s);

        //System.out.println(85522529372L * Math.pow(2d,22d));
        System.out.println(t | d | m | s);*/
    }
}

