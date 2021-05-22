package com.wolf.pd.synthesis
// 混合使用invokeMethod和methodMissing

// invokeMethod是groovyOjbect的一个方法。
// methodMissing是groovy中后来引入的，是基于MetaClass的方法处理的组成部分。
// 若目标是处理对不存在的方法的调用，应该用methodMissing，若是拦截所有方法调用，而不管是否存在，用invokeMethod

class Person5 {
    def work() { "working... " }
}

Person5.metaClass.invokeMethod = { String name, args ->
    System.out.println "intercepting call for $name"

    def method = Person5.metaClass.getMetaMethod(name, args)
    if (method) {
        method.invoke(delegate, args)
    } else {
        Person5.metaClass.invokeMissingMethod(delegate, name, args)
    }
}

Person5.metaClass.methodMissing = { String name, args ->
    def plays = ['Tennis', 'VolleyBall', 'BasketBall']

    System.out.println "MethodMissing called for $name"
    def methodInList = plays.find { it == name.split('play')[1] }
    if (methodInList) {
        def impl = { Object[] vargs ->
            "playing ${name.split('play')[1]}..."
        }
        Person5.metaClass."$name" = impl
        impl(args)
    } else {
        throw new MissingMethodException(name, Person.class, args)
    }
}

jack = new Person4()
println jack.work()
println jack.playTennis()
println jack.playTennis()

try {
    jack.playPolitics()
} catch (ex) {
    println "Error: " + ex
}