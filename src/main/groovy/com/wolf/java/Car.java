package com.wolf.java;

/**
 * Description:
 * Created on 2021/5/21 10:34 PM
 *
 * @author 李超
 * @version 0.0.1
 */
public class Car {
    private int miles;

    public int getMiles() {
        return miles;
    }

    public void drive(int dist) {
        miles += dist;
    }

    public void divide(int dist) {
        miles = miles / dist;
    }
}
