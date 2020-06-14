//package com.dmf.boot.learn.test;
//
//import ch.javasoft.util.intcoll.IntHashMap;
//
//import java.util.HashMap;
//
///**
// * @author dmf
// * @date 2018/8/26
// */
//public class IntHashMapTest {
//
//    static HashMap<Integer, String> hashMap = new HashMap<>();
//    static IntHashMap<String> intHashMap = new IntHashMap<>();
//
//    public static void add() throws InterruptedException {
//        Thread.sleep(5000);
//        for (int i = 0; i < 10000; i++) {
//            for (int j = 0; j < 50000; j++) {
//                hashMap.putIfAbsent(j, "小毛驴");
//            }
//        }
//    }
//
//    public static void add1() throws InterruptedException {
//        Thread.sleep(5000);
//        for (int i = 0; i < 10000; i++) {
//            for (int j = 0; j < 50000; j++) {
//                if (intHashMap.get(j) == null) {
//                    intHashMap.put(j, "小毛驴");
//                }
//            }
//        }
//    }
//
//    public static void main(String[] args) throws Exception{
//        long curTime = System.currentTimeMillis();
//        //add();
//        add1();
//        long curTime2 = System.currentTimeMillis();
//        System.out.println("耗时:"+(curTime2-curTime)+"ms");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
