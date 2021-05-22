import groovy.xml.XmlSlurper

// 用DOMCategory进行xml解析
// DOMCategory可以通过类GPath(Groovy path expression)的符号在DOM结构中导航
document = groovy.xml.DOMBuilder.parse(new FileReader('languages.xml'))// 加载到内存
rootElement = document.documentElement
use(groovy.xml.dom.DOMCategory) {// 要使用DOMCategory必须把代码放在use块内
    println "Languages and authors"
    languages = rootElement.language// 通过子元素的名字就能访问所有子元素
    languages.each { language ->
        // 获取属性名name，获取子元素author
        println "${language.'@name'} authored by ${language.author[0].text()}"
    }

    def languagesByAuthor = { authorName ->
        languages.findAll { it.author[0].text() == authorName }.collect {
            it.'@name'
        }.join(', ')
    }
    println "Languages by Wirth:" + languagesByAuthor('Wirth')
}

// XMLParser，利用groovy的动态类型和元编程能力。但没有保留XML InfoSet，忽略了文档中的XML注释和处理指令
languages = new XmlParser().parse('languages.xml')
println "Languages and authors"
languages.each {
    println "${it.@name} authored by ${it.author[0].text()}"
}

def languagesByAuthor = { authorName ->
    languages.findAll { it.author[0].text() == authorName }.collect {
        it.@name
    }.join(', ')
}
println "Languages by Wirth:" + languagesByAuthor('Wirth')

// XMLSlurper
// 较大文档，XMLParser的内存使用大，XMLSlurper可以处理，执行惰性求值，内存比较友好。
languages = new XmlSlurper().parse('languages.xml')
println "Languages and authors"
languages.language.each {
    println "${it.@name} authored by ${it.author[0].text()}"
}
def languagesByAuthor1 = { authorName ->
    languages.language.findAll { it.author[0].text() == authorName }.collect {
        it.@name
    }.join(', ')
}
println "Languages by Wirth:" + languagesByAuthor1('Wirth')

// 使用XML文档中的命名空间解析他们，命名空间可以处理名字冲突，命名空间需要保持唯一性。
languages = new XmlSlurper().parse('computerAndNaturalLanguages.xml').declareNamespace(human: 'Natural')
println "Languages: "
println languages.language.collect { it.@name }.join(', ')

println "Natural languages: "
println languages.'human:language'.collect { it.@name }.join(', ')

// 创建xml
langs = ['C++': 'Stroustrup', 'Java': 'Gosling', 'Lisp': 'McCarthy']
content = ''
langs.each { language, author ->
    fragment = """
      <language name="$language}>
        <author>${author}</author>
      </language>
"""
    content += fragment
}
def xml = "<languages>${content}</languages>"
println xml

// 使用StreamingBuilder
langs = ['C++': 'Stroustrup', 'Java': 'Gosling', 'Lisp': 'McCarthy']
xmlDocument = new groovy.xml.StreamingMarkupBuilder().bind {
    mkp.xmlDeclaration()
    mkp.declareNamespace(computer: "Computer")
    languages {
        comment << "Created using StreamingMarkupBuilder"
        langs.each { key, value ->
            computer.language(name: key) {
                author(value)
            }
        }
    }
}
println xmlDocument
