使用扩展模块(extension-modules)特性，在编译时向现有类添加实例方法和静态方法，并在运行时在应用中使用

0. cd root path to main
1. groovyc -d classes groovy/com/wolf/pd/ext/extpack/*.groovy 编译类
2. jar -cf priceExtensions.jar -C classes com -C resources/manifest . 打jar包

3. groovy -classpath priceExtensions.jar groovy/com/wolf/pd/ext/usepack/FindPrice.groovy 执行
