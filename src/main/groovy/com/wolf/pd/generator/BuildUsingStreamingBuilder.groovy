// 若文档非常大(若干兆字节)，可以用StreamingMarkupBuilder，内存占用更好
// 带有命名空间和xml注释

langs = ['C++': 'Stroustrup', 'Java': 'Gosling', 'Lisp': 'McCarthy']
xmlDocument = new groovy.xml.StreamingMarkupBuilder().bind {
    mkp.xmlDeclaration()
    mkp.declareNamespace(computer: "Computer")// 定义命名空间
    languages {
        comment << "Created using StreamingMarkupBuilder"
        langs.each { k, v ->
            computer.language(name: k) {// 元素关联到命名空间
                author(v)
            }
        }
    }
}
println xmlDocument