// 11 字符串和字符字面量
// 单引号创建的字面量属于String类型对象
// 双引号创建的字面量可能是String或GString("Groovy字符串"的简称)对象，由字面量中是否有插值决定
// 通常，方法声明了形参的类型，Groovy会自动转换成GString和String。
// 小心那些形参谓Object的，需要检查他们的实际类型
assert 'c'.getClass() == String
assert "c".getClass() == String
assert "c${1}".getClass() in GString

// a.两种字符串类型，普通字符串（java.lang.String）和插值字符串（groovy.lang.GString）
// b.普通字符串使用单引号，不能占位符
println 'hello'
// 插值字符串使用双引号，可以有占位符
def name = '张三'
println "hello $name"

// 三单引号，可以保留文本的换行及缩进格式
def strippedFirstNewline = '''line one
            line two 
                    line three
'''
println strippedFirstNewline

// substring
def log = "Exception on saving user with username:johntheripper"
def username = log.substring(log.lastIndexOf(":") + 1, log.length())
println username
username = log.subSequence(log.lastIndexOf(":") + 1, log.length())
println username

// getAt同[]
def text1 = "My last character will be removed soon"
assert text1[3..-1] == "last character will be removed soon"// 正向，从0开始，逆向，从-1开始
assert text1[0..-2] == "My last character will be removed soo"
def a = "abc"
assert a[-1..0] == "cba"// 从后到前
assert (text1 - "last") == "My  character will be removed soon"// 减字符串,调用minus函数
