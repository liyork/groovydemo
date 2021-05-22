package com.wolf.pd

import groovy.transform.CompileDynamic

// 使用@CompileStatic让groovy执行静态编译，则目标代码生成的字节码会和javac生成的很像。，可以获得更好的性能。
// groovyc编译代码，javac -p NoStaticCompile
@CompileDynamic
def shout1(String str) {
    println str.toUpperCase()
}