package com.wolf.inaction

import java.util.regex.Matcher

// operator override
class Money {
    private int amount
    private String currency

    Money(amountValue, currencyValue) {
        amount = amountValue
        currency = currencyValue
    }

    @Override
    boolean equals(Object other) {// 重写==操作符
        if (null == other) return false
        if (!(other instanceof Money)) return false
        if (currency != other.currency) return false
        if (amount != other.amount) return false
        return true
    }

    @Override
    int hashCode() {
        amount.hashCode() + currency.hashCode()
    }

    Money plus(Money other) {// 实现+操作符
        if (null == other) return null
        if (other.currency != currency) {
            throw new IllegalAccessError(
                    "cannot add $other.currency to $currency"
            )
        }
        return new Money(amount + other.amount, currency)
    }

    Money plus(Integer more) {
        return new Money(amount + more, currency)
    }
}

def buck = new Money(1, 'USD')
assert buck == new Money(1, 'USD')// 调用重写的==操作符
assert buck + buck == new Money(2, 'USD')// 调用+操作符
assert buck + 1 == new Money(2, 'USD')// 调用+操作符

// GString
me = 'xx'
you = 'xx'
lin = "me $me - you $you"
assert lin == "me xx - you xx"
println lin.strings

def date = new Date(0)
out = "xx $date.year month $date.month day $date.date"
out = "Date is ${date.toGMTString()}"// 使用花括号表示的完整语法
assert out instanceof GString
sql = """// 多行的GString
xxxxcvxcv
qqqq $date.year
"""
out = "my 0.02\$"
assert out == 'my 0.02$'

// strings
greeting = 'Hello Groovy!'
assert greeting.startsWith('Hello')
assert greeting.getAt(0) == 'H'
assert greeting[0] == 'H'
assert greeting.indexOf('Groovy') >= 0
assert greeting.contains('Groovy')
assert greeting[6..11] == 'Groovy'
assert 'Hi' + greeting - 'Hello' == 'Hi Groovy!'
assert greeting.count('o') == 3
assert 'x'.padLeft(3) == '  x'
assert 'x'.padRight(3, '_') == 'x__'
assert 'x'.center(3) == ' x '
assert 'x' * 3 == 'xxx'

// StringBuffer
greeting = 'Hello'
greeting <<= ' Groovy'
assert greeting instanceof java.lang.StringBuffer
greeting << '!'
assert greeting.toString() == 'Hello Groovy!'
greeting[1..4] = 'i'
assert greeting.toString() == 'Hi Groovy!'

// reg
// regular expresison GStrings
assert "abc" == /abc/
assert "\\d" == /\d/
def reference = "hello"
assert reference == /$reference/
assert "\$" == /$/

twister = 'she sells sea shells at the sea shore of seychelles'
assert twister =~ /s.a/
finder = (twister =~ /s.a/)
assert finder instanceof java.util.regex.Matcher
assert twister ==~ /(\w+ \w+)*/
WORD = /\w+/
matches = (twister ==~ /($WORD $WORD)*/)
assert matches instanceof java.lang.Boolean
assert (twister ==~ /s.e/) == false// 必须全匹配才true
wordsByx = twister.replaceAll(WORD, 'x')
assert wordsByx == 'x x x x x x x x x x'
words = twister.split(/ /)
assert words.size() == 10
assert words[0] == 'she'

// Working on each match of a patter
myFairStringy = 'The rain in Spain stays mainly in the plain!'
BOUNDS = /\b/
rhyme = /$BOUNDS\w*ain$BOUNDS/
found = ''
myFairStringy.eachMatch(rhyme) { match ->
    found += match + ' '
}
assert found == 'rain Spain plain '
found = ''
(myFairStringy =~ rhyme).each { match ->
    found += match + ' '
}
assert found == 'rain Spain plain '
choze = myFairStringy.replaceAll(rhyme) { it - 'ain' + '___' }
assert choze == 'The r___ in Sp___ stays mainly in the pl___!'

matcher = 'a b c' =~ /\S/
assert matcher[0] == 'a'
assert matcher[1..2] == ['b', 'c']
assert matcher.count == 3

//分组，返回数组
matcher = 'a:1 b:2 c:3' =~ /(\S+):(\S+)/
assert matcher.hasGroup()
assert matcher[0] == ['a:1', 'a', '1']// 完全部分，匹配的各部分
('xy' =~ /(.)(.)/).each { all, x, y ->
    assert all == 'xy'
    assert x == 'x'
    assert y == 'y'
}
println Matcher.getLastMatcher()[0]

assert (~/..../).isCase('bear')
switch ('bear') {
    case ~/..../: assert true; break
    default: assert false
}
beasts = ['bear', 'wolf', 'tiger', 'regex']
assert beasts.grep(~/..../) == ['bear', 'wolf']

// GDK methods on numbers
def store = ''
4.times {
    store += 'x'
}
assert store == 'xxxx'
store = ''
1.upto(5) {
    store += it
}
assert store == '12345'
store = ''
2.downto(-2) {
    store += it + ' '
}
assert store == '2 1 0 -1 -2 '
store = ''
0.step(0.5, 0.1) {
    store += it + ' '
}
assert store == '0 0.1 0.2 0.3 0.4 '