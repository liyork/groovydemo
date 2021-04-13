package com.wolf.evaluate

println("running in com.wolf.eval.ScriptOne.groovy")

println(binding.variables.name)
age = 3

println("MyScript:name=" + name)
// 命令行执行带上参数a=1
println("MyScript:args[0]=" + args[0])
println(binding.variables.name)


1


