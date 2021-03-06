groovy in action 

为什么你应
该学习 groovy？你期望的回报是什么？

Groovy 将让你迅速的获得成功， groovy 比用 java 写代码更加简单，更易进行自动化重
复的任务，还可以作为日常工作用来编写特别脚本， groovy 的代码阅读起来更加自然易懂，
当然更重要的是， groovy 用起来更加有趣

我们坚信学习一门程序语言的唯一途径是：动手尝试它

groovy 开发的主要方向：更加丰富的特性和比 java 更友好的语言， 为
已经十分成熟的平台带来动态语言的特性

没有哪个语言提供了
比 groovy 的更好的 java 友好性和完整的现代语言特性。

groovy 的最好定义： groovy 是在 java
平台上的、 具有象 Python， Ruby 和 Smalltalk 语言特性的灵活动态语言， groovy 保证了这些
特性象 java 语法一样被 java 开发者使用


groovy 代码被编译成 java 字节码，然后能集成到 java 应用程序中
或者 web 应用程序，整个应用程序都可以是 groovy 编写的——groovy 是非常灵活的

groovy
在后台做了许多工作来完成敏捷性和动态性

与 java 的友好性有俩方面：与 java 运行时环境无缝集成和与 java 相似的语法

groovy 运行在 java 虚拟机， java 的类库也可以继续使
用， Groovy 仅仅是创建 java 类的一种新的途径——通过在运行时创建， groovy 是使用了额
外 jar 文件为依赖的 java

每一个 groovy 的类型（groovy type）都是 java.lang.Object 的子
类。每一个 groovy 对象都是一个类的实例。

学习 groovy 的理由还有很多，它开阔你的思路， 使你有
了新的解决方案，在开发的时候帮助你理解新概念，无论你使用哪个语言

groovyc的编译器， groovyc
编译器为每个groovy源文件产生至少一个类文件

groovyc –d classes Fibonacci.groovy
groovyc产生一个父类为groovy.lang.Script的java类，
这个类包括一个main方法

学习新的程序语言好比学习说外语，你必须学习新的词汇，语法和语言习惯

学习初的
努力是必不可少的，无论如何，学习新语言唯一方法是自我练习，接受新概念和风格，并且
你探索新想法

断言的多个作用：
断言用来显示程序当前的状态
断言经常用来替换行注释，因为他们显示期望的结果并且在同一时间进行验证


类是面向对象编程的基础。因为类用来定义了一个对象的结构。

groovy中缺省的方法访问修饰符是public

脚本是一个扩展名为.groovy的文本文件，这个文件能够在命令行执行：
groovy myfile.groovy

脚本包括了groovy语句，这些语句没有在一个声明的类中

一个groovy脚本被完整构建——也就是说，在
执行之前脚本被转换、编译和产生类

Groovy的数字不像java，他们都是类对象，而不是专有类型

groovy中保证了一切事物都
是对象

注意声明、访问和修改列表是如何统一在一起的,map中都是通过[]操作
groovy语言设计者怎样设计一般的请求
操作， 并且为程序员提供简单的、一致的语法，让程序员的工作更轻松

闭包允许执行一个任意指定好的
代码块

一个闭包是一个用花括号围起来的语句块，像别的任何代码块，为了传递参
数给闭包，闭包有一组可选的参数列表，通过“->”表示列表的结束

程序员应该通过最简单的代码尽可能清晰的描述问题

不管你写的是groovy类，或者是groovy脚本，它们都作为java类在
JVM中运行

每一个groovy对象都是一个java对象实例，在运行时他们都指向同一个对象

Groovy通过一个名叫MetaClass的装备来技巧性的过滤对对象所有方法的调用，这样
允许动态分配方法，包括解决附加方法到存在的类

Groovy的语法是面向行的，但是执行groovy代码确不是这样的，不像别的脚本语言，
groovy代码不是一行一行的解释执行的

groovy代码被完整的转换，通过转换器产生一个java类，产生的这个类是
groovy和java之间的粘合剂。产生的groovy类的格式与java字节码的格式是一样的

所有的这些工作都在后台进行，以至于你会感觉groovy像解释执行语言。但是实际上
它不是，在使用之前类已经完整的构建并且在运行时不能进行更改

假设一个groovy文件包括一个像foo语句， groovy产生的字节码
不是直接调用这个方法的，而是像这样：
getMetaClass().invokeMethod(this, "foo", EMPTY_PARAMS_ARRAY)

方法的调用被重定向为通过对象的MetaClass进行处理，这个MetaClass
现在能在运行时处理如拦截、重定向、增加/删除方法等等之类的方法调用

在运行之前， Groovy是兼容于java并且符合java类构建协议的，但是groovy在运行
时仍旧是动态生成类，通过MetaClass， groovy能修改groovy调用者调用的任何方法。

在groovy中，万事万物皆对象

有两种类型密切关联（ int和
Integer），这导致了这个加法的复杂性， java这么处理的理由是： C的遗传并且基于性
能上的考虑， groovy的答案是将更多的工作留给计算机，让程序员的工作更少。

一切都是对象有助于
保持代码的紧凑和可读

在groovy代码中，任何时候看到专有类型值（比如数字5，或者布尔值true）， 它都
是相应的包装类型实例的一个引用。 为了简洁和习惯， groovy允许声明变量为专有类型的
变量，后台真正使用的类型是包装类型

注意BigDecimal是默认的非整数类型
——除非指定后缀强制类型为Float或者Double

在groovy中出现的字面符号（数字、字符串等等）没有任何问题，他们都
是对象，他们仅仅传递给java时才进行装箱和拆箱，操作符是方法调用的快速途径

不管是int还是
Integer， groovy使用的都是引用类型（Integer）

静态类型为性能优化、编译时安全检查和IDE支持提供更多有用的帮助，也显示变量或
者方法参数相关的附加信息及方法重载，静态类型也是从反射获取有用信息的前提。

动态类型，在另一方面，这不仅仅便于延迟程序员编写一些特定的脚本，也对保护和规
避类型有用.对node的类型和包名称是不感兴趣的，这样可以节省很多工作——类
型的声明，导入需要的包
动态类型第二个有用的地方是在一个对象上进行没有固定类型的方法调用

当一门语言的操作符是基于一个方法调用时， 如果这些方法能够被覆盖，这
种行为叫做操作符重载

1+1仅仅是1.plus(1)的便利书写方式， Integer有一
个plus方法的实现。

Groovy的字符串有两种风格：一般字符串和GString， 一般的字符串是
java.lang.String的实例， GString是groovy.lang.GString的实例， GString允
许有占位符并且允许在运行时对占位符进行解析和计算。

模式操作符把模式创建的时间从模式匹配的时间中分离出来，通过重用有限状态机提升
了性能

数字也是对
象， 并且能像普通对象那样看到它们

提供一般工作的更多便利性是groovy的主要目标之一，所以， groovy提升每个简单
的数据类型为一个类对象并且把操作符实现为一个方法调用，这样使得面向对象的优势无所
不在

groovy在设计之初就考虑了这些问题，在不牺牲java性能的情
况下专注于使重复的任务尽可能的简单

groovy通过range扩展了你在代码中表述自
己思想的方式
事实上range也是对象

range可以使用任何类型，
只要这个类型满足以下两个条件：
 该类型实现next和previous方法，也就是说，重写++和--操作符；
 该类型实现java.lang.Comparable接口；也就是说实现compareTo方法，
实际上是重写<=>操作符

for循环和条件不是对象，不能被重
用，并且不能传递参数，但是range可以， range让我们只关注业务代码该做什么，而不用
关心如何做，使你专注于自己的业务，而不用关心边界条件

闭包不难
面向对象的最高原则是对象有自己的行为和数据，闭包也是对象，他主要的目的是他们
的行为——这几乎是闭包的全部。

一个闭包是被包装为一
个对象的代码块，实际上闭包像一个可以接受参数并且能够有返回值的方法
闭包是一个普
通对象，因为你能够通过一个变量引用到它
闭包是由一些代码组
成的对象，并且 groovy 为闭包提供了简洁的语法
闭包是 groovy 提供的进行透明回调的一种方式

Groovy使用闭包来指定这些每次都被执行的代码块，并且增加了许多额外的方法（each、
find、 findAll、 collect 等等）到集合类上，使他们容易使用，只是用来
简化了 groovy，因为闭包能够把控制逻辑从每次执行的代码块中分离出来，如果你发现你
想到的一个相似的结构没有在 groovy 中出现，可以容易的增加到 groovy 中
把控制逻辑和每次迭代处理的逻辑分开不是介绍闭包概念的唯一原因，第二个原因（可
能是更重要的原因）是在处理资源的时候使用闭包
控制逻辑是每次循环，而里面迭代的逻辑是业务逻辑
file 的 eachLine 方法负责处理文件输入流的打开和关闭，这样避免你偶然的错误处
理形成资源泄漏

箭头是闭包的参数和代码的分隔指示符。 方法的传递值给箭头左边参数，箭头的
右边是闭包的代码
什么时候你看到闭包的花括号，思考： new Closure(){}
花括号指明构建了一个新的闭包对象

在 groovy 中类型是可选的，因此闭包的参数是可选的，如果闭包的参数进行了显式的
类型声明，那么类型的检查发生在运行时而不是在编译的时候

把闭包作为最后一个参数，这样在调用该方法的时候可以使用闭包
的简单语法声明方式

类 groovy.lang.Closure 是一个普通的 java 类

明白细节经
常能让你自己想出特别优美的解决方案

闭包是一个逻辑块，可以直接把闭包作为参数到处传递，从方法
调用返回闭包，或者存储起来，在后面再使用

堆栈信息最
好是从底部向上阅读

断言的最佳实践：
a.在写断言之前，先让你的代码失败，并且看看抛出的异常信息是否充足；
b.当写一个断言的时候，第一次先让断言失败，看看失败的信息是否充足，如果
不充足，增加一些信息，然后再次让断言失败以检查信息现在是否充足；
c.如果你觉得需要断言来让你的代码更清晰或者保护你的代码，增加断言而不要
管前面的规则；
d.如果你觉得需要一个信息来使你的断言的目的和意图更清晰，增加一个这样的
信息，而不要管前面的规则。

本质上，衡量标准是代码
容易理解并且能简单的检


groovy 文件根据下面的规则能够包含多个公共类的声明：
a.如果一个 groovy 文件不包括类声明，那么它被作为一个脚本处理；也就是说，
它透明的包装为 Script 类型的类，自动生成的类的名称与源文件名称相同（不包括扩展
名），文件只能够的内容被包装在一个 run 方法中，并且增加了一个 main 方法用来容易的启动脚本
b.如果 groovy 文件中只包含一个类声明，并且这个类的名称与文件名相同（不
包括扩展名），那么这里有与 java 中一样的一对一的关系
c.一个 groovy 文件也许包含多个不同访问范围的类声明， 这里没有强制规则必
须使类的名称与文件名一样， groovyc 编译器完美的为所有在这个文件中声明的类创建
*.class 文件，如果你希望直接调用你的脚本，例如在命令行或者 IDE 中使用 groovy，
那么在你的文件中的第一个类中应该有一个 main 方法
d.一个 groovy 文件也许混合了类的声明和脚本代码，在这种情况下，脚本代码
将变成一个主类被执行，因此不要声明一个与源文件同名的类

当没有进行显式编译的时候， groovy 通过匹配相应名称的*.groovy 源文件来查找类，groovy 仅仅根据类名称是否匹配源文件名称来查找类，当这
样的一个文件被找到的时候，在这个文件中声明的所有类都将被转换，并且 groovy 在后面
的时间都将能找到这些类

由于*.groovy 源文件不需要编译成*.class 文件，因此在查找类的时候也需要查找
*.groovy 文件，当这样做的时候， groovy 使用了相同的策略：编译器查找一个 groovy 类 Vendor
的时候，在 business 包对应的文件系统中查找 business/Vendor.groovy 文件

查找类的时候是从某个地方开始的， java 的类路径就是基于这个目的来使用的，类路径
是查找*.class 文件的开始点的一组列表。 Groovy 查找*.groovy 时重用了类路径。

当我们查找一个给定的类的时候，如果 groovy 同时找到了一个*.class 和*.groovy 文件，
它使用最后修改的那个文件

groovy 的类必须在定义之前指定它们所在的包，当没有声
明包的时候， groovy 使用默认包

Groovy 使用在%GROOVY_HOME%/conf 下一个特殊配置文件来定义它自己的配置文
件，查看 groovy-starter.conf 文件显示的下列行

groovy 的方法查找是方法参数的动态类型化，而 java 是静态类型的，这个 groovy
的特性叫做复合方法 Multimethods

groovy 构造了访问方法并且把它们增加到了字节码中，这样保证了 MyBean 在 java
中的使用， groovy 的 MyBean 类是一个验证过的 JavaBean

groovy 安排了下列规则
在类内部，引用 fieldName 或者 this.fieldName 将被解释为直接属性访问，而不是
bean 风格的属性访问（通过访问方法进行）；在类的外部，可以使用 reference.@fieldName
语法直接访问类属性

groovy 的元对象协议 MOP（Meta-Object-Protocol）的组成部分，
MOP 是在运行时改变对象和类行为的系统能力

在 groovy 中，所有的对象都实现了 GroovyObject 接口

在
groovy 中你的所有类都是通过 GroovyClassGenerator 来构建的，因此它们都
实现了GroovyObject这个接口并且有接口中的方法的默认实现

GroovyObject 与 MetaClass 协作， MetaClass 是 Groovy 元概念的核心，它提供了一个
Groovy 类的所有的元数据

MetaClass 被存储在一个名称为 MetaClassRegistry 的中心存储器中，同时 groovy 也从
MetaClassRegistry 中获取 MetaClass

每个对象有一个 MetaClass，这种能力在默认的实现中没有
使用，当前默认实现是在 MetaClassRegistry 中每一个类使用一个 MetaClass

GroovyObject 引用到的 MetaClass 是 GroovyObject 在 MetaClassRegistry 中注册的
类型，他是不需要相同的，例如，一个特定的对象可以有一个特殊的 MetaClass（这个
MetaClass 可以与该对象类的其他对象的 MetaClass 不一样


Groovy 生成 java 字节码，这样每一个方法的处理都遵循下列机制之一：
1、 类自己实现 invokeMethod 的方法（这也许被代理到 MetaClass）；
2、 它自己的 MetaClass，通过 getMetaClass().invokeMethod()进行调用；
3、 在 MetaClassRegistry 中注册的该类型的 MetaClass。

调用逻辑有多种方式来实现拦截、中转或者伪装方法：
 实现/覆盖在 GroovyObject 中的 invokeMethod 方法来伪装或者中转方法调用
（通常你定义的所有方法仍然被调用）；
 实 现 / 覆 盖 在 GroovyObject 中 的 invokeMethod 方 法 ， 并 且 也 实 现
GroovyInterceptable 接口来增加方法的拦截调用代码。
 提供一个 MetaClass 的实现类，并且在目标 GroovyObject 对象上调用
setMetaClass；
 提供一个 MetaClass 的实现类，并且在 MetaClassRegistry 中为所有的目标类
（Groovy 和 java 类）进行注册，这种方案通过 ProxyMetaClass 进行支持。

在应用大范围的改变到 MetaClassRegistry 的时候要当心，这可能导致代码
产生看起来与它们无关的潜在错误。小心的使用切面，这样可以避免太多切面造成的痛苦



	










