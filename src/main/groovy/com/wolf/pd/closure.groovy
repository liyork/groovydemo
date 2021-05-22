package com.wolf.pd

import jdk.nashorn.internal.ir.annotations.Immutable

// 将遍历+偶数判断固定，执行的逻辑动态
def pickEven(n, block) {// 高阶函数，即以函数为参数，或返回一个函数作为结果的函数。groovy中称这种匿名代码块为闭包(closure)
    for (int i = 2; i <= n; i += 2) {// 代码简洁而且复用性更好，将部分实现逻辑委托出去提供了一种简单、方便的方式
        block(i)
    }
}
// 打印偶数
pickEven(4, { println it })// block变量保存了一个指向闭包的引用。
pickEven(4) { println it }// 若闭包是方法的最后一个参数则{}可以放外面

// 求所有偶数的值
total = 0
pickEven(10) { total += it }// 闭包是一个函数，用到的外部变量都绑定到了一个上下文或环境中，函数运行其中
println total

// 将闭包赋值给变量并复用
def totalSelectValues(n, closure) {
    total = 0
    for (i in 1..n) {
        if (closure(i)) {// 将选择过程委托给闭包
            total += i
        }
    }
    total// 闭包中最后一个表达式的值返回给调用者
}

println totalSelectValues(10) { it % 2 == 0 }
def isOdd = { it % 2 != 0 }// 通过变量isOdd引用闭包，这种预先定义的闭包可以在多个调用中复用。
println totalSelectValues(10, isOdd)

// 复用闭包
class Equipment {
    def calculator

    Equipment(calc) {
        calculator = calc
    }

    def simulate() {
        println "running simulation"
        calculator()
    }
}

def eq1 = new Equipment({ println "Calculator 1" })
def aCalculator = { println "Calculator 2" }
def eq2 = new Equipment(aCalculator)
def eq3 = new Equipment(aCalculator)
eq1.simulate()
eq2.simulate()
eq3.simulate()

// 向闭包传递参数
def tellFortune(closure) {
    closure new Date("09/20/2012"), "your day is filled with ceremony"
}

// ->将闭包的参数声明和闭包主体分开
tellFortune() { Date date, fortune ->// groovy支持可选的类型，
    println "fortune for $date is '${fortune}' "
}
// 若为参数选择了表现力好的名字，通常可以避免定义类型

// 用闭包进行资源清理
new FileWriter('output.txt').withWriter { writer ->// 使用groovy添加的withWriter方法
    writer.write('a')
}// 闭包返回时自动flush+close

// 自动open+close
class Resource {
    def open() { println "opened" }

    def close() { println "closed" }

    def read() { println "read.." }

    def write() { println "write.." }
}

def static use(closure) {// 使用Execute Around Method模式
    def r = new Resource()
    try {
        r.open()
        closure(r)
    } finally {
        r.close()
    }
}
// 可以将注意力集中于应用领域及其内在复杂性上，让类库去处理诸如确保文件I/O的清理等系统级人物
use { res ->
    res.read()
    res.write()
}

// coroutine?
def iterate(n, closure) {
    1.upto(n) {
        println "in iterate with value $it"
        closure(it)// 每次调用闭包，都从上一次调用中恢复total值
    }
}

println "calling iterate"
total = 0
iterate(4) {
    total += it
    println "in closure total so far is $total"
}
println "done"

// 预先绑定形参的闭包叫科里化闭包(curried closure)
def tellFortunes(closure) {
    Date date = new Date("09/20/2012")
    // 通过科里化避免重复发送date
    postFortune = closure.curry(date)// 科里化形参，从0开始，或者用rcurry/ncurry
    postFortune "your day is xxxx"
    postFortune "xxxx222"
}

tellFortunes() { date, fortune ->
    println "fortune for $date is '$fortune'"
}

// 确定闭包是否存在
def doSimeThing(closure) {
    if (closure) {
        closure()
    } else {
        println "using default implementation"
    }
}

doSimeThing() { println "use specialized implementation" }
doSimeThing()

// 检查闭包的参数
def completeOrder(amount, taxComputer) {
    tax = 0
    if (taxComputer.maximumNumberOfParameters == 2) {// 获取闭包接受的参数个数,getMaximumNumberOfParameters()
        tax = taxComputer(amount, 6.05)
    } else {
        tax = taxComputer(amount)
    }
    println "sales tax is $tax"
}

completeOrder(100) { it * 0.0825 }
completeOrder(100) { amount, rate -> amount * (rate / 100) }

// 获取参数类型
def examine(closure) {
    println "closure.maximumNumberOfParameters parameter(s) given:"
    for (aParameter in closure.parameterTypes) {
        println aParameter.name
    }
    println "--"
}

examine() {}// 默认接受一个参数Object类型
examine() { it }
examine() { -> }// 不接受任何参数
examine() { val1 -> }
examine() { Date val1 -> }
examine() { Date val1, val2 -> }
examine() { Date val1, String val2 -> }

// 闭包内this、owner和delegate三个属性，用于确定由哪个对象处理该闭包内的方法调用
def examiningClosure(closure) {
    closure()
}

println "out this is:" + this
// 闭包内this指向该闭包所绑定的对象(正在执行的上下文)
// 闭包内引用的变量和方法都会绑定到this，它负责处理任何方法调用，以及对任何属性或变量的访问。
// 若this无法处理，则专项owner，最后是delegate
examiningClosure() {// 闭包被创建成了内部类，closure$_run_closure23，父类是groovy.lang.Script
    println "in first closure:"
    println "class is " + getClass().name// 新内部类被创建
    println "this is " + this + ", super:" + this.getClass().superclass.name
    println "owner is " + owner + ", super: " + owner.getClass().superclass.name// 指向持有这个闭包者
    println "delegate is " + delegate + ", super:" + delegate.getClass().superclass.name// 默认被设置成了owner

    examiningClosure() {// 第二个闭包定义在第一个比包内，所以第一闭包成了第二个闭包的owner
        println "in closure within the first closure:"
        println "class is " + getClass().name// 新内部类的内部类被创建
        println "this is " + this + ", super:" + this.getClass().superclass.name// this与上面的一致，都是最外层的调用者
        println "owner is " + owner + ", super: " + owner.getClass().superclass.name
// 指向第一个closure，父类是groovy.lang.Closure
        println "delegate is " + delegate + ", super:" + delegate.getClass().superclass.name
    }
}

// 演示修改delegate
class Handler {
    def f1() { println "f1 of handler called.." }

    def f2() { println "f2 of handler called.." }
}

class Example {
    def f1() {
        println "f1 of example called"
    }

    def f2() {
        println "f2 of example called"
    }

    def foo(closure) {
        closure.delegate = new Handler()
        closure()
    }

    def foo1(closure) {
        def clone = closure.clone()
        clone.delegate = new Handler()
        clone()
//        new Handler().with closure// 可以代替上面三行
    }
}

def f1() { println "f1 of Script called" }

new Example().foo() {// 闭包内的方法调用首先被路由到闭包的上下文对象(this)，若没找到则路由到owner由于没有f2再路由到delegate
    f1()
    f2()
}

// 修改delegate，会有副作用，尤其是闭包还被用于其他的函数或线程时。
// 为避免副作用，应该复制该闭包，在副本上设置delegate，并使用副本
new Example().foo1() {
    f1()
    f2()
}

// 使用尾递归
// 一般方法，对于量大会产生stackoverflow
def factorial(BigInteger number) {
    if (number == 1) 1 else number * factorial(number - 1)
}

try {
    println "factorial of 5 is ${factorial(5)}"
//    println "number of bits in the result is ${factorial(5000).bitCount()}"
} catch (Throwable ex) {
    println "Caught ${ex.class.name}"
}

// 借助编译器优化技术和语言支持，递归程序可以转换为迭代程序
// 使用该特性，先将递归函数实现为闭包
def factorial
factorial = { int number, BigInteger theFactorial ->// 求阶乘的值，递归的中间结值
    // 第二个参数是本次计算的结果，将每次求得的值代入下一层
    number == 1 ? theFactorial : factorial.trampoline(number - 1, number * theFactorial)// 通过trampoline递归调用闭包
}.trampoline()// 调用后返回一个TrampolineClosure的一个实例
println "factorial of 5 is ${factorial(5, 1)}"// 调用TrampolineClosure实例的call方法，使用了简单的的for循环掉用闭包上的call方法，直到不再产生TrampolineClosure的实例
//println "number of bits in the result is ${factorial(5000, 1).bitCount()}"
// 这种递归称为尾递归，因为方法中最后的表达式是:a.结束递归，b.调用自身。而一般递归计算阶乘时，最后的表达式调用是*。

// 针对上面让用户多传递参数问题，解决方式是将闭包封装到一个函数内
def factorial1(int factorialFor) {
    def tailFactorial
    tailFactorial = { int number, BigInteger theFactorial = 1 ->// 内部直接给定默认值
        number == 1 ? theFactorial : tailFactorial.trampoline(number - 1, number * theFactorial)
    }.trampoline()
    tailFactorial(factorialFor)
}

println "factorial of 5 is ${factorial1(5)}"
//println "number of bits in the result is ${factorial1(5000).bitCount()}"

// 使用记忆化改进性能，动态规划，将问题分解为可以多次重复解决的若干部分，执行期间，将子问题的结果保存下来，供后续使用。
def timeIt(length, closure) {
    long start = System.nanoTime()
    println "max revenue for $length is ${closure(length)}"
    long end = System.nanoTime()
    println "time taken ${(end - start) / 1.0e9} seconds"
}

// 长度为下标的不同杆的价格，如长度为2的杆[2]=3价格是3
def rodPrices = [0, 1, 3, 4, 5, 8, 9, 11, 12, 14, 15, 15, 16, 18, 19, 15, 20, 21, 22, 24, 25, 24, 26, 28, 29, 35, 37, 38, 39, 40]
// 总需要的长度
def desiredLength = 27

@Immutable
class RevenueDetails {
    int revenue// 花费金额
    ArrayList splits// 使用的杆
    RevenueDetails(int revenue, ArrayList splits) {
        this.revenue = revenue
        this.splits = splits
    }

    @Override
    public String toString() {
        return "RevenueDetails{" +
                "revenue=" + revenue +
                ", splits=" + splits +
                '}';
    }
}

// 给定每个杆的价格和需求长度，计算最节省方式
def cutRod(prices, length) {
    if (length == 0) {
        new RevenueDetails(0, [])
    } else {
        def maxRevenueDetails = new RevenueDetails(Integer.MIN_VALUE, [])
        for (rodSize in 1..length) {// 从1到length
            def revenueFromSecondHalf = cutRod(prices, length - rodSize)// 递归，缩小计算长度，同样价格
            def potentialRevenue = new RevenueDetails(
                    prices[rodSize] + revenueFromSecondHalf.revenue,// 计算rodSize杆的价格+剩余length - rodSize的价格
                    revenueFromSecondHalf.splits + rodSize)// 集合相加
            if (potentialRevenue.revenue > maxRevenueDetails.revenue) {// 用较大者
                maxRevenueDetails = potentialRevenue
            }
        }
        maxRevenueDetails
    }
}

//timeIt desiredLength, { length -> cutRod(rodPrices, length) }

// 使用记忆化改进，将函数转为闭包，并在其上调用memoize方法后，创建了Memoize类的实例，其中有一个闭包引用和结果的缓存
// memoizeAtMost可以限制缓存大小，使用LRU。还有memoizeAtLeast/memoizeAtLeastBetween
def cutRod
cutRod = { prices, length ->
    if (length == 0) {
        new RevenueDetails(0, [])
    } else {
        def maxRevenueDetails = new RevenueDetails(Integer.MIN_VALUE, [])
        for (rodSize in 1..length) {
            def revenueFromSecondHalf = cutRod(prices, length - rodSize)
            def potentialRevenue = new RevenueDetails(
                    prices[rodSize] + revenueFromSecondHalf.revenue,
                    revenueFromSecondHalf.splits + rodSize
            )
            if (potentialRevenue.revenue > maxRevenueDetails.revenue) {
                maxRevenueDetails = potentialRevenue
            }
        }
        maxRevenueDetails
    }
}.memoize()
timeIt desiredLength, { length -> cutRod(rodPrices, length) }
















