import com.wolf.pd.unittest.ClassWithDependency
import groovy.test.GroovyTestCase

// 使用groovy Mock Library实现模拟
// 用于模拟较深的依赖，即模拟被测方法内创建的协议对象或依赖对象。
// StubFor和MockFor的意图是像分类那样拦截方法调用，不必为模拟创建单独的类。
class TestUsingStubFor extends GroovyTestCase {
    void testMethodB() {
        def testObj = new ClassWithDependency()

        // 提供想为其创建存根的类
        def fileMock = new groovy.mock.interceptor.StubFor(java.io.FileWriter)
        def text
        fileMock.demand.write { text = it.toString() }
        fileMock.demand.close {}

        // 会将FileWriter的MetaClass替换为一个ProxyMetaClass
        // 存根和模拟都不会帮助拦截对构造器的调用
        fileMock.use {// 在所附属的闭包内，对FileWriter实例的任何调用都会被路由到该存根
            testObj.methodB(1)
        }
        assert "The value is 1", text
    }
}