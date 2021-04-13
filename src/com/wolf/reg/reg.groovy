package com.wolf.reg

import java.util.regex.Matcher

// 辅助符号
//辅助符号  等价符号  描述
//\d  [0-9]  数字
//\D  [^0-9]  非数字
//\w  匹配字母或者数字或者下划线或汉字
//\W  [^a-zA-Z0-9]  非Word字符
//\s  [\t\n\f\r\v]  空白字符
//\S  [^\t\n\f\r\v]  非空白字符
//\b :单词的开始或者结尾

// 元字符
//元字符  描述
//.  匹配除了换行符以外的任意字符
//^  匹配行的开始部分  'Groovy' ==~ '^Gro'
//$  匹配行的结尾部分  'Groovy' ==~ 'vy$'
//*  匹配*前的(字符或正则表达式)出现0到多次  'Groovy' ==~ 'o*'
//+  匹配+前的(字符或正则表达式)出现1或多次  'Groovy' ==~ 'r+'
//?  匹配?前的(字符或正则表达式)出现0或1次  'Groovy' ==~ 'q?'
//[]  匹配[]中字符集中的任意字符  'lxt008' ==~ '[a-z0-9]*8'
//{n}  匹配前一个规则，重复n次  'lxt008' ==~ 'l[a-z0-9]{4}8'
//\  转义符  '$' ==~ '\\$'  // \本身就和一个字符一起当成一个特定符号，这里需要表达的是\\将\转义成\然后再和$转义成$字符本身
//|  "或"选择符  'ten' ==~ 't(a|e|i)n'
//()  将括号内表达式封装  'ababc' ==~ '(ab)*c'

import java.util.regex.Pattern

// 创建一个Pattern对象
def pattern = ~/foo/
assert pattern instanceof Pattern
def matcher = pattern.matcher("foo")
println matcher.getClass()
assert matcher.matches()
assert !pattern.matcher("foobar").matches()

// =~创建一个Matcher对象，使用等号右边的模式对等号左边字符串进行编译并调用.matcher得到Matcher对象
def a = "foobar" =~ ~/foo/
//println a// matcher
println a.matches()

def b = 'lxt' =~ 'l.t'
println b.matches()

// ==~ 字符是否匹配模式
println "2009" ==~ /\d+/

// 捕获，m[0]是List，[0]表示匹配的所有，[1]表示匹配第一个
def m = "foobarfoo" =~ /o(b.*r)f/
assert m[0] == ["obarf", "bar"]

println 'hi'.matches('hi')
println '111'.matches('\\d{3}')
println '11as11'.matches('\\d{2}.*\\d{2}')
println '11as11'.matches('.*\\d{2}')// 贪婪
println '11as11'.matches('.*')

// 2.
// 有^$和没有的有什么区别?
def s = "She sells sea shells at the sea shore of seychelles"
matcher = s =~ /s.a/
assert matcher instanceof Matcher
// return true if, and only if, the entire region sequence matches this matcher's pattern  一定是全都匹配才可以
println matcher.matches()

def find = matcher.find()
println("find $find")

// 本次捕获的组
def groupCount = matcher.groupCount()
println("groupCount $groupCount")

// returns a List representing the first match of the regular expression in the string
def object = matcher[0]
println("matcher[0] $object")

def first = matcher.replaceFirst("xxx")
println first

// 3. matcher的使用
matcher = "eat green cheese" =~ "e+"// 最大贪婪

println "matcher.find and group start"
while (matcher.find()) {
    println matcher.group(0)
}
println "matcher.find and group end"
matcher.reset()

assert "ee" == matcher[2]
assert ["ee", "e"] == matcher[2..3]
println matcher[0..3]
assert ["e", "ee"] == matcher[0, 2]// 0和2
assert ["e", "ee", "ee"] == matcher[0, 1..2]// 0和1和2

matcher = "cheese please" =~ /([^e]+)e+/
assert ["chee", "ch"] == matcher[0]// 第一个匹配的是chee，捕获的是ch
assert ["se", "s"] == matcher[1]// 第二个匹配的是se，捕获的是s
assert [" ple", " pl"] == matcher[2]
assert [["chee", "ch"], [" ple", " pl"], ["ase", "as"]] == matcher[0, 2..3]

matcher.each { println it }
matcher.reset()
assert matcher.collect { it } == [["chee", "ch"], ["se", "s"], [" ple", " pl"], ["ase", "as"]]

assert ["foo", "moo"] == ["foo", "bar", "moo"].grep(~/.*oo$/)
assert ["foo", "moo"] == ["foo", "bar", "moo"].findAll { it ==~ /.*oo/ }

// 匹配字符串中的每一个大写单词
assert "It Is A Beautiful Day!" ==
        ("it is a beautiful day!".replaceAll(/\w+/,
                { it[0].toUpperCase() + ((it.size() > 1) ? it[1..-1] : '') }))

// 让非首字母的单词小写：
assert "It Is A Very Beautiful Day!" ==
        ("it is a VERY beautiful day!".replaceAll(/\w+/,
                { it[0].toUpperCase() + ((it.size() > 1) ? it[1..-1].toLowerCase() : '') }))

// 反向引用
def replaced = "abc".replaceAll(/(a)(b)(c)/, '$1$3')
println replaced

// 关于如何使用Pattern参考，violin的RegexTest

// 截取>后面的端口号
matcher = 'Server is listening on [ \'any\' <ipv6> 1433].' =~ /.*>\s([0-9]+).*$/
// 1
//println matcher[0]
//def count = matcher.getCount()
//println "Matches = ${count}"
//for (i in 0..<count) {
//    println matcher[i]
//}
// 2
// 可见，应该上面的动作就是下面的一样
while (matcher.find()) {
    println "${matcher.group()}, ${matcher.group(1)}"
}

println 'abc' != '中文'

// (?i) 表示所在位置右侧的表达式开启忽略大小写模式
def ignoreMatcher = 'aBc' =~ /(?i)abc/
println ignoreMatcher.matches()

// (?s) 表示所在位置右侧的表达式开启单行模式。
// 更改句点字符 (.) 的含义，以使它与每个字符（而不是除 \n 之外的所有字符）匹配。
// 注意：(?s)通常在匹配有换行的文本时使用
matcher = 'aBc\n' =~ /(?s).*/
println matcher.matches()

// 匹配每行开头的大写单词：
def before = '''
apple
orange
y
banana
'''

def expected = '''
Apple
Orange
Y
Banana
'''

// (?m) 表示所在位置右侧的表示式开启指定多行模式,只有在正则表达式中涉及到多行的“^”和“$”的匹配时，才使用Multiline模式
assert expected == before.replaceAll(/(?m)^\w+/,
        { it[0].toUpperCase() + ((it.size() > 1) ? it[1..-1] : '') })

// 上面的匹配模式可以组合使用，比如(?is),(?im)




