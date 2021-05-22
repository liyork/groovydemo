package com.wolf.pd.unittest
// cd groovydemo/src/main/groovy
// groovyc -d classes com/wolf/java/Car.java
// groovy -classpath classes com/wolf/pd/unittest/CartTest.groovy
class CarTest extends groovy.test.GroovyTestCase {
    def car

    void setUp() {
        car = new com.wolf.java.Car()
    }

    // 正面测试
    void testInitialize() {
        assertEquals 0, car.miles
    }

    void testDriver() {
        car.drive(10)
        assertEquals 10, car.miles
    }

    // 负面测试
    void testDriveNegativeInput() {
        car.drive(-10)
        assertEquals 0, car.miles
    }

    // 异常测试
    void testException() {
        shouldFail { car.divide(2, 0) }// 在try-catch中调用闭包
        shouldFail(ArithmeticException) { car.divide(2, 0) }// 特定异常
    }
}