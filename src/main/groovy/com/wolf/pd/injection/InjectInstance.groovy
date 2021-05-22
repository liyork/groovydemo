// 给实例注入方法
// 每个实例都有一个MetaClass。可以将方法注入到实例的metaClass中，或者创建一个ExpandoMetaClass实例，将指定方法加入其中
class Person {
    def play() { println 'playing...' }
}

def emc = new ExpandoMetaClass(Person)
emc.sing = { ->
    'oh baby baby...'
}
emc.initialize()

def jack = new Person()
def paul = new Person()
jack.metaClass = emc
println jack.sing()
try {
    paul.sing()
} catch (ex) {
    println ex
}

// 从实例中去掉注入的方法，只有注入的方法会被清除
jack.metaClass = null
try {
    jack.sing()
} catch (ex) {
    println ex
}

// 简单的地将方法设置到实例的metaClass上
jack.metaClass.sing = { ->
    'oh baby1...'
}
println jack.sing()
try {
    paul.sing()
} catch (ex) {
    println ex
}
jack.metaClass = null
try {
    jack.sing()
} catch (ex) {
    println ex
}

// 将方法批量注入
jack.metaClass {
    sing = { ->
        'oh baby..'
    }
    dance = { ->
        'start the music..'
    }
}