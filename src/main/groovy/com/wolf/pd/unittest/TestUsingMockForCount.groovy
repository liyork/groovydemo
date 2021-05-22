import groovy.test.GroovyTestCase

// 模拟记录了一个方法被调用的次序和次数
class InvokeCount {
    def someWriter() {
        def file = new FileWriter("output.txt")
        file.write("one")
        file.write("two")
        file.write(3)
        file.flush()
        file.write(file.getEncoding())
        file.close()
    }
}

// 假设只想测试代码与协作者之间的交互，预期：依次调用3次write、1次flush、1次getEncoding、1次write、1次close
class TestUsingMockForCount extends GroovyTestCase {
    void testSomeWriter() {
        def testObj = new InvokeCount()
        def fileMock = new groovy.mock.interceptor.MockFor(java.io.FileWriter)
        def params = ['one', 'two', 3]
        def index = 0
        // 预期方法被调用3次
        fileMock.demand.write(3..3) { assert it == params[index++] }// 断言参数
        fileMock.demand.flush {}
        fileMock.demand.getEncoding { return "whatever" }
        fileMock.demand.write { assertEquals "whatever", it.toString() }
        fileMock.demand.close {}

        fileMock.use {
            testObj.someWriter()
        }
    }
}