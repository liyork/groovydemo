package com.wolf

import groovy.transform.PackageScope
import groovy.xml.Entity

import java.nio.file.FileVisitResult

// 怎么有种感觉，groovy很像java啊。。
// 可以new也可以直接()构造

// 1 如果 Groovy 脚本文件里只有执行代码，没有类的定义，则 Groovy 编译器会生成一个 Script 的子类，类名和脚本文件的文件名一样，
// 而脚本中的代码会被包含在一个名为run的方法中，同时还会生成一个main方法，作为整个脚本的入口
println("hello")
println "hello"  // 省去括号

//2 运行时分派
// 变量声明：类型 变量  //--和java一样
void method(String arg) {
    println 1
}

void method(Object arg) {
    println 2
}

// 调用的方法在运行时被动态选择。称为运行时分派或多重方法，根据运行时实参(argument)的类型来选择方法。
Object o = getParam()
method(o);

Object getParam() {
    return "Object";
}

// 3 数组
int[] array = [1, 2, 3]
array[2] = 1
println(array)

var path = "/Users/chaoli/intellijWrkSpace/groovydemo/src/com/wolf/reg"

// 4 ARM（Automatic Resource Management，自动资源管理）语句块
new File(path + "/testreg.groovy").eachLine('Utf-8') {
    line, nb ->
        println "line $line, $nb"
}

// 6 Groovy 默认会隐式的创建getter、setter方法，并且会提供带参的构造器，下面两者是等价的。
class Person {
    String name

    // Groovy 中默认是public的，要想包级别访问需要用@PackageScope注解
    @PackageScope
    String age
}

// 7 定义变量
def a = 1
println a

def person = new Person(name: "张三")
assert '张三' == person.name
person.name = '李四'
assert '李四' == person.getName()

// 8 内部类
class A {
    // 静态内部类
    static class B {}
}

new A.B()

// 9 接口
interface AA {
    sayHello()
}

// 12 == 等价于equals
// 支持Shebang line(UNIX系统支持一种特殊的单行注释,用于指明脚本的运行环境，便于在终端中可以直接运行)
// 如 #!/user/bin/env groovy
// println "hell"

// 13 变量默认访问修饰符是public
// def修饰的变量可以省略变量类型的定义，根据变量值进行类型推导
def a1 = 123
def b1 = 'b'
def c1 = true
boolean d1 = false
int e1 = 123

// 变量的字面量值是数字时，类型会根据数字的大小自动调整
def a3 = 1
assert a3 instanceof Integer

// Integer.MAX_VALUE
def b = 2147483647
assert b instanceof Integer

// Integer.MAX_VALUE + 1
def c = 2147483648
assert c instanceof Long

// Long.MAX_VALUE
def d = 9223372036854775807
assert d instanceof Long

// Long.MAX_VALUE + 1
def e = 9223372036854775808
assert e instanceof BigInteger

def decimal = 123.456
println decimal.getClass() // class java.math.BigDecimal

// 浮点型字面量为了精度 Groovy 默认使用的类型为 BigDecimal
def decimal1 = 123.456
println decimal1.getClass()


// 为 数字类型提供一种更简单的声明类型的方式：类型后缀
// - Integer 使用I或i
//- Long 使用L或l
//- BigInteger 使用G或g
//- BigDecimal 使用G或g
//- Double 使用D或d
//- Float 使用F或f

def a2 = 123I
assert a2 instanceof Integer
def b2 = 123L
assert b2 instanceof Long

// 14 字符字面量
char c2 = 'A'
assert c2 instanceof Character

def c3 = 'B' as char
assert c3 instanceof Character

def c4 = (char) 'C'
assert c4 instanceof Character

// 15 方法默认访问修饰符是public
// return被省略时返回最后一行代码的运行结果
String method1() {
    return 'hello'
}

assert method1() == 'hello'
// 返回类型可以不需要声明，但需用def关键字。
def method2() {
    return 'hello'
}

def method3() {
    'hello'
}


// 方法的参数类型被省略时是Object类型
def add(int a, int b) {
    return a + b
}

def add(a, b) {
    a + b
}

// 17 delegate,owner,this
// 查看Closure类的源码，可以发现闭包中有delegate、owner、this三个成员变量，调用闭包没有的属性/方法时，会尝试在这三个变量上调用
// 一般情况下：
// this指向闭包外部的Object，指定义闭包的类
// owner指向闭包外部的Object/Closure，指直接包含闭包的类或闭包
// delegate默认和owner一致，指用于处理闭包属性/方法调用的第三方对象，可以修改
// 在闭包构造时this和owner就已经确定并传入，是只读的。如果需要修改，可以用Closure.rehydrate()方法克隆新的闭包，同时设置其this和owner。
//Closure还有一个resolveStrategy属性，有多种值（OWNER_FIRST、DELEGATE_FIRST、OWNER_ONLY、DELEGATE_ONLY、TO_SELF），默认为OWNER_FIRST，表示调用闭包没有定义的属性/方法时，先尝试从owner取，再尝试从delegate取
class MyDelegate {
    def func1 = {
        println 'hello'
    }
}

def c5 = {
    func1()
}
// 设定代理
c5.delegate = new MyDelegate()
c5.call()


// 19 Arrays
String[] arrStr = ['A', 'B', 'C']// 声明类型为字符数组
assert arrStr instanceof String[]

def numArr = [1, 2, 3] as int[]

// 20 Maps
// 用Map字面值符号创建的Map是有序的
def colorMap = [red: '1', green: '2']

assert colorMap['red'] == '1'
colorMap['pink'] = '3'
colorMap.yellow = '4'

def keyVal = 'name'
def persons = [(keyVal): '33']// 计算keyVal的值后作为key

def emptyMap = [:]

def row = [:]
row.containsKey('name') ? println("key is in") : null

def map = [a: 1, b: 2, c: 3]
def other = [c: 3, a: 1, b: 1]
assert map != other
assert map.any { it -> it.value > 2 }// 若有匹配，返回true
assert map.every { it -> it.value < 4 }// 若都匹配，返回true

// 浅拷贝
colorMap.clone()

colorMap.each { entry ->
    println "$entry.key $entry.value"
}

colorMap.eachWithIndex { entry, i ->
    println "$i $entry.key $entry.value"
}

colorMap.each { k, v ->
    println "$k, $v"
}

colorMap['a'] = 1

colorMap.computeIfAbsent('c', { it ->
    println(1111)
    it.toString() + "1"
})

// 注意：不应该使用GString作为Map的键，因为GString的哈希码与等效String的哈希码不同

['a', 7, 'b', [2, 3]].groupBy {
    it.class
} == [(String)   : ['a', 'b'],
      (Integer)  : [7],
      (ArrayList): [[2, 3]]
]

// map2List
def collect = ['a': 1, 'b': 2].collect { k, v ->
    k
}
println "collect->:$collect"

// 21 Range
// Ranges允许您创建顺序值List,这些可以用作List，因为Range扩展了java.util.List
def range = 0..5
assert (0..5).collect() == [0, 1, 2, 3, 4, 5]// [x,y]
assert (0..<5).collect() == [0, 1, 2, 3, 4]// [x,y)
assert (0..5) instanceof List
assert (0..5).size() == 6
assert ('a'..'d').collect() == ['a', 'b', 'c', 'd']

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
    // Caught: groovy.lang.MissingMethodException: No signature of method: java.lang.Boolean.call() is applicable for argument types: (com.wolf.hello$_run_closure12$_closure16) values: [com.wolf.hello$_run_closure12$_closure16@6f6621e3]
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

// 22 执行外部程序
def process = "ls -l".execute()
//println "Found text ${process.text}"

// 遍历命令进程的输入流(命令的标准输出)
process.in.eachLine { line ->
    println line
}

// 由于一些本地平台仅为标准输入和输出流提供有限的缓冲区大小，因此无法及时写入输入流或读取子过程的输出流可能导致子过程阻塞甚至死锁
def p = "ls -l".execute([], new File(path))
p.consumeProcessOutput()
p.waitFor()
println p.text

// pipe
proc1 = 'ls'.execute()
proc2 = 'tr -d  o'.execute()
proc1 | proc2
proc2.waitFor()
if (proc2.exitValue()) {
    println proc2.err.text
} else {
    println proc2.text
}

// ConfigSlurper，类似property
def config = new ConfigSlurper().parse('''
    app.data = new Date()
    app.age = 42
    app {
      name = "Test${age}"
    }
''')

assert config.app.data instanceof Date
assert config.app.age == 42
assert config.app.name == 'Test42'

// 23 Expando
def expando = new Expando()
expando.toString = { -> 'john' }
expando.say = { String s -> "josn says: ${s}" }
assert expando as String == 'john'
assert expando.say('Hi') == 'josn says: Hi'

String a4 = null
// 为空则返回null，不会执行toString方法，这个感觉好像kotlin?
println a4?.toString()

// groovy中没有let
//def String age1 = null
//age1?.let {
//    println(it.length)
//}

def listOfMaps = [['a': 11, 'b': 12], ['a': 21, 'b': 22], null]
// 扩展点符号
assert listOfMaps*.a == [11, 21, null] // 适用于空值
// GPath标记
assert listOfMaps.a == [11, 21]// 只收集非null

assert ['z': 900, *: ['a': 100, 'b': 200], 'a': 300] == ['a': 300, 'b': 200, 'z': 900]

// 星号“*”运算符,允许您对集合的所有元素调用方法或属性
assert [1, 3, 5] == ['a', 'few', 'words']*.size()
persons = [new Person(name: 'Hugo', age: 17), new Person(name: 'Sandra', age: 19)]
assert ['17', '19'] == persons*.age

// 使用下标运算符切片
def text = 'nice cheese gromit!'
def x = text[2]
assert x == 'c'
assert x.class == String

def sub = text[5..10]
assert sub == 'cheese'

var list = [10, 11, 12, 13]
def answer = list[2, 3]
assert answer == [12, 13]

list = 100..200
assert list[1, 3, 20..25, 33] == [101, 103, 120, 121, 122, 123, 124, 125, 133]

list = ['a', 'x', 'x', 'd']
list[1..2] = ['b', 'c']
assert list == ['a', 'b', 'c', 'd']

// 负值，逆向
x = text[-1]
assert x == "!"
assert text[-7..-2] == "gromit"

// 24 io
def file = new File(path, "test.groovy")
file.eachLine { line ->
    println line
}
// 该方法能够确保资源正确地关闭
file.eachLine { line, nb ->
    println "Line $nb: $line"
}

list = file.collect { it }
def array1 = file as String[]
file.bytes

// 自动关闭stream
file.withInputStream { stream ->

}

file.withWriter('utf-8') { writer ->
    writer.writeLine('xxx')
}

file << '''Into the ancient pond
A frog jumps
Water’s sound!'''

file.bytes = [66, 22, 11]
// 自动关闭
file.withOutputStream { stream ->

}

// 遍历文件树
def dir = new File(path)
dir.eachFile { f ->
    println f.name
}

dir.eachFileMatch(~/.*\.txt/) { f ->
    println(f.name)
}

// 指定目录递归查找
dir.eachFileRecurse { f ->
    println f.name
}

dir.traverse { f ->
    if (f.directory && f.name == 'bin') {
        FileVisitResult.TERMINATE
    } else {
        println f.name
        FileVisitResult.CONTINUE
    }
}

// 25 序列化
//String message = 'Hello from Groovy'
//def file = new File('xx')
//file.withDataOutputStream { out ->
//    out.writeBoolean(true)
//    out.writeUTF(message)
//}
//
//file.withDataInputStream { input ->
//    assert input.readBoolean() == true
//    assert input.readUTF() == message
//}

// Object输出流，需要实现Serializable
//Person p = new Person(name: 'Bob', age: 75)
//file.withObjectOutputStream { out ->
//    out.writeObject(p)
//}
//file.withObjectInputStream { intput ->
//    def p2 = intput.readObject()
//    println(p2)
//}

// 26 运行时元编程
// Groovy语言支持两种类型的元编程：运行时元编程和编译时元编程。 第一个允许在运行时改变类模型和程序的行为，而第二个只在编译时发生。
// 在Groovy中，我们使用了三种对象：POJO，POGO和Groovy Interceptors。 Groovy允许对所有类型的对象进行元编程，但是是以不同的方式进行的。
// a.POJO - 一个常规的Java对象，它的类是用Java或任何其他运行在JVM上的编程语言编写的。
// b.POGO - 一个Groovy对象，它的类是用Groovy编写的。 它扩展了java.lang.Object并在默认情况下实现了groovy.lang.GroovyObject接口。
// c.Groovy Interceptor - 一个Groovy对象，它实现了groovy.lang.GroovyInterceptable接口并具有方法拦截功能，我们将在GroovyInterceptable部分讨论。
// 对于每个方法调用，Groovy检查对象是POJO还是POGO。 对于POJO，Groovy从groovy.lang.MetaClassRegistry获取它的MetaClass，并将方法调用委托给它。 对于POGO，Groovy需要更多步骤

// GroovyObject接口
// groovy.lang.GroovyObject是Groovy中的主要接口，就像Object类是Java中的主要接口一样。
// GroovyObject有一个默认实现groovy.lang.GroovyObjectSupport，负责将调用传递给groovy.lang.MetaClass对象
// 根据运行时元编程中的模式，当您调用的方法不存在于Groovy对象上时，将调用invokeMethod
class SomeGroovyClass {
    def invokeMethod(String name, Object args) {
        return "call invokeMethod $name $args"
    }

    def test() {
        return 'method exits'
    }

    def property1 = 'ha'
    def field2 = 'ho'
    def field4 = 'hu'

    def getField1() {
        return 'getHa'
    }

    // 对属性的每个读取访问都可以通过覆盖当前对象的getProperty()方法来拦截
    def getProperty(String name) {
        if (name != 'field3')
            return metaClass.getProperty(this, name)//除field3之外，将请求转发到对应的getter以获取属性
        else
            return 'field3'
    }
}

def someGroovyClass = new SomeGroovyClass()
assert someGroovyClass.test() == 'method exits'
assert someGroovyClass.someMethod() == 'call invokeMethod someMethod []'

assert someGroovyClass.field1 == 'getHa'
assert someGroovyClass.field2 == 'ho'
assert someGroovyClass.field3 == 'field3'
assert someGroovyClass.field4 == 'hu'

class POGO {
    String property1
// 通过覆盖setProperty()方法来拦截对属性的写访问权限：
    void setProperty(String name, Object value) {
        this.@"$name" = 'overridden'
    }
}

def pogo = new POGO()
pogo.property1 = 'a'
assert pogo.property1 == 'overridden'

// 可以访问对象的metaClass或设置您自己的MetaClass实现以更改默认拦截机制
// getMetaclass
//someObject.metaClass

// setMetaClass
//someObject.metaClass = new OwnMetaClassImplementation()

interface Foo2 {
    var count
}

class MyMetaClass implements Foo2 {
    int getCount() {
        return 0
    }
}
//pogo.metaClass = MyMetaClass

// 可以访问字段而不调用它们的getter和setter
class SomeGroovyClass1 {
    def field1 = 'ha'
    def field2 = 'ho'

    def getField1() {
        return 'getHa'
    }

    void setField2(String field2) {
        println "xxxx $field2"
        this.field2 = 'field2'
    }
}

def sgc1 = new SomeGroovyClass1()
assert sgc1.metaClass.getAttribute(sgc1, 'field1') == 'ha'
assert sgc1.metaClass.getAttribute(sgc1, 'field2') == 'ho'

sgc1.metaClass.setAttribute(sgc1, 'field2', 'ha')
assert sgc1.field2 == 'ha'
sgc1.field2 = 'ha2'// 会调用set方法

// Groovy支持methodMissing的概念。 此方法与invokeMethod的不同之处在于，它仅在失败的方法分派的情况下被调用
class Foo {
    def methodMissing(String name, def args) {
        return "this is me"
    }

    // 对于setter方法，可以添加另一个propertyMissing定义
    def propertyMissing(String name, value) { println "no set" }

    // Groovy支持propertyMissing的概念，仅当Groovy运行时找不到给定属性的getter方法时才调用propertyMissing(String)方法
    def propertyMissing(String name) { name }
}

assert new Foo().someUnknownMethod(42l) == 'this is me'


new Foo().boo == 'boo'

// groovy.lang.GroovyInterceptable接口是用于通知Groovy运行时的扩展GroovyObject的标记接口，所有方法都应通过Groovy运行时的方法分派器机制拦截。
// 当Groovy对象实现GroovyInterceptable接口时，对任何方法的调用都会调用invokeMethod()方法
class Interception implements GroovyInterceptable {
    def definedMethod() {}

    def invokeMethod(String name, Object args) {
        'invokedMethod'
    }
}

class InterceptableTest extends GroovyTestCase {
    void testCheckInterception() {
        def interception = new Interception()

        assert interception.definedMethod() == 'invokedMethod'
        assert interception.someMethod() == 'invokedMethod'
    }
}
// 我们不能使用默认groovy方法，如println，因为这些方法被注入到所有groovy对象中，所以它们也会被拦截

// 如果我们要拦截所有方法调用，但不想实现GroovyInterceptable接口，我们可以在对象的MetaClass上实现invokeMethod()。 此方法适用于POGO和POJO
class InterceptionThroughMetaClassTest extends GroovyTestCase {
    void testPOJOMetaClassInterception() {
        String invoking = 'ha'
        invoking.metaClass.invokeMethod = { String name, Object args ->
            'invoked'
        }

        assert invoking.length() == 'invoked'
        assert invoking.someMethod() == 'invoked'
    }

    void testPOGOMetaClassInterception() {
        Entity entity = new Entity('hello')
        entity.metaClass.invokeMethod = { String name, Object args ->
            'invoked'
        }
        assert entity.build(new Object()) == 'invoked'
        assert entity.someMethod == 'invoked'
    }
}

// ExpandoMetaClass
// Groovy带有一个特殊的MetaClass，它就是ExpandoMetaClass。 它是特别的，它允许通过使用一个整洁的闭包语法动态添加或更改方法，构造函数，属性，甚至静态方法
// 每个java.lang.Class由Groovy提供，并有一个特殊的metaClass属性，它将提供对ExpandoMetaClass实例的引用。 然后，此实例可用于添加方法或更改已有现有方法的行为
// 默认情况下ExpandoMetaClass不执行继承。 要启用它，您必须在应用程序启动之前调用ExpandoMetaClass＃enableGlobally()

class Book {
    String title
}

// 方法
// 通过<<添加方法，通过=替换方法，分配一个Closure代码块添加新方法
Book.metaClass.titleInUpperCase << { -> title.toUpperCase() }
b = new Book(title: "The Stand")
assert "THE STAND" == b.titleInUpperCase()

// 添加属性
Book.metaClass.author = "Stephen King"
assert "Stephen King" == b.author

Book.metaClass.getAuthor << { -> "tephen King" }
assert "tephen King" == b.author

// 构造函数,Closure参数将成为构造函数参数。
Book.metaClass.constructor << { String title -> new Book(title: title) }
// 当添加构造函数时要小心，因为它很容易陷入堆栈溢出问题
def book = new Book('Groovy in Action')
assert book.title == 'Groovy in Action'

// 静态方法
Book.metaClass.static.create << { String title -> new Book(title: title) }
Book.create("The Stand")

// 借用方法
class MortgageLender {
    def borrowMoney() {
        "buy house"
    }
}

// 使用ExpandoMetaClass，可以使用Groovy的方法指针语法从其他类中借用方法
def lender = new MortgageLender()
Book.metaClass.buyHouse = lender.&borrowMoney
b = new Book()
assert "buy house" == b.buyHouse()

// 动态方法名称
// 由于Groovy允许使用Strings作为属性名，这反过来允许您在运行时动态创建方法和属性名。 要创建具有动态名称的方法，只需使用引用属性名称的语言特性作为字符串
def methodName = "Bob"
Book.metaClass."changeNameTo${methodName}" = { -> delegate.title = "Bob" }
b = new Book()
assert b.title == null
b.changeNameToBob()
assert b.title == 'Bob'

// 运行时发现
// 在运行时，知道在执行该方法时存在什么其他方法或属性通常是有用的。 ExpandoMetaClass在撰写本文时提供了以下方法：
//getMetaMethod
//hasMetaMethod
//getMetaProperty
//hasMetaProperty
// 你为什么不能使用反射？ 因为Groovy是不同的，它有方法是“真正的”方法，同时方法只在运行时可用。 这些有时（但不总是）表示为MetaMethods。 MetaMethod告诉你在运行时可用的方法，因此你的代码可以适应。

// GroovyObject 方法
// ExpandoMetaClass的另一个特点是它允许重写方法invokeMethod，getProperty和setProperty，所有这些都可以在groovy.lang.GroovyObject类中找到
class Stuff {
    def invokeMe() { "foo" }
}

// 覆盖invokeMethod
Stuff.metaClass.invokeMethod = { String name1, args ->
    def metaMethod = Stuff.metaClass.getMetaMethod(name1, args)
    def result
    if (metaMethod) result = metaMethod.invoke(delegate, args)
    else {
        result = "bar"
    }
    result
}
def stf = new Stuff()
assert "foo" == stf.invokeMe()
assert "bar" == stf.doStuff()

// 仅仅有if
a = 1
if (a == 1) println(1111)

// 整体来看
// groovy和java很类似，尤其定义类型也是，有些lambda，有些高级元编程特性，其他的基本都ok

// 调用自身方法
def b3 = new StringBuilder().with {
    append('foo')
    append('bar')
    return it
}

println(b3)
