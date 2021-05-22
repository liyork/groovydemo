package com.wolf.pd.unittest

public class ClassWithDependency {
    def methodA(val, file) {// 依赖一个文件，要想办法模拟
        file.write "The value is ${val}"
    }

    def methodB(val) {
        def file = new java.io.FileWriter("output.txt")
        file.write "The value is ${val}"
    }

    def methodC(val) {
        def file = new java.io.FileWriter("output.text")
        file.write "The value is ${val}"
        file.close()
    }
}