import groovy.test.GroovyTestCase
import jdk.internal.org.objectweb.asm.tree.analysis.Value

// variable declaration examples
class SomeClass {
    public fieldWithModifier
    String typedField
    def untypedField
    protected field1, field2, field3
    private assignedField = new Date()
    static classField
    public static final String CONSTA = 'a', CONSTB = 'b'

    def someMethod() {
        def localUntypedMethodVar = 1
        int localTypedMethodVar = 1
        def localVarWithoutAssignment, andAnotherOne
    }
}

def localvar = 1
boundvar1 = 1

def someMethod() {
    localMethodVar = 1
    boundvar2 = 1
}

final String PI = 3.14
assert PI.class.name == 'java.lang.String'
assert PI.length() == 4
new GroovyTestCase().shouldFail(ClassCastException.class) {
    Float areaOfCircleRadiousOne = PI
}

// referencing fields with the subscript operator
class Counter {
    public count = 0
}

def counter = new Counter()
counter.count = 1
assert counter.count == 1
def fieldName = 'count'
counter[fieldName] = 2
assert counter['count'] == 2

// Extending the general field-access mechanism
class PretendFieldCounter {
    public count = 0

    // 没有属性则访问此
    Object get(String name) {// 重写了dot-fieldName操作符
        return 'pretend value'
    }

    // 设定任意属性则进入
    void set(String name, Object value) {// 重写了field assignmeng操作符
        count++
    }
}

def pretender = new PretendFieldCounter()
assert pretender.isNoField == 'pretend value'
assert pretender.count == 0
pretender.isNofieldEither = 'just to increse counter'
assert pretender.count == 1

// declaring methods
class SomeClass1 {
    static void main(args) {// implicit public，默认publics
        def some = new SomeClass1()
        some.publicVoidMethod()
        assert 'hi' == some.publicUntypedMethod()
        assert 'ho' == some.publicTypedMethod()
        combinedMethod()// call static method of current class
    }

    void publicVoidMethod() {

    }

    def publicUntypedMethod() {
        return 'hi'
    }

    String publicTypedMethod() {
        return 'ho'
    }

    protected static final void combinedMethod() {

    }
}

// 当声明类型被忽略的时候，groovy 使用 Object 作为类型
class SomeClass2 {
    static void main(Args) {
        assert 'untyped' == method(1)
        assert 'typed' == method('whatever')
        assert 'two args' == method(1, 2)
    }

    static method(Arg) {
        return 'untyped'
    }

    static method(String arg) {
        return 'typed'
    }

    static method(arg1, Number arg2) {
        return 'two args'
    }
}

// advanced parameter usages
class Summer {
    def sumWithDefaults(a, b, c = 0) {// explicit arguments and a default value
        return a + b + c
    }

    def sumWithList(List args) {// define arguments as a list
        return args.inject(0) { sum, i -> sum += i }
    }

    def sumWithOptionals(a, b, Object[] optionals) {// optional arguments as an array
        return a + b + sumWithList(optionals.toList())
    }

    def sumNamed(Map args) {// define arguments as a map
        ['a', 'b', 'c'].each { args.get(it, 0) }
        return args.a + args.b + args.c
    }
}

def summer = new Summer()
assert 2 == summer.sumWithDefaults(1, 1)
assert 2 == summer.sumWithList([1, 1])
assert 3 == summer.sumWithList([1, 1, 1])
assert 2 == summer.sumWithOptionals(1, 1)
assert 3 == summer.sumWithOptionals(1, 1, 1)
assert 2 == summer.sumNamed(a: 1, b: 1)
assert 3 == summer.sumNamed(a: 1, b: 1, c: 1)
assert 1 == summer.sumNamed(c: 1)

// protecting from NullPointerExceptions using the ?. operator
def map = [a: [b: [c: 1]]]
assert map.a.b.c == 1
if (map && map.a && map.a.x) {// protect with if: short-circuit evaluation
    assert map.a.x.c == null
}
try {
    assert map.a.x.c == null
} catch (NullPointerException npe) {
}
assert map?.a?.x?.c == null// safe referencing operator

// calling constructors with positional parameters
// 构造方法默认是 public 的
class VendorWithCtor {
    String name, product

    VendorWithCtor(name, product) {// constructor definition
        this.name = name
        this.product = product
    }
}

def first = new VendorWithCtor('Canoo', 'ULC')// normal constructor use
def second = ['Canoo', 'ULC'] as VendorWithCtor// coercion with as
VendorWithCtor third = ['Canoo', 'ULC']// coercion in assignment

// calling constructors with named parameters
class Vendor {
    String name, product
}

new Vendor()
new Vendor(name: 'Canoo')
new Vendor(product: 'ULC')
new Vendor(name: 'Canoo', product: 'ULC')
def vendor = new Vendor(name: 'Canoo')
assert 'Canoo' == vendor.name

// 隐式的调用构造方法
java.awt.Dimension area
area = [200, 100]
assert area.width == 200
assert area.height == 100

// multimethods: method lookup relies on dynamic types
def oracle(Object o) { return 'object' }

def oracle(String o) { return 'string' }

Object x = 1// Object静态类型和Integer动态类型
Object y = 'foo'
assert 'object' == oracle(x)
assert 'string' == oracle(y)

// multimethods to selectively override equals
class Equalizer {
    boolean equals(Equalizer equalizer) {// 仅仅用在参数类型为Equalizer上
        return true
    }
}

Object same = new Equalizer()
Object other = new Object()
assert new Equalizer().equals(same)
assert !new Equalizer().equals(other)

// declaring properties in GroovyBeans
class MyBean implements Serializable {
    def untyped
    String typed
    def item1, item2
    def assigned = 'default value'
}

// 自动插入get、set
def bean = new MyBean()
assert 'default value' == bean.getAssigned()
bean.setUntyped('some value')
assert 'some value' == bean.getUntyped()
bean = new MyBean(typed: 'another value')
assert 'another value' == bean.getTyped()

// calling accessors the Groovy way
class MrBean {
    String firstname, lastname// groovy style properties

    String getName() {// getter for derived property
        return "$firstname $lastname"
    }
}

bean = new MrBean(firstname: 'Rowan')// generic constructor
bean.lastname = 'Atkinson'// call setter
assert 'Rowan Atkinson' == bean.name// call getter

// advanced accessors with Groovy
class DoublerBean {
    public value// visible field
    void setValue(value) {
        this.value = value// inner field access
    }

    def getValue() {
        value * 2// inner field access
    }
}

bean = new DoublerBean(value: 100)
assert 200 == bean.value// property access,调用getValue
assert 100 == bean.@value// outer field access

// GDK methods for bean properties
class SomeClass3 {
    def someProperty
    public someField
    private somePrivateField
}

def obj = new SomeClass3()
def store = []
obj.properties.each {
    store += it.key
    store += it.value
}
assert store.contains('someProperty')
assert !store.contains('someField')
assert !store.contains('somePrivateField')
assert store.contains('class')
//assert store.contains('metaClass')// 错误
assert obj.properties.size() == 2

// expando
def boxer = new Expando()// 可以作为bean的扩展
assert null == boxer.takeThis
boxer.takeThis = 'ouch!'
assert 'ouch!' == boxer.takeThis
boxer.fightBack = { times ->
    return this.takeThis * times
}
//assert 'ouch!ouch!ouch!' == boxer.fightBack(3)// MissingPropertyException

println this// 当前对象
println this.class
this.class.methods.name.grep(~/get.*/).sort()

// invoice example for GPath
class Invoice {
    List<LineItem> items
    Date date
}

class LineItem {
    Product product
    int count

    int total() {
        return product.dollar * count
    }
}

class Product {
    String name
    def dollar
}

def ulcDate = new Date(107, 0, 1)
def ulc = new Product(dollar: 1499, name: 'ULC')
def ve = new Product(dollar: 499, name: 'Visual Editor')
def invoices = [
        new Invoice(date: ulcDate, items: [
                new LineItem(count: 5, product: ulc),
                new LineItem(count: 1, product: ve),
        ]),
        new Invoice(date: [107, 1, 2], items: [
                new LineItem(count: 4, product: ve)
        ])
]
//assert [5 * 1499, 499, 4 * 499] == invoices.items*.total()// total for each line item
//def name = invoices[0].items.grep { it.total() > 7000 }.product.name
//assert ['ULC'] == invoices.items.grep { it.total() > 7000 }.product.name// query of product names
def searchDates = invoices.grep {
    it.items.any { it.product == ulc }
}.date*.toString()
assert [ulcDate.toString()] == searchDates

// *展开操作符
def getList() {
    return [1, 2, 3]
}

def sum(a, b, c) {
    return a + b + c
}

assert 6 == sum(*list)

def range = (1..3)
assert [0, 1, 2, 3] == [0, *range]

map = [a: 1, b: 2]
assert [a: 1, b: 2, c: 3] == [c: 3, *: map]

// the use keyword for calculation on strings
class StringCalculationCategory {
    static def plus(String self, String operand) {
        try {
            return self.toInteger() + operand.toInteger()
        } catch (NumberFormatException fallback) {
            return (self << operand).toString()
        }
    }
}

// Category 的用法被限制在闭包体内和当前线程上，这样改变之后不会在全局可视
use(StringCalculationCategory) {
    assert 1 == '1' + '0'
    assert 2 == '1' + '1'
    assert 'x1' == 'x' + '1'
}

