package com.wolf.pd.run.use;

import com.wolf.pd.run.other.AGroovyClass;

/**
 * Description: 使用groovy中的闭包，有参
 * Created on 2021/5/2 9:44 PM
 *
 * @author 李超
 * @version 0.0.1
 */
public class UseAGroovyClass2 {
    public static void main(String[] args) {
        AGroovyClass instance = new AGroovyClass();
        System.out.println("Received: " + instance.passToClosure(2, new Object() {
            public String call(int value) {
                return "You called from Groovy with value " + value;
            }
        }));
    }
}
