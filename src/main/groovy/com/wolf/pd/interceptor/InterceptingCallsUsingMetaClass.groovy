package com.wolf.pd.interceptor

import com.wolf.java.Car2

// 若无权修改类，或这个类是Java类，或者在运行时决定基于某些条件或应用状态开始拦截调用。则要用metaClass拦截

Car2.metaClass.invokeMethod = { String name, args ->// 拦截Car实例上的所有调用
    System.out.println "Call to $name intercepted..."
    if (name != 'check') {
        System.out.println "running filter..."
        Car2.metaClass.getMetaMethod('check').invoke(delegate, null)// delegate指向要拦截其方法的目标对象
    }

    def validMethod = Car2.metaClass.getMetaMethod(name, args)
    if (validMethod != null) {
        validMethod.invoke(delegate, args)
    } else {
        Car2.metaClass.invokeMissingMethod(delegate, name, args)// 已经在invokeMethod方法中，不能再调用invokeMethod
    }
    System.out.println ""
}

car = new Car2()
car.start()
car.drive()
car.check()
try {
    car.speed()
} catch (Exception ex) {
    println ex
}
