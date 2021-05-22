// 使用命令连链接特性改进流程性。
// 利用groovy支持将命令或方法调用链接起来的特性，可以实现一定程度的流畅性。
// 调用方法需要参数时，groovy不要求使用括号。若方法返回一个结果，不使用点符号，就能在返回的这个实例上
// 进行连续的调用
def (forward, left, then, fast, right) = ['forward', 'left', '', 'fast', 'right']// 定义多变量

def move(dir) {
    println "moving $dir"
    this
}

def and(then) { this }

def turn(dir) {
    println "turning $dir"
    this
}

def jump(speed, dir) {
    println "jumping $speed and $dir"
    this
}

move forward and then turn left// 读取move和forward，调用move时forward是参数。之后groovy期待有一个可以调用and方法的对象。
jump fast, forward and then turn right// jump方法接收两个参数。