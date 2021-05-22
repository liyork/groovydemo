import com.wolf.pd.unittest.CodeWithHeavierDependencies
import groovy.test.GroovyTestCase

// 使用覆盖实现模拟
// 若简单的为myMethod编写单元测试，很慢，由于没有返回值也无法执行断言。
class TestCodeWithHeavierDependenciesUsingOverriding extends GroovyTestCase {
    void testMyMethod() {
        def testObj = new CodeWithHeavierDependenciesExt()
        testObj.myMethod()
        assertEquals 35, testObj.result
    }
}

// 新类扩展，并模拟行为
// 必须想一个聪明的办法，将依赖模拟出来，这样就可以将精力集中到对代码的行为做单元测试上。
// 模拟了someAction/println
class CodeWithHeavierDependenciesExt extends CodeWithHeavierDependencies {
    def result

    int someAction() { 25 }

    def println(text) { result = text }
}