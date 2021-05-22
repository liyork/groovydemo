package com.wolf.java;

/**
 * Description:
 * Created on 2021/5/21 11:09 PM
 *
 * @author 李超
 * @version 0.0.1
 */
public class JaveCodeWithHeavierDependencies {
    public int someAction() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (int) (Math.random() * 100);
    }

    public void myMethod() {
        int value = someAction() + 10;
        System.out.println(value);
    }
}
