import com.wolf.pd.unittest.CodeWithHeavierDependencies
import groovy.test.GroovyTestCase

// 使用分类进行模拟
// 分类仅在Groovy代码中才有用，无助于模拟在编译好的java代码中调用的方法
// 若被测试的类是final的，分类方式可以应用
class TestUsingCategories extends GroovyTestCase {
    void testMyMethod() {
        def testObj = new CodeWithHeavierDependencies()
        use(MockHelper) {// 让分类来拦截对方法的调用
            testObj.myMethod()
            assertEquals 35, MockHelper.result
        }
    }
}

class MockHelper {
    def static result

    def static println(self, text) { result = text }

    def static someAction(CodeWithHeavierDependencies self) { 25 }
}
