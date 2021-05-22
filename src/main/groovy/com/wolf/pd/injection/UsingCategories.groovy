class StringUtil {
    def static toSSN(self) {// self表示目标对象，默认Object类型
        if (self.size() == 9) {
            "${self[0..2]}-${self[3..4]}-${self[5..8]}"
        }
    }
}

// 分类，提供类一种可控的方法注入方式，限定在一个代码块内
// 分类，一种能修改类的MetaClass的对象，这种修改仅在代码块的作用域和执行线程内有效。
// 要求注入的方法是静态的，而且至少接受一个参数,第一个参数是方法调用的目标。
use(StringUtil) {// 要注入的方法在代码块内生效
    println "123456789".toSSN()
    println new StringBuilder("987654321").toSSN()
}
// 原理
// 调用use时，groovy将脚本中调用use()方法路由到GroovyCategorySupport类的use方法，该方法定义了一个新的作用域，
// 其中包括栈上的一个新的属性/方法列表，用于目标对象的MetaClass，之后它会检查给定分类中的每个静态方法，并将静态方法
// 及其参数(至少一个)加入到属性/方法列表中。最后它会调用附在其后的闭包，闭包内的任何方法调用都会被拦截，发送给由
// 分类提供的实现。最后，一旦等从闭包返回，use()就结束掉前面创建的作用域，丢弃分类中注入的方法

try {
    println "123456789".toSSN()
} catch (MissingMethodException ex) {
    println ex.message
}

// 让groovy编译器将实例方法转变为静态方法
@Category(String)
class StringUtilAnnotated {
    def toSSN() {
        if (size() == 9) {
            "${this[0..2]}-${this[3..4]}-${this[5..8]}"
        }
    }
}

use(StringUtilAnnotated) {
    println "123456789".toSSN()
}

// 注入的方法可以以对象或闭包为参数
class FindUtil {
    def static extractOnly(String self, closure) {
        def result = ''
        self.each {
            if (closure(it)) {
                result += it
            }
        }
        result
    }
}

use(FindUtil) {
    println "121254123".extractOnly { it == '4' || it == '5' }
}

// 多分类
use(StringUtil, FindUtil) {
    str = '123487651'
    println str.toSSN()
    println str.extractOnly { it == '8' || it == '1' }
}

// 类一旦定义，Groovy会将类名转变为一个对该类的Class元对象的引用
println String == String.class

// 拦截对toString()的调用
class Helper {
    def static toString(String self) {
        def method = self.metaClass.methods.find { it.name == 'toString' }
        '!!' + method.invoke(self, null) + '!!'
    }
}

// 应该将分类用于方法注入，而不是方法拦截
// 每次进入和退出代码块都是有代价的，每次进入时，groovy都必须检查静态方法，并将其加入到新作用域的一个方法列表中。在块的最后还要清理该作用域
use(Helper) {
    println 'hello'.toString()
}



