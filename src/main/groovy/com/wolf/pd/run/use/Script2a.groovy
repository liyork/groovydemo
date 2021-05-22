println "In Script2"
name = "Venkat"
shell = new GroovyShell(binding)// 将当前的Binding对象传递，每个脚本执行时都有一个Binding对象。
result = shell.evaluate(new File('../other/Script1a.groovy'))
println "Script1a returned: $result"// 返回值
println "hello2 $name"