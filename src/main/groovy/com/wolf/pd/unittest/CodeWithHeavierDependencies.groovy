package com.wolf.pd.unittest

// 被测试对象，很重
public class CodeWithHeavierDependencies {
    public void myMethod() {
        def value = someAction() + 10
        println value
    }

    int someAction() {// 被依赖，很重
        Thread.sleep(5000)
        return Math.random() * 100
    }
}