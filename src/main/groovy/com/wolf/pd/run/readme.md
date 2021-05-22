## 运行groovy代码

1. groovy Greet 执行groovy代码

2. groovyc Greet.groovy 编译groovy代码为class文件

3. java -classpath groovy-3.0.7.jar:. Greet

## groovy脚本中使用其他包的groovy类,需要确保该类在classpath下，以该类的名字在classpath下查找.groovy文件，若没有则用同样的名字查找.class文件

### 若没有package则

1. groovy -classpath other use/useCar

### 若有package则

1. groovy -classpath /Users/chaoli/intellijWrkSpace/groovydemo/src/main/groovy use/useCar

## 联合编译混合使用Groovy和Java。若只有Groovy源代码，当java类依赖其他java类时，若没有找到字节码，

javac将编译它认为必要的任何java类。而javac对groovy没有那么友好。不过groovyc支持联合编译，当编译groovy代码时，他会确定是否 有任何需要编译的java类，并负责编译他们。

1. 联合编译两个文件：groovyc -j other/AJavaClass.java use/UseJavaClass.groovy -Jsource=1.8

2. 运行：java -classpath groovy-3.0.7.jar:. UseJavaClass

## java中使用groovy的闭包

当groovy调用一个闭包时，它只是使用一个call方法。在java中创建一个闭包，只需要一个包含该方法的类

### 无参

1. 联合编译：groovyc -j use/UseAGroovyClass.java other/AGroovyClass.groovy
2. java -classpath groovy-3.0.7.jar:. com.wolf.pd.run.use.UseAGroovyClass

### 有参

3. groovyc -j use/UseAGroovyClass2.java
4. java -classpath groovy-3.0.7.jar:. com.wolf.pd.run.use.UseAGroovyClass2


