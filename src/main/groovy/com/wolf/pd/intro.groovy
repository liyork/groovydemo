package com.wolf.pd

import groovy.transform.Canonical
import jdk.nashorn.internal.ir.annotations.Immutable

import static java.lang.Math.random as rand

// groovy风格循环
2.times { println it }// for循环

// 用java的类
println XmlParser.class
println XmlParser.class.superclass

// 动态，扩展
String.metaClass.isPalindrome = { ->
    delegate == delegate.reverse()
}
word = 'tattarrattat'
println "$word is a palindrome? ${word.isPalindrome()}"
word = 'Groovy'
println "$word is a palindrome? ${word.isPalindrome()}"

// GDK(Groovy JDK)
lst = ['Groovy', 'is', 'hip']
println lst.join(' ')
println lst.getClass()

// 命令行执行groovy intro

for (i in 0..2) {
    println 'ho '
}

0.upto(2) { println "$it " }// 0是Integer的一个实例,[0,2]

3.times { println "$it " }// [0,3)

0.step(10, 2) { println "$it " }

// 执行命令
def execute = "git help".execute()
println execute.getClass().name
println execute.text

println "groovy -v".execute().text
println "ls -l".execute().text

// safe-navigation操作符?.
def foo(str) {
    str?.reverse()// str不为null时才会向右执行调用
}

println foo('eval')
println foo(null)

// 异常不强制被要求捕获，可以自动向上层传递
try {
    openFile("xxx")
//} catch (FileNotFoundException ex) {
} catch (ex) {// 所有Exception，不能处理Error或Throwable
    println "Oops:" + ex
}

def openFile(fileName) {
    new FileInputStream(fileName)
}

// 静态方法使用this引用Class对象
def learn = Wizard.learn("a", 1).learn("b", 2)
println learn

class Wizard {
    def static learn(trick, action) {
        println "$trick, $action"
        this
    }
}

// 属性
class Car {
    def miles = 0// 在这个上下中声明一个属性,groovy为其创建get、set
    final year
    private age = 0
    final age1 = 0

    Car(theYear) { year = theYear }

    def getAge() {
        println "getAge called"
        age
    }

    private void setAge(age) {// 将变量设定为私有
        throw new IllegalAccessException("you're not allowed to change age")
    }

    def drive(age) { if (age > 0) age += age }
}

Car car = new Car(2000)
println "Year: $car.year"// 调用该属性的get
println "miles: $car.miles"
car.miles = 25
println "miles: $car.miles"

try {
    car.age1 = 1
} catch (groovy.lang.ReadOnlyPropertyException ex) {
    println ex.message
}


try {
    car.age = 1
} catch (IllegalAccessException ex) {
    println ex.message
}

// 存取属性方便
Calendar.instance// 代替Calendar.getInstance()
str = 'hello'
str.getClass().name// 代替str.getClass().getName(),

// 初始化javabean
class Robot {
    def type, height, width

    def access(location, weight, fragile) {
        println "Received $fragile, $weight, $location"
    }

    // 避免混乱，指定第一个形参是Map
    def access1(Map location, weight, fragile) {
        println "Received $fragile, $weight, $location"
    }
}

robot = new Robot(type: 'arm', width: 10, height: 40)
println "$robot.type, $robot.height, $robot.width"
// 如果第一个是Map，可以将这个映射中的kv展开
robot.access(x: 30, y: 20, z: 10, 50, true)
robot.access(50, true, x: 30, y: 20, z: 10)// 修改参数顺序,谨慎使用
robot.access1(50, true, x: 30, y: 20, z: 10)// 避免混乱

// 可选形参
def log(x, base = 10) {
    Math.log(x) / Math.log(base)
}

println log(1024)
println log(1024, 2)

// 末尾的数组形参视作可选
def task(name, String[] details) {
    println "$name - $details"
}

task 'Call', '1111'
task 'Call', '1111', '222'
task 'Call'

// 返回多个结果，返回一个数组，多个变量以逗号分割接收
def splitName(fullName) { fullName.split(' ') }

def (firstName, lastName) = splitName('James Bod')
println "$lastName, $firstName"

// 交换变量
def name1 = 'a'
def name2 = 'b'
println "$name1 and $name2"
(name1, name2) = [name2, name1]
println "$name1 and $name2"

// 指定多赋值中定义的单个变量的类型
def (String cat, String mouse) = ['Tom', 'Jerry', 'Spike', 'Tyke']// 多余字段丢弃
println "$cat and $mouse"

def (first, second, third) = ['Tom', 'Jerry']
println "$first, $second, $third"// 少值则为null，若是int third的基本类型则会出现异常

// 根据上下文，自动把表达式计算为布尔值，尝试推断
str = 'hello'
if (str) {// 若在需要布尔值的地方放了一个对象引用，则groovy会检查该引用是否null，将null视为false
    println 'hello'
}

// 若对象引用不为null，表达式的结果海域对象的类型有关，若是集合则检查集合是否为空，groovy内建的布尔求值约定
// 也可以通过实现asBoolean()实现自己的布尔转换
lst0 = null
println lst0 ? 'lst0 true' : 'lst0 false'
lst1 = [1, 2, 3]
println lst1 ? 'lst1 true' : 'lst1 false'
lst2 = []
println lst2 ? 'lst2 true' : 'lst2 false'

// 操作符重载，每个操作符都会映射到一个标准方法
for (ch = 'a'; ch < 'd'; ch++) {// 通过++操作符实现从字符a到c，++映射的是String.next()方法
    println ch
}
// 简洁for
for (ch in 'a'..'c') {
    println ch
}
// 添加
lst = ['hello']
lst << 'there'// 转换成leftShitf方法
println lst

// 自己重载
class ComplexNumber {
    def real, imaginary

    def plus(other) {
        new ComplexNumber(real: real + other.real, imaginary: imaginary + other.imaginary)
    }

    String toString() {
        "$real ${imaginary > 0 ? '+' : ''} ${imaginary}i"
    }
}

c1 = new ComplexNumber(real: 1, imaginary: 2)
c2 = new ComplexNumber(real: 4, imaginary: 1)
println c1 + c2
// 应该只重载能使事物变得显而易见的操作符

// 自动装箱
int val = 5// 尽管是int，但创建的是java.lang.Integer类的实例,groovy会依据该实例的使用方式来决定其存储为int还是Integer
println val.getClass().name

// 实现了Iterable接口的对象可以用for-each
String[] greetings = ['hello', 'hi', 'hwody']
for (String greet : greetings) {// 用:需要指定类型
    println greet
}
for (greet in greetings) {// 用in不用指定类型
    println greet
}

// enum
enum CoffeeSize {
    SHORT, SMALL, MEDIUM, LARGE, MUG
}

def orderCoffee(size) {
    println "received"
    switch (size) {
        case [CoffeeSize.SHORT, CoffeeSize.SMALL]:
            println "you're health concious"
            break
        case CoffeeSize.MEDIUM..CoffeeSize.LARGE:
            println "you got be a paor"
            break
        case CoffeeSize.MUG:
            println "your should try caffi"
            break
    }
}

orderCoffee(CoffeeSize.SMALL)
orderCoffee(CoffeeSize.LARGE)
for (size in CoffeeSize.values()) {
    println size
}

// 枚举有构造
enum Methodologies {
    Evo(5), XP(21), Scrum(30);
    final int daysInIteration

    Methodologies(days) { daysInIteration = days }

    def iterationDetails() {
        println "$this recommeds $daysInIteration days for iteration"
    }
}

for (methodology in Methodologies.values()) {
    methodology.iterationDetails()
}

// 变长参数
def receiveVarArgs(int a, int ... b) {
    println "yor passed $a and $b"
}

def receiveArray(int a, int[] b) {// 以数组作为末尾形参
    println "your passed $a and $b"
}

receiveVarArgs(1, 2, 3)
receiveArray(1, 2, 3)

// groovy将方括号中的值看做ArrayList的实例
// 要发送数组，需要定义一个指向该数组的引用，或者用as操作符
//receiveArray(1, [2, 3, 4, 5])// 报错
int[] values = [2, 3, 4, 5]
receiveArray(1, values)
receiveArray(1, [2, 3, 4, 5] as int[])// 指定类型改变语义

// 使用引入别名
double value = rand()
println value.getClass().name

// 自动生成toString注解
@Canonical(excludes = "lastName, age")
class Person {
    String firstName
    String lastName
    int age
}

def sara = new Person(firstName: "Sara", lastName: "Walker", age: 49)
println sara

// 使用委托
// 编译时，会检查Manager类，若该类中没有被委托类中的方法，就把这些方法从被委托类中引入进来，
// 从上向下，先引入Expert的analyze，而从Worker中把work/writeReport引入
class Worker {
    def work() { println 'get work done' }

    def analyze() { println 'analyze..' }

    def writeReport() { println 'get report written' }
}

class Expert {
    def analyze() { println "expert analysis.." }
}

class Manager {
    @Delegate
    Expert expert = new Expert()
    @Delegate
    Worker worker = new Worker()
}

def bernie = new Manager()
bernie.analyze()
bernie.work()
bernie.writeReport()

// 不可变，标记在类上时，字段会为final
@Immutable
class CreditCard {
    String cardNumber
    int creditLimit
}

println new CreditCard()

// 懒加载
class Heavy {
    def size = 10

    Heavy() { println "Create Heavy with $size" }
}

class AsNeeded {
    def value
    @Lazy
    Heavy heavy1 = new Heavy()
    @Lazy
    Heavy heavy2 = {
        new Heavy(size: value)// 由于没有构造方法，而字段是动态设定的，所以先调用默认构造，那时是size=10，构造完成，初始化字段sieze=100
    }()

    AsNeeded() { println "Created AsNeeded" }
}

// groovy不仅推迟了创建，还标记volatile，并确保创建期间是线程安全的
def asNeeded = new AsNeeded(value: 100)
println asNeeded.heavy1.size// 字段使用时才会触发构造
println asNeeded.heavy1.size// 重复使用
println asNeeded.heavy2.size

// 去掉new
@Newify([Person, CreditCard])
def fluentCreate() {
    println Person.new(firstName: "john", lastName: "doe", age: 20)
    println Person(firstName: "josh", lastName: "doe", age: 20)
    println CreditCard()
}

fluentCreate()

// 单例
@Singleton(lazy = true, strict = false)
class TheUnique {
    private TheUnique() { println "Instance created" }

    def hello() { println 'hello' }
}

TheUnique.instance.hello()
TheUnique.instance.hello()


