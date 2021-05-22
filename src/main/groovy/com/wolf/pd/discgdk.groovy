package com.wolf.pd
// 看类的一个实例包含哪些内容
str = 'hello'
println str
println str.dump()
println str.inspect()// 旨在说明创建一个对象需要提供什么，需要重写toString告知

// 使用上下文with方法，支持创建一个上下文(context)。在with的作用域内调用的任何方法都被定向导该上下文对象。
lst = [1, 2]
lst.with {// 使用with方法设置一个上下文
    add(3)
    add(4)
    println size()
    println contains(2)
}

// with原理，当调用with方法时，它会将该闭包的delegate属性设置到调用with的对象上
lst.with {
    println "this is $this"
    println "owner is $owner"
    println "delegate is $delegate"
}

// sleep
thread = Thread.start {// GDK
    println "Thread started"
    startTime = System.nanoTime()
    new Object().sleep(2000)// Object上调用sleep忽略中断，而Thread.sleep不会忽略中断
    endTime = System.nanoTime()
    println "Thread done in ${(endTime - startTime) / 10**9} seconds"
}
new Object().sleep(100)
println "Let's interrupt that thread"
thread.interrupt()
thread.join()

println "playWithSleep"
// 响应中断并有try
def playWithSleep(flag) {
    thread = Thread.start {
        println "Thread started"
        startTime = System.nanoTime()
        new Object().sleep(2000) {// 响应到终端，看返回值决定是否继续等待
            println "Interrupted..." + it
            flag// 返回true则立即返回，否则继续等待2000
        }
        endTime = System.nanoTime()
        println "Thread done in ${(endTime - startTime) / 10**9} seconds"
    }
    thread.interrupt()
    thread.join()
}

playWithSleep(true)
playWithSleep(false)

// 间接访问属性，一开始不知道属性名，使用[]
class Car1 {
    int miles, fuelLevel
}

car = new Car1(fuelLevel: 80, miles: 25)
propertyNames = ['miles', 'fuelLevel']
propertyNames.each { name ->
    println "$name = ${car[name]}"// []操作符映射到groovy的getAt方法，动态地访问属性
}
car[propertyNames[1]] = 100// 设定属性值
println "fuelLevel now is ${car.fuelLevel}"

// 动态调用方法
class Person1 {
    def walk() {
        println "walking.."
    }

    def walk(int miles) {
        println "walking $miles miles.."
    }

    def walk(int miles, String where) {
        println "walking $miles miles $where"
    }
}

peter = new Person1()
peter.invokeMethod("walk", null)
peter.invokeMethod("walk", 10)
peter.invokeMethod("walk", [2, 'uphill'] as Object[])

// 使用Range对象作为索引
int[] arr = [1, 2, 3, 4, 5, 6]
println arr[2..4]

// Process类提供了访问stdin/stdout和stderr命令的边界方法，对应out、in和err属性
// 与wc进程交互
process = "wc".execute()// 启动一个进程执行命令
// out创建一个输出到process的流，用于之后的写入数据
process.out.withWriter {// 在OutputStream上创建OutputStreamWriter并传递给闭包
    // 将输入发送到进程
    it << "let the world know..\n"// 以管道的方式连接到一个进程中，写入数据
    it << "groovy rocks!\n"
}
// in创建一个输入流从process
println process.in.text// text用于来自标准输出或进程的响应
//println process.text

// 命令带有参数
String[] command = ['groovy', '-e', ' "print \'Groovy\'" ']// 第一个参数是命令，其余是参数
println "calling ${command.join(' ')}"
println command.execute().text// 执行进程命令并获得结果

// 线程
def printThreadInfo(msg) {
    def currentThread = Thread.currentThread()
    println "$msg Thread is ${currentThread}. Daemon? ${currentThread.isDaemon()}"
}

printThreadInfo 'Main'
Thread.start {// 启动线程
    printThreadInfo "Started"
    sleep(3000) { println "Interrupted" }
    println "Finished Started"
}
sleep(1000)
Thread.startDaemon {// 启动守护线程
    printThreadInfo "Started Daemon"
    sleep(5000) { println "Interrupted" }
    println "Finished Started Daemon"
}

// 一次性读取文件内所有
println new File('xx.txt').text

// 每次一行
new File('xx.txt').eachLine { line ->
    println line
}

println new File('xx.txt').filterLine { it =~ /life/ }

// 写入文件,leftShift方法(<<操作符)
new File("xx.txt").withWriter { file ->
    file << "some data.."
}
// 还有withStream/withReader/newReader/iterator/

// 以上io中，实际使用时注意查看相关关闭代码

// 对java.util的扩展
// asImmutable、asSynchronized、inject、runAfter








