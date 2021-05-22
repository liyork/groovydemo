str = "hello"
methodName = 'toUpperCase'
// 获得元方法
methodOfInterest = str.metaClass.getMetaMethod(methodName)
//str.metaClass.getStaticMetaMethod()// 获取静态元方法
//str.metaClass.getProperty()// 获取元属性
println methodOfInterest.invoke(str)

// 判断对象是否支持方法
println String.metaClass.respondsTo(str, 'toUpperCase')[0].invoke(str)
println String.metaClass.respondsTo(str, 'compareTo', "test")
println String.metaClass.respondsTo(str, 'toUpperCase', 5)

// 动态访问对象
def printInfo(obj) {
    usrRequestedProperty = 'bytes'
    usrRequestedMethod = 'toUpperCase'

    // 动态调用一个属性
    println obj[usrRequestedProperty]
    // 或
    println obj."$usrRequestedProperty"
    // 动态调用一个方法
    println obj."$usrRequestedMethod"()
    // 或
    println obj.invokeMethod(usrRequestedMethod, null)
}

printInfo('hello')

// 迭代属性列表
println 'hello'.properties.each { println it }

