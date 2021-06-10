package com.wolf.pd
// 双引号和单引号都是String类的实例
println 'he said, "that is groovy"'// 双引号可以放到字符串中

def a = 'a' as char// 字符
println a// 若有任何方法调用需要的话，可能隐式地创建Character对象

// 单引号创建的String看做一个字面量值。
str = 'A string'
println str.getClass().name
// 单引号内不会计算表达式
value = 25
println 'The value is ${value}'

// 不可变
str = 'hello'
println str[2]
try {
    str[2] = '!'
} catch (Exception ex) {
    println ex
}

// 用""创建表达式
value = 12
println "He paid \$${value} for that."// 用转义字符\打印$

// 惰性求值，lazy evaluation
what = new StringBuilder('fence')
text = "Text cow jumped over the $what"
println text

what.replace(0, 5, "moon")
println text// 值也变了，看来""中的$what内容是对what的引用而不是一个单纯字符串字面量值了

// GString,groovy的字符串
def printClassInfo(obj) {
    println "class: ${obj.getClass().name}"
    println "superclass: ${obj.getClass().superclass.name}"
}

val = 125
printClassInfo("The Stock closed at ${val}")// GString
printClassInfo(/The Stock closed at ${val}/)// GString
printClassInfo("This is a simple String")// String

// GString惰性求值的问题
what = new StringBuilder('fence')
text = "Text cow jumped over the $what"// text这个GString实例中包含了变量what
println text// 打印text表达式时，会在what上调用toString求值。

what.replace(0, 5, "moon")
println text
// 修改不了值
price = 111
company = 'Google'
quote = "Today $company stock closed at $price"
println quote

stocks = [Apple: 222, Microsoft: 333]
stocks.each { key, value ->
    company = key
    price = value
    println quote// 由于上面quote引用的是company字符串，是不可变的，所以这里修改不了值
}

// GString表达式求值过程，若其中包含一个变量，该变量的值会被简单地打印到一个Writer，通常是StringWriter。
// 若GString中包含一个闭包，该闭包就会被调用。若闭包接收一个参数，GString会把Writer对象传给闭包。
companyClosure = { it.write(company) }
priceClosure = { it.write("$price") }
quote = "Today ${companyClosure} stock closed at ${priceClosure}"
stocks.each { key, value ->
    company = key
    price = value
    println quote// 当表达式需要求值/打印时，GString会调用闭包。
}
// 重构上面代码：若闭包没有参数，可以去掉it
companyClosure = { -> company }
priceClosure = { -> "$price" }
quote = "Today ${companyClosure} stock closed at ${priceClosure}"
stocks.each { key, value ->
    company = key
    price = value
    println quote
}
// 重构上面代码：不想单独定义闭包，希望代码是自包含的
quote = "Today ${-> company} stock closed at ${-> price}"
stocks.each { key, value ->
    company = key
    price = value
    println quote
}

// 多行字符串，使用'''...'''定义多行字面常量
memo = '''ssss
dddd
qqqq'''
println memo
price = 251
message = """aaa
bbb${price}"""
println message

// 字符串便捷方法
str = "It's a rainy day in Seattle"
println str
str -= "rainy"// minus
println str

for (str in 'hela'..'held') {
    println "$str"
}

// 正则
obj = ~"hello"// 映射到bitwiseNegate，创建Pattern
println obj.getClass().name

pattern = "(G|g)roovy"
text = 'Groovy is Hip'
if (text =~ pattern) {// =~执行RegEx部分匹配，返回Matcher对象，对matcher的布尔求职处理与java不同，只要至少有一个匹配就true
    println "match"
} else {
    println "no match"
}
if (text ==~ pattern) {// ==~执行RegEx精确匹配
    println "match"
} else {
    println "no match"
}

// 多个匹配则matcher包含一个匹配的数组
matcher = 'Groovy is groovy' =~ /(G|g)roovy/
println "size of matcher is ${matcher.size()}"
println "with elements ${matcher[0]} and ${matcher[1]}"

// replace
str = 'Groovy is groovy, really groovy'
println str
result = (str =~ /groovy/).replaceAll('hip')
println result

// 打印$
println """
${'$'}9.99
"""



















