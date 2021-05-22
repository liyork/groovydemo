// 多个混入时，若有重名则最后的生效。
// 链条方式组织

abstract class Writer {
    abstract void write(String message)
}

class StringWriter extends Writer {
    def target = new StringBuilder()

    void write(String message) {
        target.append(message)
    }

    String toString() {
        target.toString()
    }
}

def writeStuff(writer) {
    writer.write("This is stupid")// 调用mixin的方法(以最后一个为准)
    println writer
}

// 混入filter
def create(theWriter, Object[] filters = []) {
    def instance = theWriter.newInstance()
    filters.each { filter -> instance.metaClass.mixin filter }
    instance
}

writeStuff(create(StringWriter))

// 需求变化，要将给定参数的值以大写形式写出
// 这可能是一个先兆，后面可能还会有很多变换或过滤需求，若对这些类做出任何修改，当这样的请求出现时，他们可能无法扩展
class UppercaseFilter {
    void write(String message) {// 聚焦于，过滤和转换消息，之后传递消息
        def allUpper = message.toUpperCase()
        invokeOnPreviousMixin(metaClass, "write", allUpper)
    }
}

// 从右向左不断执行
// 当前第一次被触发是在StringWriter，所以delegate是StringWriter
Object.metaClass.invokeOnPreviousMixin = { MetaClass currentMixinMetaClass, String method, Object[] args ->
    def previousMixin = delegate.getClass()// 链条中最左侧的实例的类型是目标实例
    for (mixin in mixedIn.mixinClasses) {// mixedIn属性，为实例保存有序的Minxi列表，所添加的Mixin构成一个链条fifo，通向被换入的目标实例
        if (mixin.mixinClass.theClass == currentMixinMetaClass.delegate.theClass) break
        previousMixin = mixin.mixinClass.theClass
    }
    mixedIn[previousMixin]."$method"(*args)
}
// StringWriter<----UperCaseFilter
//           <--write            <--write
// 在StringWriter实例上调用write先被路由到所连接的UppercaseFilter，UppercaseFilter.write对参数变换后将调用转发到目标实例上
writeStuff(create(StringWriter, UppercaseFilter))

// 另一个过滤器
class ProfanityFilter {
    void write(String message) {// 替换参数，然后转发给链条中左侧的实例
        def filtered = message.replaceAll('stupid', 's*****')
        invokeOnPreviousMixin(metaClass, "write", filtered)
    }
}

writeStuff(create(StringWriter, ProfanityFilter))

// 综合，方法的调用会向链条的左侧传递
// StringWriter<--write--UppercaseFilter<--write--ProfanityFilter--write
writeStuff(create(StringWriter, UppercaseFilter, ProfanityFilter))
// StringWriter<--write--ProfanityFilter<--write--UppercaseFilter--write
writeStuff(create(StringWriter, ProfanityFilter, UppercaseFilter))
