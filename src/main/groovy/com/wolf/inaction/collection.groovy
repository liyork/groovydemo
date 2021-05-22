package com.wolf.inaction
// range
assert (0..10).contains(0)
assert (0..10).contains(5)
assert (0..10).contains(10)
assert !(0..10).contains(-1)
assert !(0..10).contains(11)
assert (0..<10).contains(9)
assert !(0..<10).contains(10)
def a = 0..10
assert a instanceof IntRange
assert a.contains(5)
a = new IntRange(0, 10)
assert a.contains(5)
assert !(0.0..1.0).contains(0.5)
def today = new Date()
def yesterday = today - 1
assert (yesterday..today).size() == 2
assert ('a'..'c').contains('b')
def log = ''
for (element in 5..9) {
    log += element
}
assert log == '56789'
log = ''
for (element in 9..5) {
    log += element
}
assert log == '98765'
log = ''
(9..<5).each {
    log += it
}
assert log == '9876'

// 作为一个顶级类， range也可以通过实现一个isCase方法进行操作符重写，与contains一样，可以像grep过滤器一样在switch语句中使用range
result = ''
(5..9).each {
    result += it
}
assert result == '56789'
assert (0..10).isCase(5)
age = 36
switch (age) {
    case 16..20: insuranceRate = 0.05; break
    case 21..50: insuranceRate = 0.06; break
    default: throw new IllegalArgumentException()
}
assert insuranceRate == 0.06
ages = [20, 36, 42, 56]
midage = 21..50
assert ages.grep(midage) == [36, 42]

// custom range
class Weekday implements Comparable {

    static final DAYS = [
            'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'
    ]

    private int index = 0

    Weekday(String day) {
        index = DAYS.indexOf(day)
    }

    Weekday next() {
        return new Weekday(DAYS[(index + 1) % DAYS.size()])
    }

    Weekday previous() {
        return new Weekday(DAYS[(index - 1)])// automatic underflow
    }

    int compareTo(Object other) {
        return this.index <=> other.index
    }

    String toString() {
        return DAYS[index]
    }
}

def mon = new Weekday('Mon')
def fri = new Weekday('Fri')
def worklog = ''
for (day in mon..fri) {
    worklog += day.toString() + ' '
}
assert worklog == 'Mon Tue Wed Thu Fri '

// specifying lists
myList = [1, 2, 3]
assert myList.size() == 3
assert myList[0] == 1
assert myList instanceof ArrayList
emptyList = []
assert emptyList.size() == 0
longList = (0..1000).toList()
assert longList[555] == 555
explicitList = new ArrayList()
explicitList.addAll(myList)
assert explicitList.size() == 3
explicitList[0] = 10
assert explicitList[0] == 10
explicitList = new LinkedList(myList)
assert explicitList.size() == 3
explicitList[0] = 10
assert explicitList[0] == 10

// accessing parts of a list with the overloaded subscript operator
myList = ['a', 'b', 'c', 'd', 'e', 'f']
assert myList[0..2] == ['a', 'b', 'c']
assert myList[0, 2, 4] == ['a', 'c', 'e']
myList[0..2] = ['x', 'y', 'z']
assert myList == ['x', 'y', 'z', 'd', 'e', 'f']
myList[3..5] = []
assert myList == ['x', 'y', 'z']
myList[1..1] = ['y', '1', '2']// adding
assert myList == ['x', 'y', '1', '2', 'z']

// list operators involved in adding and removing items
myList = []
myList += 'a'
assert myList == ['a']
myList += ['b', 'c']
assert myList == ['a', 'b', 'c']
myList = []
myList << 'a' << 'b'// leftShift is like append
assert myList == ['a', 'b']
assert myList - ['b'] == ['a']
assert myList * 2 == ['a', 'b', 'a', 'b']

// lists taking part in control structures
myList = ['a', 'b', 'c']
assert myList.isCase('a')
candidate = 'a'
switch (candidate) {
    case myList: assert true; break// classify by containment
    default: assert false
}
assert ['x', 'a', 'z'].grep(myList) == ['a']
myList = []
if (myList) assert false// empty lists are false
log = ''
for (i in [1, 'x', 5]) {
    log += i
}

// methods to manipulate list content
assert [1, [2, 3]].flatten() == [1, 2, 3]
assert [1, 2, 3].intersect([4, 3, 1]) == [3, 1]
assert [1, 2, 3].disjoint([4, 5, 6])
def list = [1, 2, 3]
popped = list.pop()
assert popped == 1
assert list == [2, 3]
assert [1, 2].reverse() == [2, 1]
assert [3, 1, 2].sort() == [1, 2, 3]
def list1 = [[1, 0], [0, 1, 2]]
list1 = list1.sort { a1, b1 -> a1[0] <=> b1[0] }
assert list1 == [[0, 1, 2], [1, 0]]
list1 = list1.sort { it.size() }
assert list1 == [[1, 0], [0, 1, 2]]
list1 = ['a', 'b', 'c']
list1.remove(2)
assert list1 == ['a', 'b']
list1.remove('b')
assert list1 == ['a']
list1 = ['a', 'b', 'b', 'c']
list1.removeAll(['b', 'c'])
assert list1 == ['a']
def doubled = [1, 2, 3].collect { it * 2 }// transforming one list into another
assert doubled == [2, 4, 6]
def odd = [1, 2, 3].findAll { it % 2 == 1 }
assert odd == [1, 3]
// 去除重复
def x = [1, 1, 1]
assert [1] == new HashSet(x).toList()
assert [1] == x.unique()
x = [1, null, 1]
assert [1, 1] == x.findAll { it != null }
assert [1, 1] == x.grep { it }

// list query, iteration, and accumulation
list1 = [1, 2, 3]
assert list1.count(2) == 1// 统计2出现次数
assert list1.max() == 3
assert list1.min() == 1
def even = list1.find { it % 2 == 0 }
assert even == 2
assert list1.every { it < 5 }
assert list1.any { it < 2 }
def store = ''
list1.each { store += it }
assert store == '123'
store = ''
list1.reverseEach { store += it }
assert store == '321'
assert list1.join('-') == '1-2-3'
// 用lambda对一个中间结果和遍历的当前元素进行操作，第一个参数是中间结果的初始值
result = list1.inject(0) { clinks, guests -> clinks += guests }// 从0开始，每次用lambda，然后下次再
assert result == 0 + 1 + 2 + 3
assert list1.sum() == 6
factorial = list1.inject(1) { fac, item -> fac *= item }
assert factorial == 1 * 1 * 2 * 3

// quicksort with lists
def quickSort(List list) {
    if (list.size() < 2) return list
    def pivot = list[list.size().intdiv(2)]
    def left = list.findAll { item -> item < pivot }
    def middle = list.findAll { item -> item == pivot }
    def right = list.findAll { item -> item > pivot }
    return (quickSort(left) + middle + quickSort(right))
}

assert quickSort([]) == []
assert quickSort([1]) == [1]
assert quickSort([1, 2]) == [1, 2]
assert quickSort([2, 1]) == [1, 2]
assert quickSort([3, 1, 2]) == [1, 2, 3]
assert quickSort([3, 1, 2, 2]) == [1, 2, 2, 3]
assert quickSort([1.0f, 'a', 10, null]) == [null, 1.0f, 10, 'a']
assert quickSort('Karin and Dierk'.toList()) == '  DKaadeiiknnrr'.toList()

// map
// specifying maps
def myMap = [a: 1, b: 2, c: 3]//一般key都是字符串
assert myMap instanceof HashMap
assert myMap.size() == 3
assert myMap['a'] == 1
def emptyMap = [:]
assert emptyMap.size() == 0
def explicitMap = new TreeMap()
explicitMap.putAll(myMap)
assert explicitMap['a'] == 1
def x1 = 'a'
assert ['x1': 1] == [x1: 1]
assert ['a': 1] == [(x1): 1]

// accessing maps(GDK map methods)
myMap = [a: 1, b: 2, c: 3]
assert myMap['a'] == 1
assert myMap.a == 1
assert myMap.get('a') == 1
assert myMap.get('a', 0) == 1
assert myMap['d'] == null
assert myMap.d == null
assert myMap.get('d') == null
assert myMap.get('d', 0) == 0// supply a default value
assert myMap.d == 0
myMap['d'] = 1
assert myMap.d == 1
myMap.d = 2
assert myMap.d == 2
myMap = ['a.b': 1]// 特殊字符
assert myMap.'a.b' == 1

// query methods on maps
myMap = [a: 1, b: 2, c: 3]
def other = [b: 2, c: 3, a: 1]
assert myMap == other// call to equals
assert !myMap.isEmpty()
assert myMap.size() == 3
assert myMap.containsKey('a')
assert myMap.containsValue(1)
assert myMap.keySet() == toSet(['a', 'b', 'c'])
assert toSet(myMap.values()) == toSet([1, 2, 3])
assert myMap.entrySet() instanceof Collection
assert myMap.any { it.value > 2 }
assert myMap.every { it.key < 'd' }

def toSet(list) {
    new java.util.HashSet(list)
}

// iterating over maps(GDK)
myMap = [a: 1, b: 2, c: 3]
store = ''
myMap.each {
    store += it.key
    store += it.value
}
assert store.contains('a1')
assert store.contains('b2')
assert store.contains('c3')
store = ''
myMap.each { key, value ->
    store += key
    store += value
}
assert store.contains('a1')
assert store.contains('b2')
assert store.contains('c3')
store = ''
for (key in myMap.keySet()) {
    store += key
}
assert store.contains('a')
assert store.contains('b')
assert store.contains('c')
store = ''
for (value in myMap.values()) {
    store += value
}
assert store.contains('1')
assert store.contains('2')
assert store.contains('3')

// changing map content and building new objects from it
myMap = [a: 1, b: 2, c: 3]
myMap.clear()
assert myMap.isEmpty()
myMap = [a: 1, b: 2, c: 3]
myMap.remove('a')
assert myMap.size() == 2
myMap = [a: 1, b: 2, c: 3]
def abMap = myMap.subMap(['a', 'b'])
assert abMap.size() == 2
abMap = myMap.findAll { it.value < 3 }
assert abMap.size() == 2
assert abMap.a == 1
def found = myMap.find { it.value < 2 }
assert found.key == 'a'
assert found.value == 1
doubled = myMap.collect { it.value *= 2 }
assert doubled instanceof List
assert doubled.every { it % 2 == 0 }
def addTo = []
myMap.collect(addTo) { it.value * 2 }
assert doubled instanceof List
assert addTo.every { it % 2 == 0 }

// couting word frequency with maps
def textGorpus =
        """
Look for the bare necessities
The simple bare necessities
Forget about your worries and your strife
I mean the bare necessities
Old Mother Nature's recipes
That bring teh bare necessities of life
"""
def words = textGorpus.tokenize()
def wordFrequency = [:]
words.each {
    wordFrequency[it] = wordFrequency.get(it, 0) + 1
}
def wordList = wordFrequency.keySet().toList()
wordList.sort { wordFrequency[it] }
def statistic = "\n"
wordList[-1..-6].each {
    statistic += it.padLeft(12) + ': '
    statistic += wordFrequency[it] + '\n'
}
assert statistic ==
        """
 necessities: 4
        bare: 4
        your: 2
         the: 2
        life: 1
          of: 1
"""
















