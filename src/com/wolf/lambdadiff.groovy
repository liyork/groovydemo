package com.wolf
// 1.1 函数类型，只有一种Closure
Closure sum = { Integer x, Integer y -> x + y }

// 1.2 简化格式，def也可以省略
sumSimple = { x, y -> x + y }

// 2. trailing lambda，函数的最后一个参数是一个函数类型，可将lambda表达式放到参数列表外
// 2.1 非一个参数
def a3(def x, def y, Closure closure) {
    closure(x, y)
}

a3(1, 2, { x, y -> x + y })
a3(1, 2) { x, y -> x + y }
// 2.2 一个参数
def a1(Closure closure) {
    closure(1, 2)
}

a1 { x, y -> x + y }

// 3.it，lambda只有一个参数时，用it表示
def itFun(Closure closure) {
    closure(1)
}

itResult = itFun { println(it) }

it = { println(it) }
it(1)

// 4 lambda返回值
retval = {
    1 + 1
    3
}
retval()

// 5 无用变量处理，_被认为正常的声明
map = [1: "a", 2: "b"]
map.each { _, value -> println(_ + value) }

// 6 lambda内访问外部变量
ints = [1, 2, 3]
sum1 = 0
ints.findAll(it > 0).each {
    sum += it
}
println(sum)

// 7 调用lambda
sum2 = { x, y -> x + y }
sum2(1, 2)
