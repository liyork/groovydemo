package com.wolf.pd.run.other

// 每个groovy对象都实现了GroovyObject接口，有一个invokeMethod方法，接受要调用的方法名字以及参数
class DynamicGroovyClass {
    def methodMissing(String name, args) {// 当某个不存在的方法被调用时，该方法被调用
        println "You called $name with ${args.join(', ')}."
        args.size()
    }
}