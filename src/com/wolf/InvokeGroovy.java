package com.wolf;


import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.Eval;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Created on 2021/3/11 11:08 PM
 *
 * @author 李超
 * @version 0.0.1
 */
public class InvokeGroovy {
    // 与Java集成
// Groovy非常容易集成在Java环境中，利用其动态性来做规则引擎、流程引擎、动态脚本环境，非常适合那些不不需要经常发布但又经常变更的场景下使用。在Java中集成（调用）Groovy 代码有下面几种方式。
    public static void main(String[] args) throws Exception {
        //useEval();

        //useGroovyShell();

        //useGroovyClassLoader();

        //useGroovyScriptEngine();

        useJSR223();
    }

    private static void useJSR223() throws javax.script.ScriptException, FileNotFoundException {
        // JSR 223 javax.script API
        // JSR-223 是 Java 中调用脚本语言的标准 API。从 Java 6 开始引入进来，主要目的是用来提供一种统一的框架，以便在 Java 中调用多种脚本语言。JSR-223 支持大部分流行的脚本语言，比如JavaScript、Scala、JRuby、Jython和Groovy等
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
        Bindings bindings = new SimpleBindings();
        bindings.put("age", 22);
        bindings.put("name", "xxx");
        Object value = engine.eval("if(age < 18){'未成年'}else{'成年'}", bindings);
        assert value.equals("成年");

        engine.eval(new FileReader("/Users/chaoli/intellijWrkSpace/groovydemo/src/com/wolf/reg/test.groovy"), bindings);
    }

    private static void useGroovyScriptEngine() throws IOException, ResourceException, ScriptException, InterruptedException {
        // GroovyScriptEngine
        // groovy.util.GroovyScriptEngine能够处理任何 Groovy 代码的动态编译与加载，可以从统一的位置加载脚本，并且能够监听脚本的变化，当脚本发生变化时会重新加载。
        GroovyScriptEngine scriptEngine = new GroovyScriptEngine("/Users/chaoli/intellijWrkSpace/HelloGroovy/src/hello/groovy");
        Binding binding = new Binding();
        binding.setVariable("name", "groovy");
        while (true) {
            // 修改test.groovy中值，会自动加载
            scriptEngine.run("com.wolf.reg.test.groovy", binding);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static void useGroovyClassLoader() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        // GroovyClassLoader
        // groovy.lang.GroovyClassLoader是一个定制的类加载器，可以在运行时加载 Groovy 代码，生成 Class 对象
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        String scriptText = "class Hello{void hello(){println 'hello'}}";
        Class clazz = groovyClassLoader.parseClass(scriptText);
        assert clazz.getName().equals("Hello");
        clazz.getMethod("hello").invoke(clazz.newInstance());
    }

    private static void useGroovyShell() {
        // GroovyShell
        // groovy.lang.GroovyShell除了可以执行 Groovy 代码外，提供了更丰富的功能，比如可以绑定更多的变量，从文件系统、网络加载代码等
        GroovyShell shell = new GroovyShell();
        // 绑定变量
        shell.setVariable("age", 22);
        // 求值
        Object evaluate = shell.evaluate("if(age < 18){'未成年'}else{'成年'}");
        System.out.println(evaluate);
        // 解析为脚本，延迟执行或缓存起来
        Script script = shell.parse("if(age < 18){'未成年'}else{'成年'}");
        assert script.run().equals("成年");
        // 加载/执行脚本
        //shell.evaluate(new File("script.groovy"));
    }

    private static void useEval() {
        // Eval
// groovy.util.Eval 类是最简单的用来在运行时动态执行 Groovy 代码的类，提供了几个静态工厂方法供使用，内部其实就是对GroovyShell的封装
        // 执行Groovy代码
        Eval.me("println 'hello'");
        // 绑定自定义参数
        Object result = Eval.me("age", 22, "if(age < 18){'未成年'}else{'成年'}");
        assert result.equals("成年");
        // 绑定一个名为x的参数
        assert Eval.x(4, "2*x").equals(8);
        // 两个参数x，y
        assert Eval.xy(4, 5, "x*y").equals(20);
        // 三个参数
        assert Eval.xyz(4, 5, 6, "x*y+z").equals(26);
    }
}
