package com.dmf.boot.learn.pattern.chain;

/**
 * @author dmf
 * @date 2020/4/21 21:29
 */
public interface IHandler<R,T> {

    /**
     * chain 上实际方法的抽象
     * @param r 参数
     * @return T 返回
     */
    T handler(R r);
}
