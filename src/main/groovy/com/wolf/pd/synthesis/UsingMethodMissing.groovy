class Person3 {
    def work() { "working.." }
    def plays = ['Tennis', 'VolleyBall', 'BasketBall']

    def methodMissing(String name, args) {// 拦截不存在的方法调用
        System.out.println "MethodMissing called for $name"
        def methodInList = plays.find { it == name.split('play')[1] }
        if (methodInList) {
            "playing ${name.split('play')[1]}..."
        } else {
            throw new MissingMethodException(name, Person.class, args)
        }
    }
}

jack = new Person3()
println jack.work()
println jack.playTennis()
println jack.playBasketBall()
println jack.playVolleyBall()
println jack.playTennis()

try {
    jack.playPolitics()
} catch (ex) {
    println "Error: " + ex
}