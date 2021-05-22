// 方法拦截与DSL
// 使用拦截方法调用实现


def dslDef = new File('GroovyPizzaDSL.groovy').text
// orderPizza.dsl中时groovy代码，除了双引号中的字符串，其他不是方法名就是变量名
def dsl = new File('orderPizza.dsl').text

def script = """
${dslDef}
acceptOrder{
  ${dsl}
}
"""
new GroovyShell().evaluate(script)