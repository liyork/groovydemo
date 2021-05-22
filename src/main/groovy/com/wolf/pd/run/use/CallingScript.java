package com.wolf.pd.run.use;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Description: 利用JSR 223(即Java Specification Request，在JVM和脚本语言之间桥梁)调用groovy脚本
 * <p>
 * Created on 2021/5/6 9:19 AM
 *
 * @author 李超
 * @version 0.0.1
 */
public class CallingScript {
    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        System.out.println("Calling script from java");
        try {
            // java中执行脚本
            engine.eval("println 'Hello from Groovy'");// 还可以从其他地方读取
        } catch (ScriptException ex) {
            System.out.println(ex);
        }
    }
}
