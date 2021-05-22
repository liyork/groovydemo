// 21 Range
// Ranges允许您创建顺序值List,这些可以用作List，因为Range扩展了java.util.List
def range = 0..5
assert (0..5).collect() == [0, 1, 2, 3, 4, 5]// [x,y]
assert (0..<5).collect() == [0, 1, 2, 3, 4]// [x,y)
assert (0..5) instanceof List
assert (0..5).size() == 6
assert ('a'..'d').collect() == ['a', 'b', 'c', 'd']
println 1 in [1, 2]
println 1.1 in (1..2)

for (int x = 0; x < 2; x++) {
    println("common for $x")
}

for (x in 1..10) {
    println x
}

//('a'..'z').each {
//    println it
//}

//println("a->c->d..")
('a'..'c').each {
    if (it == 'b') {
        return// 不能退出
    }
    println(it)
}

// 针对空list调用any不会报错
[].any {
    println 1111
}

// return true时直接跳出
('a'..'c').any {
    println(it)
    if (it == 'b') {
        return true// 不能退出
    }
    // 这里能用else?
//    else (it == 'b') {// o，不对，这里else不能加条件了，可能就被认为是另一个lambada了。。
//        return true
//    }
    // Caught: groovy.lang.MissingMethodException: No signature of method: java.lang.Boolean.call() is applicable for argument types: (main.wolf.hello$_run_closure12$_closure16) values: [main.wolf.hello$_run_closure12$_closure16@6f6621e3]
//    return true
// 默认返回的应该是false
}

def age = 25
switch (age) {
    case 0..17:
        println '未成年'
        break
    case 18..30:
        println '青年'
        break
    case 31..50:
        println '中年'
        break
    default:
        println '老年'
}