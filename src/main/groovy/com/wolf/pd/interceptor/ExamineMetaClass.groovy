package com.wolf.pd.interceptor

Integer.metaClass.invokeMethod = { String name, args -> }
println Integer.metaClass.getClass().name// groovy.lang.ExpandoMetaClass

// groovy的类默认metaClass不是ExpandoMetaClass当向其中添加方法后变成ExpandoMetaClass当

def printMetaClassInfo(instance) {
    print "MetaClass of ${instance} is ${instance.metaClass.class.simpleName}"
    println " with delegate ${instance.metaClass.delegate.class.simpleName}"
}

printMetaClassInfo(2)// 开始时Integer实例的metaClass是HandleMetaClass实例
println "MetaClass of Integer is ${Integer.metaClass.class.simpleName}"

println "Adding a method to Integer metaClass"

Integer.metaClass.someNewMethod = {}
printMetaClassInfo(2)// 向metaClass添加方法后，被替换为ExpandoMetaClass实例，好像这里并没有变
println "MetaClass of Integer is ${Integer.metaClass.class.simpleName}"

@groovy.transform.Immutable
class MyClass {
    String name
}

obj1 = new MyClass("obj1")
printMetaClassInfo(obj1)
println "Adding a method to MyClass metaClass"
MyClass.metaClass.someNewMethod = {}
printMetaClassInfo(obj1)

println "obj2 created later"
obj2 = new MyClass("obj2")
printMetaClassInfo(obj2)

