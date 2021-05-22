// 生成json

class Person5 {
    String first
    String last
    def sigs
    def tools
}

john = new Person5(first: "John", last: "Smith",
        sigs: ['Java', 'Groovy'], tools: ['script': 'Groovy', 'test': 'Spock'])
bldr = new groovy.json.JsonBuilder(john)
writer = new StringWriter()
bldr.writeTo(writer)
println writer

println "---"

// 对输出加以定制
bldr = new groovy.json.JsonBuilder()
bldr {
    firstName john.first// k,v
    lastName john.last
    "special interest groups" john.sigs
    "preferred tools" {// 对象
        numberOfTools john.tools.size()
        tools john.tools
    }
}
writer = new java.io.StringWriter()
bldr.writeTo(writer)
println writer

// 若不想将数据保存在内存中，而是创建时就直接将其变成流，使用StreamingJsonBuilder
