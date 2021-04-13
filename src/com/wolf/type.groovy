package com.wolf

import org.codehaus.groovy.runtime.GStringImpl

// Groovy是一个解释执行的语言。
// 在解释执行的Groovy文件中，有如下特点：
// 文件运行在一个Script对象中。

//Groovy 中一切皆对象，这意味着 Groovy 中不存在基本类型。
int i = 1
def j = 2
// 在 Groovy 中基本类型是通过包装类实现的
println i.class
println j.class

// 数字类型
// 执行 n 次
def store = ""
3.times { store += "x" }
assert store == "xxx"

store = ""
// n.upto(m){} n到m每次+1
1.upto(3) { n -> store += n }
assert store == "123"

store = ""
// n.downto(m){} ,n~m，每次-1
2.downto(-2) { n -> store += n }
assert store == "210-1-2"

store = ""
// n.step(a,b){} 从 n 开始，每次步长 b，直到等于 a
0.step(0.5, 0.1) { n -> store += n + "," }
assert store == "0,0.1,0.2,0.3,0.4,"


// Groovy 中可以使用 Java 的 String 和 Groovy 的 GString 表示字符串。
//原则
//当没有明确指明类型时，字符串都会被推断为 String 类型
//String 可以用单引号或双引号声明，但是 GString 只能以双引号声明
//只有 GString 支持使用引用符 ${}
//三引号 """ 或 ''' 可以定义跨行的字符串，即按格式原样输出
//双引号内部可以使用单引号，单引号内部可以使用双引号

def x = 2
def singleQuote = 'abc'
def doubleQuotes = "abc"
def singleQuote2 = 'abc${x}'
def doubleQuotes2 = "abc${x}"
assert singleQuote.class == String.class
assert doubleQuotes.class == String.class
assert singleQuote2.class == String.class
assert doubleQuotes2.class == GStringImpl

// 常用方法
def str = 'Groovy&Grails&lxt008'
println str[4]// 从0开始
println str[-1]
println str[1..2]
println str[1..<3]// <不包含
println str[4, 1, 6]
println 'a' == 'a'
println 'a' <=> 'a'
println 'a'.compareTo('a')
println 'a' - 'a'
println 'a' + 'a'
println 'a' * 3// 重复

str = 'Groovy'
println str.center(11)// 当前str居中，剩余内容用空格占位
println str.center(2)
println str.center(11, '=')
println str.count('o')
println str.leftShift(' world')
println str << ' world'
println str.minus('vy')
println str - 'vy'// 排除字符

str = 'Groovy'
println str.next()// 增长最后一个字符
println str.previous()

println str.padLeft(4)
println str.padLeft(11)// 多余的字符用空格占位
println str.padLeft(11, "=")

println str.replaceAll('[a-z]') { ch -> ch.toUpperCase() }
println '123'.toDouble()
println '123'.toList()

str = "Groovy Grails&lxt"
println str.tokenize()// 默认空格分割
println str.tokenize('&')
println str.tokenize().getClass().getName()// ArrayList
println str.tokenize("t").getClass().getName()
println str.split("t").getClass().getName()// Array

// 类型推断
String value = "Hello world"
// 使用def定义变量，具体类型由Groovy根据右边的值进行推断
def val = "Hello world"
println val.class

// Groovy 支持动态类型和静态类型
// 动态类型
def dynamicDate = new Date()
// 静态类型，不能再改变类型了
Date staticDate = new Date()

// 作用域
// Groovy 类的作用域同 Java

// Groovy 脚本
// 绑定域：脚本内的全局作用域，相当于该脚本对象的成员变量。如果没有定义过变量(可以直接使用或仅仅初始化但未声明)，其作用域即是绑定域
// 本地域：脚本内的代码块。如果是定义过的变量，其作用域就是本地域
String hello = "hello"// 定义变量，作用域是本地域
def world = "world" // 定义变量，作用域是本地域

helloworld = "hello world"// 全局变量，作用域是绑定域

// 脚本中声明的方法访问不了本地域
void check() {
//    println hello// 不能使用
//    println workld
    println helloworld
}

check()

a = 1// 全局

void testVarScope() {
    a = 111
    def b = 222
}

println a
//println b// 不可访问
testVarScope()
println a

//块内定义，若不使用def,则全局可见
if (true) {
    def email = "sky@gmail.com"
    //1 email = "sky@gmail.com"
    println "in code block email=$email"
}
//println "out of block email=$email" // 上面1中没有打开，这里不可访问

//没有用def定义，可以在binding.variables中出现
num = 5
assert binding.variables.num == 5
//用def定义
def name = "sky"
println binding.variables.name

// 是否使用def定义变量区别：
//1）如果变量用def，变量是局部变量。变量的作用域仅仅限于run方法内部，本run局部变量不能被其他方法所能访问。--可见脚本中的代码除了方法都会被放到run函数中
//2）如果变量未用def定义，就会放入脚本绑定的变量。绑定的变量在其他方法里是可见的，绑定变量在脚本需要与外部程序交互时特别有用。
// 生成了type这个类，public class type extends Script ，一般内容都放到了run，方法被放到类的和run同级别处了，所以两者交互只能借助binding了，即不声明def




