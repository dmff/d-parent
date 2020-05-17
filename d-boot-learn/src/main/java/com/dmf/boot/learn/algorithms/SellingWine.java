package com.dmf.boot.learn.algorithms;

import java.util.Arrays;
import java.util.OptionalInt;

/**
 * @author dmf
 * @date 2018/9/30
 * <p>
 * 卖酒问题：现有六桶酒，容量分别为30升、32升、36升、38升、40升、62升。白酒五桶，红酒一桶。
 * 他的酒都是整桶出售，上午卖出了两桶白酒，下午卖出的白酒是上午的两倍。请问：红酒是哪一桶？
 */
public class SellingWine {

    public static int redWine(int[] arr) {
        int sum = Arrays.stream(arr).sum();
        OptionalInt option = Arrays.stream(arr).filter(value -> (sum - value) % 3 == 0).findFirst();
        if (option.isPresent()) {
            return option.getAsInt();
        }
        throw new RuntimeException("这样的红酒不存在。");
    }


    public static void main(String[] args) {
        int[] arr = {30, 32, 36, 38, 40, 62};
        int redWine = redWine(arr);
        System.out.println(redWine);
    }
}
