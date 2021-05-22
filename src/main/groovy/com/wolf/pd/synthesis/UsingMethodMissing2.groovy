package com.wolf.pd.synthesis

// 使用缓存

class Person3 {
    def work() { "working.." }
    def plays = ['Tennis', 'VolleyBall', 'BasketBall']

    def methodMissing(String name, args) {// 拦截不存在的方法调用
        System.out.println "MethodMissing called for $name"
        def methodInList = plays.find { it == name.split('play')[1] }
        if (methodInList) {
            def impl = { Object[] vargs ->
                "playing ${name.split('play')[1]}..."
            }
            Person3 instance = this
            instance.metaClass."$name" = impl// 以后再调用就重复使用
            impl(args)
        } else {
            throw new MissingMethodException(name, Person.class, args)
        }
    }
}

jack = new Person3()
println jack.work()
println jack.playTennis()
println jack.playTennis()
