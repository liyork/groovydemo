import com.wolf.pd.unittest.CodeWithHeavierDependencies
import groovy.test.GroovyTestCase

// 使用ExpandoMetaClass实现模拟
// 此种方式只能在groovy代码中使用，对于在编译好的java代码内调用的方法，没有什么帮助。
// 似乎用JaveCodeWithHeavierDependencies就不行了。。看来用到java类时是已经编译好的class了，而groovy的运行时元编程是groovy的对象内的属性。
class TestUsingExpandoMetaClass extends GroovyTestCase {
    void testMyMethod() {
        def result
        // 为被测试的实例创建一个ExpandoMetaClass实例，不会对CodeWithHeavierDependencies的元类造成全局的影响
        def emc = new ExpandoMetaClass(CodeWithHeavierDependencies, true)
        emc.println = { text -> result = text }
        emc.someAction = { -> 25 }
        emc.initialize()

        def testObj = new CodeWithHeavierDependencies()
        testObj.metaClass = emc

        testObj.myMethod()
        assertEquals 35, result
    }
}