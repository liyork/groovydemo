// 为具体实例提供合成方法

class Person6 {}

def emc = new ExpandoMetaClass(Person6)
emc.methodMissing = { String name, args ->
    "I'm jack of all trades... I can $name"
}
emc.initialize()

def jack = new Person6()
def paul = new Person6()
jack.metaClass = emc
println jack.sing()
println jack.dance()
try {
    paul.sing()
} catch (ex) {
    println ex
}
