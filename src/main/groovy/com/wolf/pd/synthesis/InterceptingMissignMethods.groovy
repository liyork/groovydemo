package com.wolf.pd.synthesis

// 对于实现GroovyInterceptalbe的对象，调用该对象上的任何方法(不论是否存在)，都会调用到invokeMethod
// 只有对象将控制转移给其MetaClass的invokeMethod时，methodMissing才会被调用

// 这种方式只能是基于源文件修改进行合成

class Person4 implements GroovyInterceptable {
    def work() { "working.." }
    def plays = ['Tennis', 'VolleyBall', 'BasketBall']

    def invokeMethod(String name, args) {
        System.out.println "intercepting call for $name"

        def method = metaClass.getMetaMethod(name, args)
        if (method) {
            method.invoke(this, args)
        } else {
            metaClass.invokeMethod(this, name, args)
        }
    }

    def methodMissing(String name, args) {
        System.out.println "MethodMissing called for $name"
        def methodInList = plays.find { it == name.split('play')[1] }
        if (methodInList) {
            def impl = { Object[] vargs ->
                "playing ${name.split('play')[1]}..."
            }
            Person4 instance = this
            instance.metaClass."$name" = impl
            impl(args)
        } else {
            throw new MissingMethodException(name, Person.class, args)
        }
    }
}

jack = new Person4()
println jack.work()
println jack.playTennis()
println jack.playTennis()
