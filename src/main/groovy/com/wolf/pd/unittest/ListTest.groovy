import junit.framework.TestCase

// groovy ListTest
class ListTest extends TestCase {
    void testListSize() {
        def lst = [1, 2]
        assertEquals "ArrayList size must be 2", 2, lst.size()
    }
}