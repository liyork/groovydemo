def large = 'large'
def thin = 'thin'
def visa = 'Visa'
def Olives = 'Olives'
def Onions = 'Onions'
def Bell_Pepper = 'Bell Pepper'

orderInfo = [:]

def methodMissing(String name, args) {// 调用不存在方法时触发
    orderInfo[name] = args
}

def acceptOrder(closure) {
    closure.delegate = this
    closure()
    println "Validation and processing performed here for order received:"
    orderInfo.each { key, value ->
        println "$key ->${value.join(', ')}"
    }
}