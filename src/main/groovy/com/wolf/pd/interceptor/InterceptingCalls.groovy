package com.wolf.pd.interceptor

/**
 * 若一个Grovy对象实现了GroovyInterceptable接口，那么当调用该对象的任何一个方法时，不管是否存在，都调用invokeMethod。
 没有实现的groovy对象则只有调用到不存在的方法时，才会调用invokeMethod
 若一个对象的metaClass上实现了invokeMethod则也不管方法是否存在，都会调用invokeMethod
 */
// 因为Car实现了GroovyInterceptable，所以Car实例上的所有方法调用都会被其invokeMethod拦截
// 此方法适用于我们有Car2的访问权限
class Car implements GroovyInterceptable {
    def check() { System.out.println "check called..." }

    def start() { System.out.println "start called..." }

    def drive() { System.out.println "drive called..." }

    def invokeMethod(String name, args) {
        System.out.println "Call to $name intercepted..."
        if (name != 'check') {
            System.out.println "running filter..."
            Car.metaClass.getMetaMethod('check').invoke(this, null)
        }

        def validMethod = Car.metaClass.getMetaMethod(name, args)// 获取元方法
        if (validMethod != null) {
            validMethod.invoke(this, args)
        } else {
            Car.metaClass.invokeMethod(this, name, args)// 交给metaClass
        }
        System.out.println ""
    }
}

car = new Car()
car.start()
car.drive()
car.check()
try {
    car.speed()
} catch (Exception ex) {
    println ex
}

