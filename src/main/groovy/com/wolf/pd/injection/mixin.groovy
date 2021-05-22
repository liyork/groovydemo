// mixin一种运行时能力，将多个类中的实现引入进来。
// groovy会在内存中把这些类的实例连接起来，先调用混入的类(最后混入的优先)。
class Friend {
    def listen() {
        "$name is listening as a friend"// name由该类所混入的类提供
    }
}

// 1. 使用注解，但是只能由类的作者使用，若没有源代码则不行
@Mixin(Friend)
class Person2 {
    String firstName
    String lastName

    String getName() { "$firstName $lastName" }
}

john = new Person2(firstName: "john", lastName: "smith")
println john.listen()

// 向已有类注入行为，运行时动态实现混入
class Dog {
    String name
}

Dog.mixin Friend// 将Friend混入Dog
buddy = new Dog(name: "Buddy")
println buddy.listen()

// 向一个类的具体实例中混入类

class Cat {
    String name
}

try {
    rude = new Cat(name: "Rude")
    rude.listen()
} catch (ex) {
    println ex.message
}

socks = new Cat(name: "Socks")
socks.metaClass.mixin Friend
println socks.listen()
