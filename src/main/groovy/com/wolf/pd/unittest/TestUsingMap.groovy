import com.wolf.pd.unittest.ClassWithDependency
import groovy.test.GroovyTestCase

// 使用map实现模拟
// map的值可以是任意对象，甚至可以是闭包
class TestUsingMap extends GroovyTestCase {
    void testMethodA() {
        def text = ''
        def fileMock = [write: { text = it }]

        def testObj = new ClassWithDependency()
        testObj.methodA(1, fileMock)
        assertEquals "The value is 1", text
    }
}