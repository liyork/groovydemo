def a = null// null和1都会打印1111111和22222
//if (a == null || a == 1) {// 正常逻辑：为空或为1都要
//    println 1111111
//}
//
//// 与上面等同逻辑，防御编程，不为空且不为1则返回。即为空要，为234都不要，为1要
//if (a != null && a != 1) {
//    return
//}
//println 22222

// 逻辑：只要1的
def b = 2// 只有1都会打印1111111和22222
// 正常逻辑
if (b != null && b == 1) {
    println 1111111
}
// 这个逻辑为什么不行?因为null时判断b != null是false就继续下面了
//if (b != null && b != 1) {// 这个逻辑是从上面拷贝下来的
//    return
//}

// 这用groovy的语法
//if (b != 1) {
//    return
//}

// 这是一般写法，null返回，或者不为1返回
if (null == b || b != 1) {
    return
}
println 22222

