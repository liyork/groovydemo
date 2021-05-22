package com.wolf.pd.run.use;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Description: 调用groovy脚本，传递参数
 * JSR223提供了很多方法，Invocable的invokeMethod/invokeFunction，若重复使用一个脚本用Compilable接口
 * 还可以使用GroovyScriptEngine
 * Created on 2021/5/6 9:24 AM
 *
 * @author 李超
 * @version 0.0.1
 */
public class ParameterPassing {
    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        engine.put("name", "Venkat");// 使用Bindings传递参数
        try {
            engine.eval("println \"Hello ${name} from Groovy\"; name += '!' ");
            String name = (String) engine.get("name");
            System.out.println("Back in Java:" + name);
        } catch (ScriptException ex) {
            System.out.println(ex);
        }
    }
}
