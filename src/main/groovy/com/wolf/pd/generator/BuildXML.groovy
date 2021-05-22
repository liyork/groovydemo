// 数据来自一个集合，输出到Writer中
langs = ['C++': 'Stroustrup', 'Java': 'Gosling', 'Lisp': 'McCarthy']

writer = new StringWriter()
bldr = new groovy.xml.MarkupBuilder(writer)// 适合小到中型的文档
bldr.languages {
    langs.each { k, v ->
        language(name: k) {
            author(v)
        }
    }
}
println writer