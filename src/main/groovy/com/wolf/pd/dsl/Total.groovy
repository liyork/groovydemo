// 括号的限制与变通

value = 0

def clear() { value = 0 }

def add(number) { value += number }

def total() { println "Total is $value" }

clear()
add 2
add 5
add 7
total()
try {
    total// groovy认为对total的调用引用了一个属性
} catch (Exception ex) {
    println ex
}

// 编写方法实现名为total和clear的属性
def getClear() { value = 0 }

def getTotal() { println "getTotal is $value" }

total