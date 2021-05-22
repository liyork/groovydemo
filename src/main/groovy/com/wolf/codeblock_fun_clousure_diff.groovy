package com.wolf
// 1. 代码块
// 在代码块中用def关键字定义的变量不能在外部访问
{
    def msg = "Hello,world!";
}
//println msg;// 报错

// 在代码块中没有用def关键字声明的变量，就可以在外部访问
{
    a = "Hello,world!";
}
println a

// 2. 闭包
// 是可以引用外部上下文环境的一系列语句。可以将闭包赋予一个变量，并在稍后执行。
//每个闭包都有一个返回值，默认的返回值就是该闭包中最后一行语句的结果
//闭包可以访问外部的变量

def x = 1;

def b = {
    x * 2;//闭包可以访问外部的变量
}

println(b())

// 将闭包赋予一个变量将记住该闭包创建时的上下文环境，即使运行时已经超出了原来的作用域

def c;
{
    def m = 10;
    c = { m };
}
// m已经超出了作用域，但是闭包c可以访问到
println(c())

// 将一个闭包放入另一个闭包，可以创建它的两个实例:
c = { e = { "Hello,World"; }; e }
v1 = c();// 调用c()，返回的是闭包e
v2 = c();
// false，说明c()返回了不同的闭包实例
println v1 == v2

// 可以在定义闭包时声明一些参数，之后在调用闭包时将这些参数值传入闭包:
c = { n -> println "Parameter=" + n; }
c(10);

// 可以利用参数将信息从包内导出，也就是所谓的“输出参数”
c = { o, p -> o << p };
x = [];
c(x, 1);
c(x, 2);
c(x, 3);
x << 4// add添加
println x

// 在闭包中，总是可以用到一个叫做it的变量，如果没有显式的定义参数，则可以用it来引用
// 如果没有传入任何参数，it仍然存在，但是为null
c = { "Parameter=${it}" };
println c(10);

// 闭包的参数it会将外部it屏蔽
def it = 10;
c = { it * it };

println c(5);//在此，隐式变量it仍然引用参数5，而非外部定义的10

// 参数在调用闭包时可以被命名。这些被命名的参数将被组装进一个Map，并指定给闭包的第一个参数：
def x1 = { a1, b1, c1 -> a1.m + a1.n + a1.o - b1 + c1 };
// 在调用闭包x1的时候,参数m:10,n:15,o:20被组装进Map赋予形参a,5、6按顺序分别赋予形参b和c
println x1(m: 10, 5, n: 15, 6, o: 20);

// 我们可以在闭包内部和外部查询参数的个数:
def c2 = { a2, b2, c2 -> getMaximumNumberOfParameters(); }
println c2(1, 2, 3)
println c2.getMaximumNumberOfParameters()

// 调用闭包的时候，可以对最后的一个或几个参数应用默认值:
def c3 = { a3, b3 = 2, c3 = 3 -> a3 + b3 + c3 };
println c3(1);

// 一个闭包可以在最后一个参数前添加Object[]前缀来创建可变长度的参数:
def c4 = { a4, Object[] b4 -> a4 + b4[0] + b4[1]; }
println c4(1, 2, 3);

// 可以使用一个list参数调用闭包。如果闭包没有定义一个list类型的参数，则此参数将作为分解的参数传入闭包:
def c5 = { a5, b5, c5 -> a5 + b5 + c5 };
println c5([1, 2, 3]);

// 闭包可以通过将第一个或前几个参数固化为常量，使用curry方法进行拷贝:
def c6 = { a6, b6, c6 -> a6 + b6 + c6 };
def d6 = c6.curry(1);//拷贝闭包c6,并将第一个参数a固化为常量1
println d6(2, 3);//调用闭包d6，只需要给出剩余的参数。实际使用的参数仍然是1,2,3

// 在闭包中，可以结合使用curry和不定长度参数:
def c7 = { a7, Object[] b7 -> b7.each({ m -> a7 = a7 + m; }); a7; };
def d7 = c7.curry(1);
def e7 = d7.curry(2, 3);//实际参数为1,[2,3]
def f7 = e7.curry(4);//实际参数为1,[2,3,4]
println f7(5);//实际参数为1,[2,3,4,5]

// 3. 函数
// 必须使用def关键字定义函数,函数也不能嵌套使用；
// 函数不能访问在外部通过def定义的变量:

def c8 = 10;

def f() {
    println c8;//编译通过
}
//f();//运行时报错

// 可以使用特殊的语法标记&将函数赋予新的命名：
def f2() {
    println "Hello,world!";
}

def s = this.&f2;
s()

// 函数可以后参数，包括输入参数和输出参数；
// 如果两个函数的拥有不同个数的未指定类型的参数，则可以使用相同的名称
def f(a) {
    println a * a;
}

def f(a, b) {
    println a * b;
}

f(10);
f(5, 10)

// 函数返回值:
// a.如果有显示地使用return关键字，则返回return指定的返回值，其后面的语句不再执行;
// b.如果没有显式地使用return关键字，则返回函数最后一行语句的运行结果;
// c.如果使用void关键字代替def关键字定义函数，则函数的返回值将为null。

// 如果一个函数与一个闭包拥有相同的名称和参数，则调用的时候看哪个在下面，会进行覆盖
def f2(a) {
    return a * 10;
}

def f2 = { a -> a * 20 };

println f2(5);//输出50而不是100

// 函数可以接受闭包作为参数:
def f(a, Closure c) {
    c(a);
}

f(10) { println "Parameter a = " + it; }

// 函数可以为参数指定默认值:
def f3(a, b = 10) {
    return a + b;
}

println f3(5);//输出15

// 函数也会将命名的参数包装进一个Map，并赋予第一个参数:
def f5(a, b) {
    return b + a.x + a.y;
}

println f5(x: 2, 1, y: 3);//输出6

// 函数同样支持不定长度的参数、分解list作为参数，以及递归调用