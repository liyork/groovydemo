package com.wolf

var a = null
// 对null进行each没有问题！
a.each {
    println(111)
}
println(222)

//a.xxx// 对null进行属性操作就报错

var Integer[] b = new Integer[3]
if (b[2]) {// 若不存在或null则是false
    println(111)
} else {
    println(222)
}
if (b[2] == 1) {// 为空也不会进行判断
    println(111)
} else {
    println(222)
}
