import java.util.Map.Entry

// simple abbreviated closure declaration
log = ''
(1..10).each { counter -> log += counter }
assert log == '12345678910'
log = ''
(1..10).each { log += it }
assert log == '12345678910'

// 声明闭包并赋值给变量
def printer = { line -> println line }

// 返回一个闭包
def Closure getPrinter() {
    return { line -> println line }
}
// 声明一个方法闭包,simple method closures in action
class MethodClosureSample {
    int limit

    MethodClosureSample(int limit) {
        this.limit = limit
    }

    boolean validate(String value) {
        return value.length() <= limit
    }
}

MethodClosureSample first = new MethodClosureSample(6)
MethodClosureSample second = new MethodClosureSample(5)
Closure firstClosure = first.&validate// method closure assignment,方法闭包被限制在一个实例方法
def words = ['long string', 'medium', 'short', 'tiny']
assert 'medium' == words.find(firstClosure)
assert 'short' == words.find(second.&validate)

// multimethod closures--the same method name called with different
// parameters is used to call different implementations
class MultiMethodSampel {
    int mysteryMethod(String value) {
        return value.length()
    }

    int mysteryMethod(List list) {
        return list.size()
    }

    int mysteryMethod(int x, int y) {
        return x + y
    }
}

MultiMethodSampel instance = new MultiMethodSampel()
Closure multi = instance.&mysteryMethod// only a single closure is created
// different implementations are called based on argument types
assert 10 == multi('string arg')
assert 3 == multi(['list', 'of', 'values'])
assert 14 == multi(6, 8)

// full closure declaration example
map = ['a': 1, 'b': 2]
map.each { k, v -> map[k] = v * 2 }// assign and then call a closure reference
assert map == ['a': 2, 'b': 4]
doubler = { k, v -> map[k] = v * 2 }
map.each(doubler)
assert map == ['a': 4, 'b': 8]

def doubleMethod(entry) {// method declaration
    map[entry.key] = entry.value * 2
}

// reference and call a method as a closure
doubler = this.&doubleMethod// 用ref.&操作符引用方法名称，作为一个闭包
map.each(doubler)
assert map == ['a': 8, 'b': 16]

// calling closures
def adder = { x, y -> return x + y }
assert adder(4, 3) == 7
assert adder.call(2, 6) == 8

// calling closures
def benchmark(repeat, Closure worker) {
    start = System.currentTimeMillis()
    // call closure the given number of times
    repeat.times { worker(it) }// it表示当前正在重复的次数
    stop = System.currentTimeMillis()
    return stop - start
}

slow = benchmark(10000) { (int) it / 2 }
fast = benchmark(10000) { it.intdiv(2) }
//assert fast * 15 < slow

adder = { x, y = 5 -> return x + y }
assert adder(4, 3) == 7
assert adder.call(7) == 12

def caller(Closure closure) {
    closure.getParameterTypes().size()
}

assert caller { one -> } == 1
assert caller { one, two -> } == 2
// a simple currying example,curry返回当前闭包的一个克隆品，已经绑定了一个或多个给定的参数，从左到右绑定
adder = { x, y -> return x + y }
def addOne = adder.curry(1)// 调用curry时创建了一个新的闭包,第一参数是1了
assert addOne(5) == 6

// Curry 最强大的地方是当闭包的参数是闭包本身的时候
// 这个模式是极其灵活的，因为怎样过滤的逻辑，怎样应用格式化和这样输出结果都是可配置的（甚至在运行时）
def configurator = { format, filter, line ->
    filter(line) ? format(line) : null
}
def appender = { config, append, line ->
    def out = config(line)
    if (out) append(out)
}
def dateFormatter = { line -> "${new Date()}: $line" }
def debugFilter = { line -> line.contains('debug') }
def myConf = configurator.curry(dateFormatter, debugFilter)// 固定格式和过滤的closure，可以用于对line进行操作了
def consoleAppender = { line -> println line }
def myLog = appender.curry(myConf, consoleAppender)// 设定配置和输出地，之后就可以对line操作了
myLog('here is some debug msg')
myLog('this will not be printed')

// 闭包实现了 isCase 方法， 这样闭包可以在 grep 和 switch 中作为分类器使用
assert [1, 2, 3].grep { it < 3 } == [1, 2]
switch (10) {
    case { it % 2 == 1 }: assert false
}

// internal
// 花括号显示了闭包声明的时间，不是执行的时间,闭包在声明的时候，绑定了本地变量的引用
def x = 0
10.times {// 闭包内可以访问变量x,这种生命周期的上下文需要闭包记住相应的一个引用
    x++
}
assert x == 10

// investigating the closure scope
class Mother {
    int field = 1

    int foo() {
        return 2
    }

    Closure brith(param) {
        def local = 3
        def closure = { caller ->
            [this, field, foo(), local, param, caller, owner]
        }
        return closure
    }
}

Mother julia = new Mother()
closure = julia.brith(4)
context = closure.call(this)
println context[0].class.name// Script
assert context[1..4] == [1, 2, 3, 4]// 这些元素在声明的时候被绑定而不是在闭包被调用的时候
assert context[5] instanceof Script// the calling object,// this引用的是调用闭包的对象，在这里，它们将所有的方法调用代理给 delegate对象， delegate 缺省是声明闭包的对象（即owner），这样保证了闭包在它的上下文中运
assert context[6] instanceof Mother// the declaring object, owner引用到声明闭包的对象
firstClosure = julia.brith(4)// closure braces are like new，重新构建闭包
secondClosure = julia.brith(4)
// 方法在类产生的时候就被构建，而且只会构建一次，闭包对象在运行的时候被构建，并且相同的代码也有可能被构建多次
assert !firstClosure.is(secondClosure)

// the accumulator problem in groovy
def foo(n) {
    return { n += it }
}

def accumulator = foo(1)
assert accumulator(2) == 3
assert accumulator(1) == 4

// 返回结果
[1, 2, 3].collect { it * 2 }
[1, 2, 3].collect { return it * 2 }
def collect = [1, 2, 3, 4].collect {
    if (it % 2 == 0) return it * 2
}
println(collect)// groovy的closure中的return仅仅是返回closure所在的计算，对于使用它的函数不会退出

// the visitor pattern in groovy
// 这个Visitor对象知道怎样遍历这个集合，也知道怎样为不同的类型执行商业逻辑函数，如果集合改变了或者逻辑函数随着时间而改变了，仅仅Visitor类是固定的
class Drawing {// 有list，调用accept时，遍历每个元素对其调用他们的accept方法并传入closure
    List shapes

    def accept(Closure yield) {// 集合遍历操作，固定。业务逻辑在closure中。
        shapes.each { it.accept(yield) }
    }
}

class Shape {
    def accept(Closure yield) {// 调用参数closure并用this作为参数
        yield(this)
    }
}

class Square extends Shape {
    def width

    def area() { width**2 }
}

class Circle extends Shape {
    def radius

    def area() { Math.PI * radius**2 }
}

def picture = new Drawing(shapes: [new Square(width: 1), new Circle(radius: 1)])
def total = 0
picture.accept { total += it.area() }// it就是每个元素this
println "The shapes in this drawing cover an area of $total units."
println 'The individual contributions are: '
picture.accept { println it.class.name + ":" + it.area() }

// Builder模式提供了通过一个产品的成分逐步组装形成产品的处理
// Groovy的builder提供了一种解决方案，该方案使用嵌套的闭包来方便的确定复杂的产品
// Grails之类的高级别框架做得更远一些



