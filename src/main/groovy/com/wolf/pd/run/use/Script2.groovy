package com.wolf.pd.run.use

println "In com.wolf.pd.run.use.Script2"
shell = new GroovyShell()
// 调用另一个groovy脚本
shell.evaluate(new File('Script1.groovy'))
// 另一种方式
//evaluate(new File('Script1.groovy'))

