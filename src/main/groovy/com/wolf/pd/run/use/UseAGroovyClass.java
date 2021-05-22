package com.wolf.pd.run.use;

import com.wolf.pd.run.other.AGroovyClass;

/**
 * Description: 使用groovy中的闭包，无参
 * Created on 2021/5/2 9:37 PM
 *
 * @author 李超
 * @version 0.0.1
 */
public class UseAGroovyClass {
    public static void main(String[] args) {
        AGroovyClass instance = new AGroovyClass();
        Object result = instance.useClosure(new Object() {
            public String call() {
                return "You called from Groovy!";
            }
        });
        System.out.println("Received: " + result);
    }
}
