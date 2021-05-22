// 对Delegation.groovy重构

class Manager2 {
    {// 初始化块，调用delegateCallsTo
        delegateCallsTo Worker, Expert, GregorianCalendar
    }

    def schedule() { println "Scheduling..." }
}

Object.metaClass.delegateCallsTo = { Class... klassOfDelegates ->
    def objectOfDelegates = klassOfDelegates.collect { it.newInstance() }
    // 向类中添加methodMissing，这个类在比包内称为delegate
    delegate.metaClass.methodMissing = { String name, args ->
        println "intercepting call to $name..."
        def delegateTo = objectOfDelegates.find { it.metaClass.respondsTo(it, name, args) }// 第一个找到的优先级高
        if (delegateTo) {
            delegate.metaClass."$name" = { Object[] varArgs ->
                delegateTo.invokeMethod(name, varArgs)
            }
            delegateTo.invokeMethod(name, args)
        } else {
            throw new MissingMethodException(name, Manager.class, args)
        }
    }
}

peter = new Manager2()
peter.schedule()
peter.simpleWork1('fast')
peter.simpleWork1('quality')
peter.simpleWork2()
peter.simpleWork2()
peter.advancedWork1('fast')
peter.advancedWork1('quality')
peter.advancedWork2('prototype', 'fast')
peter.advancedWork2('product', 'fast')

try {
    peter.simpleWork3()
} catch (ex) {
    println ex
}