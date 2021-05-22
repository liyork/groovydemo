package com.wolf.inaction
// example boolean test evaluations
assert true
assert !false
// Matchers must match
assert ('a' =~ /./)
assert !('a' =~ /b/)
// collections must be non-empty
assert [1]
assert ![]
// maps must be non-empty
assert ['a': 1]
assert ![:]
// strings must be non-empty
assert 'a'
assert !''
// numbers(any type) must be nonzero
assert 1
assert 1.1
assert 1.2f
assert 1.3g
assert 2L
assert 3G
assert !0
// any other value must be non-null
assert new Object()
assert !null

// 注意==和=
// 在if语句中的顶级表达式不能是一个赋值表达式

// the if statement in action
if (true) assert true
else assert false

if (1) {
    assert true
} else {
    assert false
}
if ('non-empty') assert true
else if (['x']) assert false
else assert false

if (0) assert false
else if ([]) assert false
else assert true

// the conditional operator
def result = (1 == 1) ? 'ok' : 'failed'
assert result == 'ok'
result = 'some string' ? 10 : ['x']
assert result == 10

// general switch appearance is like java or c
def a = 1
def log = ''
switch (a) {
    case 0: log += '0'// 需要break
    case 1: log += '1'
    case 2: log += '2'; break
    default: log += 'default'
}
assert log == '12'

// 如果一个类型实现了isCase方法，那么这个类可以作为switch的分类器

// advanced switch and mixed classifiers
switch (10) {
    case 0: assert false; break
    case 0..9: assert false; break
    case [8, 9, 11]: assert false; break
    case Float: assert false; break// type case
    case { it % 3 == 0 }: assert false; break// closure case
    case ~/../: assert true; break// regular expression case
    default: assert false; break
}
// 在集合的grep也使用isCase方法，如Collection.grep(classifier)返回符合分类器的一个集合列表

// example while loops
def list = [1, 2, 3]
while (list) {
    list.remove(0)
}
assert list == []

while (list.size() < 3) list << list.size() + 1
assert list == [1, 2, 3]

// multiple for loop examples
def store = ''
for (String i in 'a'..'c') store += i// typed, over string range, no braces
assert store == 'abc'
store = ''
for (i in [1, 2, 3]) {// untyped, over list as collection, braces
    store += i
}
assert store == '123'
def myString = 'Equivalent to java'
store = ''
for (i in 0..<myString.size()) {// untyped, over half-exclusive IntRange, braces
    store += myString[i]
}
assert store == myString
store = ''
for (i in myString) {
    store += i
}
assert store == myString

// simple break and continue
a = 1
while (true) {
    a++
    break
}
assert a == 2

for (i in 0..10) {
    if (i == 0) continue
    a++
    if (i > 0) break
}
assert a == 3

// throw,try,catch, and finally
def myMethod() {// 可以不声明throws IllegalArgumentException
    throw new IllegalArgumentException()
}

log = []
try {
    myMethod()
} catch (Exception e) {
    log << e.toString()
} finally {
    log << 'finally'
}
assert log.size() == 2

