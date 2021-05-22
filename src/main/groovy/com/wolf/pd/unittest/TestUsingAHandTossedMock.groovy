import com.wolf.pd.unittest.ClassWithDependency
import groovy.test.GroovyTestCase

// 用Expando模拟
// 利用groovy的动态类型，只要目标具备预期参数的能力。
class TestWithExpando extends GroovyTestCase {
    void testMethodA() {
        def testObj = new ClassWithDependency()
        def fileMock = new HandTossedFileMock()
        testObj.methodA(1, fileMock)
        assertEquals "The value is 1", fileMock.result
    }

    // 不用创建类，使用Expando
    void testMethodAUsingExpando() {
        // text属性和write方法
        def fileMock = new Expando(text: '', write: { text = it })

        def testObj = new ClassWithDependency()
        testObj.methodA(1, fileMock)
        assertEquals "The value is 1", fileMock.text
    }
}

class HandTossedFileMock {
    def result

    def write(value) { result = value }
}
