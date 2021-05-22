package com.wolf.pd.mop

import groovy.test.GroovyTestCase

/**
 * 调用方法不同处理方式：
 * 1. 对于POJO，去应用类的MetaClassRegistry取它的MetaClass并将方法调用委托给它，在它MetaClass上定义的任何拦截或方法都优先
 * 2. 对于POGO
 * a.若实现了GroovyInterceptable那么所有调用都路由到invokeMethod,通过里面再路由分发
 * b.若该POGO没有实现GroovyInterceptable，先查找他的MetaClass方法，之后若没有则查找POGO自身上方法。若也没有则以方法名查找
 * 属性或字段，若属性或字段是closure类型，会调用它，替代方法调用。
 * 若也没有，若POGO有methodMissing则调用，否则调用invokeMethod，都没有则invokeMethod默认抛出异常MissingMethodException
 */
class TestMethodInvocation extends GroovyTestCase {
    // 使用metaClass动态添加toString
    void testInterceptedMethodCallOnPOJO() {
        def val = new Integer(3)
        Integer.metaClass.toString = { -> 'intercepted' }
        assertEquals "intercepted", val.toString()
    }

    // 有拦截器时，有无方法都执行invokeMethod，只能从invokeMethod里面再调用目标方法否则不执行目标方法了
    void testInterceptableCalled() {
        def obj = new AnInterceptable()
        assertEquals 'intercepted', obj.existingMethod()
        assertEquals 'intercepted', obj.nonExistingMethod()
    }

    // 通过metaClass将方法覆盖
    void testInterceptedExistingMethodCalled() {
        AGroovyObject.metaClass.existingMethod2 = { ->
            println "testInterceptedExistingMethodCalled intercepted"
            'intercepted'
        }
        def obj = new AGroovyObject()
        assertEquals 'intercepted', obj.existingMethod2()
    }

    // 没有拦截器，直接调用
    void testUnInterceptedExistingMethodCalled() {
        def obj = new AGroovyObject()
        assertEquals 'existingMethod', obj.existingMethod()
    }

    // 调用closure
    void testPropertyThatIsClosureCalled() {
        def obj = new AGroovyObject()
        assertEquals 'closure called', obj.closureProp()
    }

    // 针对没有的方法，优先执行methodMissing，若没有则执行invokeMethod
    void testMethodMissingCalledOnlyForNonExistent() {
        def obj = new ClassWithInvokeAndMissingMethod()
        assertEquals 'existingMethod', obj.existingMethod()
        assertEquals 'missing called', obj.nonExistingMethod()
    }

    void testInvokeMethodCalledForOnlyNonExistent() {
        def obj = new ClassWithInvokeOnly()
        assertEquals 'existingMethod', obj.existingMethod()
        assertEquals 'invoke called', obj.nonExistingMethod()
    }

    // 没有methodMissing和invokeMethod则调用不存在方法时异常
    void testMethodFailsOnNonExistent() {
        def obj = new TestMethodInvocation()
        shouldFail(MissingMethodException) { obj.nonExistingMethod() }
    }
}

class AnInterceptable implements GroovyInterceptable {
    def existingMethod() {
        println "existingMethod in com.wolf.pd.mop.AnInterceptable"
    }

    def invokeMethod(String name, args) { 'intercepted' }
}

class AGroovyObject {
    def existingMethod() {
        'existingMethod'
    }

    def existingMethod2() {
        'existingMethod2'
    }

    def closureProp = { 'closure called' }
}

class ClassWithInvokeAndMissingMethod {
    def existingMethod() {
        'existingMethod'
    }

    def invokeMethod(String name, args) {
        'invoke called'
    }

    def methodMissing(String name, args) {
        'missing called'
    }
}

class ClassWithInvokeOnly {
    def existingMethod() { 'existingMethod' }

    def invokeMethod(String name, args) {
        'invoke called'
    }
}

