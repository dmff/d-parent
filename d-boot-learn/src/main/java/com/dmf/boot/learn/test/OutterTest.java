package com.dmf.boot.learn.test;

/**
 * @author dmf
 * @date 2019/12/29
 */
public class OutterTest {

    private Inner inner = null;

    public Inner getInnerInstance() {
        if(inner == null) {
            inner = new Inner();
        }

        return inner;
    }

    protected class Inner {
        public Inner() {

        }
    }
}
