// 使用ExpandoMetaClass，不用修改源文件而能进行合成方法

class Person4 {
    def work() { "working... " }
}

Person4.metaClass.methodMissing = { String name, args ->// 比类的methodMissing方法优先级高
    def plays = ['Tennis', 'VolleyBall', 'BasketBall']

    System.out.println "MethodMissing called for $name"
    def methodInList = plays.find { it == name.split('play')[1] }
    if (methodInList) {
        def impl = { Object[] vargs ->
            "playing ${name.split('play')[1]}..."
        }
        Person4.metaClass."$name" = impl
        impl(args)
    } else {
        throw new MissingMethodException(name, Person.class, args)
    }
}

jack = new Person4()
println jack.work()
println jack.playTennis()
println jack.playTennis()

try {
    jack.playPolitics()
} catch (ex) {
    println "Error: " + ex
}