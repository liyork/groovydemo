package com.wolf
// Lists，区别于Array仅仅是左边定义部分，array用[]String
def numbers = []
numbers.add(0)
numbers.get(0)
numbers[0]
assert numbers instanceof List
assert numbers.class instanceof Class
//assert numbers.size() == 3

[1, 2].each {
    println "item: $it"
}

('a'..'c').eachWithIndex { it, int i ->
    println "$i: $it"
}

['a', null, 'c'].eachWithIndex { it, int i ->
    println "$i: $it"
}

assert [1, 2].collect { it * 2 } == [2, 4]

assert [1, 2, 3].find { it > 1 } == 2// 找出第一个符合的
a = [1, 2, 3].find { it > 4 }
println "find =>a:$a"// 未找到返回null
assert [1, 2, 3].findAll { it > 1 } == [2, 3]// 找出所有符合的
assert ['a', 'b', 'c', 'd', 'e'].findIndexOf {// 找出符合条件的第一个元素的index
    it in ['c', 'e', 'g']
} == 2

assert ['a', 'b', 'c', 'd', 'c'].indexOf('c') == 2
assert ['a', 'b', 'c', 'd', 'c'].indexOf('z') == -1
assert ['a', 'b', 'c', 'd', 'c'].lastIndexOf('c') == 4

assert [1, 2, 3].every { it < 5 }// 每个都符合则true
assert ![1, 2, 3].every { it < 3 }
assert [1, 2, 3].any { it > 2 }

assert [1, 2, 3, 4, 5, 6].sum() == 21
assert ['a', 'b'].sum {
    it == 'a' ? 1 : it == 'b' ? 2 : 0
} == 3

assert [].sum(1000) == 1000
assert [1, 2, 3].sum(1000) == 1006

assert [1, 2, 3].join('-') == '1-2-3'
assert [1, 2, 3].inject('counting: ') { str1, item ->
    str1 + item
} == 'counting: 123'

[1, 2, 3].max() == 3
['abc', 'z'].max { it.size() } == 'abc'

Comparator mc = { a11, b11 -> a11 == b11 ? 0 : (a11 < b11 ? -1 : 1) }
[1, 2].max(mc) == 2

def list = []
assert list.empty
list << 5
assert list.size() == 1
list << 1 << 2

// 重要的是List上的+运算符不会改变List本身。 与<<相比，+运算符会创建一个新的列表，这通常不是你想要的，并可能导致性能问题

[1, 2].remove(0)
[1, 2].clear()

assert 1 in [1, 2]

[1, 2].sort()
['1', '2'].sort {
    it.size()
}

Comparator mc2 = { a22, b22 -> a22 == b22 ? 0 : Math.abs(a22) < Math.abs(b22) ? -1 : 1 }
Collections.sort([1, 2], mc)

// 这里退出是在clousure中，不会影响整体
(1..3).each {
    if (it == 2) {
        return
    }
    println(it)
}
println(111)