import com.wolf.java.JaveCodeWithHeavierDependencies
import groovy.test.GroovyTestCase

class TestCodeWithHeavierDependenciesUsingOverriding1 extends GroovyTestCase {
    void testMyMethod() {
        def testObj = new ExtendedJavaCode()
        def originalPrintStream = System.out
        def printMock = new PrintMock()
        System.out = printMock
        try {
            testObj.myMethod()
        } finally {
            System.out = originalPrintStream
        }
        assertEquals 35, printMock.result
    }
}

class ExtendedJavaCode extends JaveCodeWithHeavierDependencies {
//class ExtendedJavaCode extends JaveCodeWithHeavierDependencies2 {// kotlin需要保持open才可以覆盖
    int someAction() { 25 }
}

class PrintMock extends PrintStream {// 代替System.out，当调用println时进行存储result
    PrintMock() { super(System.out) }
    def result

    void println(int text) { result = text }
}

