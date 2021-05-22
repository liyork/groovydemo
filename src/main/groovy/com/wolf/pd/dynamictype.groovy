package com.wolf.pd

import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode

// 动态类型获取帮助
def takeHelp(helper) {// 没有指定类型，默认Object
    helper.helpMoveThings()// 调用方法，design by capability
}

class Man {
    void helpMoveThings() {
        println "man's helping"
    }
}

class Woman {
    void helpMoveThings() {
        println "woman's helping"
    }
}

class Elephant {
    void helpMoveThings() {
        println "Elephant's helping"
    }

    void eatSugarcane() {
        println "i love sugarcane"
    }
}

// 很像go，不用有强制接口契约，有方法能力就能说是这个接口
// 可以方便加入一些设计反思，为创建容易扩展的代码提供更多的灵活性和动力
takeHelp(new Man())
takeHelp(new Woman())
takeHelp(new Elephant())

// 因为没有编译器帮助进行检查，需要依赖单元测试保证其正确性
// 询问是否有方法支持
def takeHelpAndReward(helper) {
    helper.helpMoveThings()
    if (helper.metaClass.respondsTo(helper, 'eatSugarcane')) {
        helper.eatSugarcane()
    }
}

takeHelpAndReward(new Man())
takeHelpAndReward(new Woman())
takeHelpAndReward(new Elephant())

// 多太
class Employee {
    void raise(Number amount) {
        println "Employee got raise"
    }
}

class Executive extends Employee {
    void raise(Number amount) {
        println "Executive got raise"
    }

    void raise(java.math.BigDecimal amount) {
        println "Executive got outlandish raise"
    }
}

void giveRaise(Employee employee) {
    employee.raise(new BigDecimal(10000.00))
//    employee.raise(10000.00)// 语句同上
}

// 若一个类中有重载的方法，groovy会聪明地选择正确的实现——不仅基于目标对象还基于参数，
// 方法分配基于多个实体——目标加参数，被称为多分派或多方法(multimethods)
giveRaise new Employee()
giveRaise new Executive()

// 兼容泛型问题
// 参考java的testCollectionRemove
ArrayList<String> lst = new ArrayList<String>()
Collection<String> col = lst
lst.add("one")
lst.add("two")
lst.add("three")
lst.remove(0)
col.remove(0)// groovy的动态与多方法能力解决了这种问题
println "number of elements is:" + lst.size()
println "number of elements is:" + col.size()

// 强制静态类型检查，可用于类和方法
@TypeChecked
def shout(String str) {
    println str.toUpperCase()
//    println str.toUppercase()// 拼写错误
}

try {
    shout('hello')
} catch (exception) {
    println "Failed.."
}

// 由于编译时检查并验证，这会阻止使用元编程能力
//@TypeChecked
def shoutString(String str) {
    println str.shout()
}

str = 'hello'
str.metaClass.shout = { -> toUpperCase() }// 使用元编程注入方法
shoutString(str)

// 变通方案，可以用invokeMethod()

// 静态类型检查没有限制groovy向JDK添加方法，还会检查一个特殊类DefaultGroovyMethods，还会检查定制扩展
@TypeChecked
def printInReverse(String str) {
    println str.reverse()
}

// 用instanceof检查后，不用再强制转换
@TypeChecked
def use(Object instance) {
    if (instance instanceof String) {
        println instance.length()// 不必再强制转换
    } else {
        println instance
    }
}

use('hello')
use(4)

// 跳过部分方法的静态类型检查
@TypeChecked
class Sample {
    def method1() {

    }

    @TypeChecked(TypeCheckingMode.SKIP)
    def method2(String str) {
        println str.shout()
    }
}
// 静态类型检查旨在帮助在编译时识别出一些错误，若没有错误则编译器为类型检查版本和无类型检查版本生成的字节码使用类似的。
// 若想生成高效的字节码，需要静态编译

