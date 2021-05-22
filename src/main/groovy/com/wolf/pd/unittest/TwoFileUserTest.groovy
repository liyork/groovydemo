import groovy.test.GroovyTestCase

// 若方法会创建多个实例。
// 模拟可灵活地支持多个对象
class TwoFileUser {
    def useFiles(str) {
        def file1 = new java.io.FileWriter("output1.txt")
        def file2 = new java.io.FileWriter("output2.txt")
        file1.write str
        file2.write str.size()
        file1.close()
        file2.close()
    }
}

class TwoFileUserTest extends GroovyTestCase {
    void testFiles() {
        def testObj = new TwoFileUser()
        def testData = 'Multi Files'
        def fileMock = new groovy.mock.interceptor.MockFor(java.io.FileWriter)
        fileMock.demand.write() { assertEquals testData, it }
        fileMock.demand.write() { assertEquals testData.size(), it }
        fileMock.demand.close(2..2) {}
        fileMock.use {
            testObj.useFiles(testData)
        }
    }

    void tearDown() {
        new File('output1.txt').delete()
        new File('output2.txt').delete()
    }
}