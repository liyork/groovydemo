package com.wolf.inaction

import org.codehaus.groovy.runtime.InvokerHelper
import org.codehaus.groovy.runtime.StringBufferWriter

// trace implementation by overriding invokeMethod
class Traceable implements GroovyInterceptable {// tagged superclass
    Writer writer = new PrintWriter(System.out)
    private int indent = 0

    Object invokeMethod(String name, Object args) {// override default
        writer.write("\n" + '  ' * indent + "before method '$name'")
        writer.flush()
        indent++
        // execute call
        def metaClass = InvokerHelper.getMetaClass(this)
        def result = metaClass.invokeMethod(this, name, args)
        indent--
        writer.write("\n" + '  ' * indent + "after method '$name'")
        writer.flush()
        return result
    }
}

class Whatever extends Traceable {
    int outer() {
        return inner()
    }

    int inner() {
        return 1
    }
}

def log = new StringBuffer()
def traceMe = new Whatever(writer: new StringBufferWriter(log))
assert 1 == traceMe.outer()
println(log.toString())

// intercepting method calls with ProxyMethodClass and TracingInterceptor
class Whatever2 {
    int outer() {
        return inner()
    }

    int inner() {
        return 1
    }
}

log = new StringBuffer("\n")
def tracer = new TracingInterceptor()
traceMe.writer = new StringBufferWriter(log)
def proxy = ProxyMetaClass.getInstance(Whatever2.class)// retrieve a suitable ProxyMetaClass
proxy.interceptor = tracer
proxy.use {// determine scope for using it
    assert 1 == new Whatever2().outer()
}
println(log.toString())

