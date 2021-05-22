import com.wolf.pd.unittest.ClassWithDependency
import groovy.test.GroovyTestCase

// 模拟会指出，纵然代码产生了指定结果，但其表现与预期不符，即代码段没有调用使用demand在测试预期中设置的close方法
class TestUsingMockFor extends GroovyTestCase {
    void testMethodB() {
        def testObj = new ClassWithDependency()

        def fileMock = new groovy.mock.interceptor.MockFor(java.io.FileWriter)
        def text
        fileMock.demand.write { text = it.toString() }
        fileMock.demand.close {}

        fileMock.use {
            testObj.methodB(1)
        }
        assert "The value is 1", text
    }

    // methodC中调用了close
    void testMethodC() {
        def testObj = new ClassWithDependency()

        def fileMock = new groovy.mock.interceptor.MockFor(java.io.FileWriter)
        def text
        fileMock.demand.write { text = it.toString() }
        fileMock.demand.close {}

        fileMock.use {
            testObj.methodC(1)
        }
        assert "The value is 1", text
    }
}