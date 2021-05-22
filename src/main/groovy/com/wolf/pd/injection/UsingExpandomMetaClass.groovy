// 使用ExpandoMetaClass注入方法
// 向类的MetaClass添加方法可以实现向类中注入方法，全局的
Integer.metaClass.daysFromNow = { ->
    Calendar today = Calendar.instance
    today.add(Calendar.DAY_OF_MONTH, delegate)// delegate表示调用者
    today.time
}
println 5.daysFromNow()

Integer.metaClass.getDaysFromNow = { ->
    Calendar today = Calendar.instance
    today.add(Calendar.DAY_OF_MONTH, delegate)
    today.time
}
println 5.daysFromNow// 没有括号则groovy会将方法当成一个属性，所以需要设置一个属性，创建getDaysFromNow的方法

// 还想注入到Short和Long这些类上,将闭包保存到一个变量中进行复用
daysFromNow = { ->
    Calendar today = Calendar.instance
    today.add(Calendar.DAY_OF_MONTH, (int) delegate)
    today.time
}
Long.metaClass.daysFromNow = daysFromNow
println 5L.daysFromNow()

// 也可以选择在Integer的基类上提供这个方法
Number.metaClass.someMethod = { ->
    println "someMethod called"
}
2.someMethod()

// 向类中注入静态方法
Integer.metaClass.'static'.isEven = { val -> val % 2 == 0 }
println "Is 2 even? " + Integer.isEven(2)
println "Is 3 even? " + Integer.isEven(3)

// 添加构造器用<<，若用<<覆盖现有构造器或方法则报错
Integer.metaClass.constructor << { Calendar calendar ->
    new Integer(calendar.get(Calendar.DAY_OF_YEAR))
}
println new Integer(Calendar.instance)

// 覆盖构造函数(构造新对象)
Integer.metaClass.constructor = { int val ->
    println "Intercepting constructor call"
    constructor = Integer.class.getConstructor(Integer.TYPE)
    constructor.newInstance(val)
}
println new Integer(4)
println new Integer(Calendar.instance)

// 添加一堆方法，groovy提供了一种可以将这些方法分组的方式，组织成一种ExpandoMetaClass(EMC)DSL的方便语法
Integer.metaClass {
    daysFromNow = { ->
        Calendar today = Calendar.instance
        today.add(Calendar.DAY_OF_MONTH, delegate)
        today.time
    }
    getDaysFromNow = { ->
        Calendar today = Calendar.instance
        today.add(Calendar.DAY_OF_MONTH, delegate)
        today.time
    }
    'static' {
        isEven = { val -> val % 2 == 0 }
    }
    constructor = { Calendar calendar ->
        new Integer(calendar.get(Calendar.DAY_OF_YEAR))
    }

    constructor = { int val ->
        println "Intercepting constructor call"
        constructor = Integer.class.getConstructor(Integer.TYPE)
        constructor.newInstance(val)
    }
}

// ExpandoMetaClass限制是只能从groovy代码内调用，不能从编译过的java代码中调用，也不能从java代码中通过反射调用。

