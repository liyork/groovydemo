def a = ["1", "2", "3"]
// 不报错，因为最后到了，所以next没有检查出来
//a.each {
//    if (it == "2") {
//        a.remove(it)
//    }
//}
//println a

// 报错
//a.each {
//    if (it == "1") {
//        a.remove(it)
//    }
//}

// 不报错
def iterator = a.iterator()
iterator.each {
    if (it == "3") {
        iterator.remove()
    }
}
println a
