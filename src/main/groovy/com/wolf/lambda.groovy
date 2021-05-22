package com.wolf
// lambda表达式,Groovy采用{}实现。
Runnable run = { println 'run' }

// 1 lambda
new Thread({ println(11) }).start()

// 2 匿名内部类
public void sayHel(AA aa) {

}

sayHel(new AA() {
    @Override
    def sayHello() {
        return null
    }
})

// 用groovy的lambda进行替代匿名类
sayHel { println 11 }

// 16 闭包，是一段可执行的代码块或函数指针
// Groovy闭包都会转化为继承groovy.lang.Closure的类，这使得闭包可以赋值给变量，或者作为参数传递
// 闭包是可以用作方法参数的代码块，Groovy的闭包更象是一个代码块或者方法指针，代码在某处被定义然后在其后的调用处执行
// 格式：def closure = { [closureParameters ->] statements }，参数为可选项

// 闭包可以访问外部变量，而方法（函数）则不能
def str = 'hello'
def closure = {
    println str
}
closure()
assert closure instanceof Closure

// 闭包调用方式
def closure2 = {
    param -> println param
}

closure2('hello')
closure2.call('hello')
closure2 'hello'

// 闭包的参数可选，最后一行表示结果
def closure3 = { println 'hello'; 1 }
def closure31 = closure3()
println "closure result :$closure31"

// 多个参数以逗号分隔，参数类型和方法一样可以显式声明也可省略。
def closure4 = { String x, int y ->
    println "hey-${x} the value is ${y}"
}

// 如果只有一个参数的话，也可省略参数的定义，用it
def closure5 = { it -> println it }

// 闭包可以作为参数传入
def eachLine(lines, closure) {
    for (String line : lines) {
        closure(line)
    }
}

eachLine('a'..'c', { println it })
// 闭包作为方法的唯一参数或最后一个参数时可将闭包放在括号外
eachLine('a'..'c') { println it }

// 调用参数中有lambda的函数，通常可以省略括号，若最后一个参数是lambda，还可以单独写在括号后面
def func = { text, Closure closure1 ->
    println text
    closure1.call()
}
// 演进
func('1', {
    println '2'
})

func('5') {
    println('6')
}

func '3', {
    println('2')
}

// 方法中使用闭包
class Example {
    def static Display(clo) {
        clo("inner")
    }

    static void main(String[] args) {
//        useLambdaInMethod()

        useThird()
    }

    private static void useLambdaInMethod() {
        def str1 = "hello"
        def clos = { param -> println "${str1} ${param}" }
        clos("world")

        // 改变闭包中的引用
        str1 = "welcome"
        clos("world")

        Display(clos)
    }

    private static void useThird() {
        def lst = [1, 2, 3]
        lst.each { println it }

        def mp = ["a": 1, "b": 2]
        mp.each { println "${it.key} maps to ${it.value}" }
    }
}

def collect = [1, 2, 3, 4].collect {
    if (it % 2 == 0) return it * 2
}
println(collect)// groovy的closure中的return仅仅是返回closure所在的计算，对于使用它的函数不会退出
