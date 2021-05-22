package com.wolf.evaluate

// Script中有一个属性binding，而binding内部有一个variables存储当前环境的变量，如当前声明的变量或者启动脚本传入的参数。当执行一个脚本时如果存在variables保存的变量那么可以直接使用。evaluate调用时会把当前脚本的binding传入下一个脚本中
//省略def不写
name = "abc"

//闭包不写def
myClosure = {
    age = 3
    println(age)
}

//evaluate函数可以调用另一个脚本文件,并把Script的binding对象传过去
result = evaluate(new File("/Users/chaoli/intellijWrkSpace/HelloGroovy/src/hello/groovy/eval/ScriptOne.groovy"))
println result



