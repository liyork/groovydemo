assert true
assert 1 == 1// ==号调用了对象的equals
def x = 1// def意思是动态的类型
assert x == 1
def y = 1; assert y == 1

// 脚本中使用Book类
//Book gina = new Book('groovy in action')// 注释放置idea报错，不过只要保证Book类在classpath上，这里就可以正常执行
assert gina.getTitle() == 'groovy in action'
assert getTitleBackwards(gina) == 'noitca ni yvoorg'

String getTitleBackwards(book) {
    title = book.getTitle()
    return title.reverse()
}

// 定义Book1类为GroovyBean
class Book1 {
    String title
}

def groovyBook = new Book1()
groovyBook.setTitle('groovy conquers the world')
assert groovyBook.getTitle() == 'groovy conquers the world'// 通过显示的方法调用使用属性
assert groovyBook.title == 'groovy conquers the world'// 通过groovy的快捷方式使用属性

// 在双引号的字符串中允许使用占位符，占位符在需要的时候将自动解析，这是一个GString类型
def nick = 'Gina'
def book = 'Groovy in Action'
assert "$nick is $book" == 'Gina is Groovy in Action'

// 正则
assert '123456' =~ /\d+/
assert 'xxxxx' == '12345'.replaceAll(/\d/, 'x')

// 数字也是对象
def x1 = 1// 是java.lang.Integer的实例
def y1 = 2
assert x1 + y1 == 3
assert x1.plus(y1) == 3
assert x1 instanceof Integer

// Lists
def roman = ['', 'I', 'II', 'III', 'IV', 'V', 'VI', 'VII']
assert roman[4] == 'IV'
roman[8] = 'VIII'// 扩张列表
assert roman.size() == 9

// map
def http = [
        100: 'CONTINUE',
        200: 'OK',
        400: 'BAD REQUEST'
]
assert http[200] == 'OK'
http[500] = 'INTERNAL SERVER ERROR'
assert http.size() == 4

// range
def x2 = 1..10
assert x2.contains(5)
assert x2.contains(15) == false
assert x2.size() == 10
assert x2.from == 1
assert x2.to == 10
assert x2.reverse() == 10..1

// 代码块：闭包
[1, 2, 3].each { entry -> println entry }

// control
if (false) assert false
if (null) {
    assert false
} else {
    assert true
}
def i = 0
while (i < 10) {
    i++
}
assert i == 10
def clinks = 0
for (remainingGuests in 0..9) {
    clinks += remainingGuests
}
assert clinks == (10 * 9) / 2
def list = [0, 1, 2, 3]
for (j in list) {
    assert j == list[j]
}
list.each() { item -> assert item == list[item] }
switch (3) {
    case 1: assert false; break
    case 3: assert true; break
    default: assert false
}







