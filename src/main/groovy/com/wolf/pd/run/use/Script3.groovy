// 新binding对象传递
println "In Script3"
binding1 = new Binding()
binding1.setProperty('name', 'Venkat')
shell = new GroovyShell(binding1)
shell.evaluate(new File('../other/Script1a.groovy'))

binding2 = new Binding()
binding1.setProperty('name', 'Dan')
shell.binding = binding2
shell.evaluate(new File('../other/Script1a.groovy'))

shell.run(new File('../other/Script1a.groovy'), new ArrayList<String>(1))// 传递参数
