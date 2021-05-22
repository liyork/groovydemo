package com.wolf.pd
// list
lst = [1, 3, 4, 1, 8, 9, 2, 6]
println lst
println lst.getClass().name

println lst[0]
println lst[lst.size() - 1]

println "-1 val is :${lst[-1]}"// 从右开始遍历
println lst[-2]

subLst = lst[2..5]
println subLst.dump()// 子类内数据和父类是相同引用
subLst[0] = 55// integer是final的，所以子类的0换了一个引用
println "after subList[0]=55 lst = $lst"

println lst[-6..-3]

// 迭代
lst = [1, 3, 4, 1, 8, 9, 2, 6]
lst.each { print "$it " }// reverseEach/eachWithIndex
// 求和
total = 0
lst.each { total += it }
println "total is $total"

// 2倍，用each
doubled = []
lst.each { doubled << it * 2 }
println doubled

// 在每元素上执行操作并返回一个结果集合
println lst.collect { it * 2 }
// 想在集合的每个元素上执行操作，用each，若想得到一个进行了这个类计算的结果集合，用collect

// 找特定元素
lst = [4, 3, 1, 2, 4, 1, 8, 9, 2, 6]
println lst.find { it == 2 }// 第一个符合的元素,findIndexOf

println lst.findAll { it == 2 }// 所有符合的元素

// 集合的便捷方法
// 求和
lst = ['Programming', 'In', 'Groovy']
count = 0
lst.each { count += it.size() }
println count
// 收集每个元素的size，然后求和
println lst.collect { it.size() }.sum()
// 对集合中每个元素调用闭包，carrOver初始为0，每次迭代处理累积后都重新赋值
println lst.inject(0) { carrOver, element -> carrOver + element.size() }
// 合并
println lst.join(' ')
// 替换
lst[0] = ['Be', 'Productive']
println lst
// 拉平
lst = lst.flatten()
println lst
// minus
println lst - ['Productive', 'In']// 若有不存在的则忽略
println lst// 不会修该原数据，
// 用*打印每个元素大小
println lst*.size()// *这里是String的展开操作符(spread operator)
// 打散list
def words(a, b, c, d) {
    println "$a $b $c $c"
}

words(*lst)










