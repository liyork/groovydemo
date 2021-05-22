package com.wolf.pd
// ==为equals，is()对比引用
str1 = 'hello'
str2 = str1
str3 = new String('hello')
str4 = 'Hello'
println "str1 == str2: ${str1 == str2}"// 值相同
println "str1 == str3: ${str1 == str3}"
println "str1 == str4: ${str1 == str4}"
println "str1.is(str2): ${str1.is(str2)}"// 相同引用
println "str1.is(str3): ${str1.is(str3)}"
println "str1.is(str4): ${str1.is(str4)}"

// ==在类没有实现Comparable接口时映射到equals，否则映射到compareTo
class A {
    boolean equals(Other) {
        println "A equals called"
        false
    }
}

class B implements Comparable {
    boolean equals(Other) {
        println "B equals called"
        false
    }

    @Override
    int compareTo(Object o) {
        println "compareTo called"
        0
    }
}

new A() == new A()
new B() == new B()

// groovy的类型是可选，编译器groovyc多数不会执行完整的类型检查，而是在遇到类型定义(或赋值)时执行强制类型转换
// 调用不存在方法时也是运行时错误

// def用于定义方法、属性和局部变量
// in用于for循环中指定循环的区间,如for(i in 1..10)

// groovy的闭包是用{}定义的，定义匿名内部类也用{}

// 注意构造器中的闭包参数
class Calibrator {
    Calibrator(calculationBlock) {
        println "using"
        calculationBlock()
    }
}

def calibrator1 = new Calibrator({ println "the calculation provided" })// 对于这个场景，不要将{}放到括号外面，那样groovy会认为要创建一个匿名内部类
def calculation = { println "another calculation provided" }
def calculation2 = new Calibrator(calculation)

// 基本类型数组
int[] arr = [1, 2, 3, 4]
println arr
println "class is " + arr.getClass().name

//若身省略左边类型信息int[]则groovy假设是ArrayList的实例，或者用as
def arr2 = [1, 2, 3] as int[]
println arr2
println "class is " + arr2.getClass().name

