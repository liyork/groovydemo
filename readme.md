Groovy 是 Apache 旗下的一门基于 JVM 平台的动态/敏捷编程语言，在语言的设计上它吸纳了 Python、Ruby 和 Smalltalk 语言的优秀特性，语法非常简练和优美，开发效率也非常高 Groovy 可以与 Java
语言无缝对接，在写 Groovy 的时候如果忘记了语法可以直接按Java的语法继续写，也可以在 Java 中调用 Groovy 脚本，都可以很好的工作 Groovy 也并不会替代
Java，而是相辅相成、互补的关系，具体使用哪门语言这取决于要解决的问题和使用的场景。

Groovy是一种动态语言，它和Java类似（算是Java的升级版，但是又具备脚本语言的特点），都在Java虚拟机中运行。 当运行Groovy脚本时它会先被编译成Java类字节码，然后通过JVM虚拟机执行这个Java字节码类

编译Groovy源码为class:
groovyc demo.groovy

执行groovy脚本的实质其实是执行的这个Java类的main方法，脚本源码里所有代码都被放到了run方法中，脚本中定义的方法都会被定义在Main类中 Groovy的实质就是Java的class# groovydemo
