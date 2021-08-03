import groovy.namespace.QName
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlSlurper
import org.junit.jupiter.api.Test

class GPathTest {

    @Test
    void testGPath() {
        // 1读取xml
        def text = '''
    <list>
        <technology>
            <name>Groovy</name>
        </technology>
    </list>
'''

        def list = new XmlSlurper().parseText(text)

        assert list instanceof groovy.xml.slurpersupport.GPathResult
        assert list.technology.name == 'Groovy'
        println list.technology.name.text()
        println list.technology.zzz.text()// 属性没有不会报错

// XmlSlurper evaluates the structure lazily. So if you update the xml you’ll have to evaluate the whole tree again.
// GPath is a path expression language integrated into Groovy which allows parts of nested structured data to be identified. In this sense, it has similar aims and scope as XPath does for XML. The two main places where you use GPath expressions is when dealing with nested POJOs or when dealing with XML


        def books = '''
    <response version-api="2.0">
        <value>
            <books>
                <book available="20" id="1">
                    <title>Don Quixote</title>
                    <author id="1">Miguel de Cervantes</author>
                    <isMain>true</isMain>
                </book>
                <book available="14" id="2">
                    <title>Catcher in the Rye</title>
                   <author id="2">JD Salinger</author>
               </book>
               <book available="13" id="3">
                   <title>Alice in Wonderland</title>
                   <author id="3">Lewis Carroll</author>
               </book>
               <book available="5" id="4">
                   <title>Don Quixote</title>
                   <author id="4">Miguel de Cervantes</author>
               </book>
           </books>
       </value>
    </response>
'''

// 第一个书的作者
        def response = new XmlSlurper().parseText(books)
        def authorResult = response.value.books.book[0].author// 得到GPathResult

        assert authorResult.text() == 'Miguel de Cervantes'// 获取节点的文本

// get the first book’s author’s id
        def book1 = response.value.books.book[0]
        def bookAuthorId1 = book1.@id// 属性获取方式1
        def bookAuthorId2 = book1['@id']// 属性获取方式2

        assert bookAuthorId1 == '1'
        assert bookAuthorId1.toInteger() == 1
        assert bookAuthorId1 == bookAuthorId2

        println book1.isMain == "true"// 默认字符串
        println book1.isMain.text().toBoolean() == true
        println book1.isMain.toBoolean() == true// 可以直接toBoolean

// *用children()直接节点，**用depthFirst()，参见GPathResult.getProperty
        def catcherInTheRye = response.value.books.'*'// 取得所有boos，可以用.children()替换
                .find { node ->
                    node.name() == 'book' && node.@id == '2'
                }

        assert catcherInTheRye.title.text() == 'Catcher in the Rye'

// **获取其下所有深度的元素
        def bookId = response.'**'// 可用.depthFirst()替换,It goes as far down the tree as it can while navigating down the tree from a given node.
                .find { b ->
                    b.author.text() == 'Lewis Carroll'
                }.@id

        assert bookId == 3

// collect all book’s titles
        def titles = response.'**'.findAll { node -> node.name() == 'title' }*.text()
        assert titles.size() == 4

//  The breadthFirst() method finishes off all nodes on a given level before traversing down to the next level.

// depthFirst() vs .breadthFirst
        def nodeName = { node -> node.name() }
        def withId2or3 = { node -> node.@id in [2, 3] }

        assert ['book', 'author', 'book', 'author'] ==
                response.value.books.depthFirst().findAll(withId2or3).collect(nodeName)
        assert ['book', 'book', 'author', 'author'] ==
                response.value.books.breadthFirst().findAll(withId2or3).collect(nodeName)

// 属性转换
        titles = response.value.books.book.findAll { b ->
            b.@id.toInteger() > 2
        }*.title

        assert titles.size() == 2


// 2创建xml
// 2.1MarkupBuilder
        def writer = new java.io.StringWriter()
        def xml = new MarkupBuilder(writer)

        xml.records() {
            car(name: 'HSV Maloo', make: 'Holden', year: 2006) {
                country('Australia')
                record(type: 'speed', 'Production Pickup Truck with speed of 271kph')
            }
            car(name: 'Royale', make: 'Bugatti', year: 1931) {
                country('France')
                record(type: 'price', 'Most Valuable Car at $15 million')
            }
        }

        def re = new XmlSlurper().parseText(writer.toString())
        assert re.car.first().@name.text() == 'HSV Maloo'
        assert re.car.last().@name.text() == 'Royale'

// Creating XML elements
        def xmlString = "<movie>the godfather</movie>"

        def xmlWriter = new java.io.StringWriter()
        def xmlMarkup = new MarkupBuilder(xmlWriter)

// create a XML node with a tag called movie and with content the godfather.
        xmlMarkup.movie("the godfather")

        assert xmlString == xmlWriter.toString()

// Creating XML elements with attributes
        xmlString = "<movie id='2'>the godfather</movie>"

        xmlWriter = new java.io.StringWriter()
        xmlMarkup = new MarkupBuilder(xmlWriter)

        xmlMarkup.movie(id: "2", "the godfather")// value要有toString

        assert xmlString == xmlWriter.toString()

// Creating XML nested elements
        xmlWriter = new java.io.StringWriter()
        xmlMarkup = new MarkupBuilder(xmlWriter)

// 根是movie
        xmlMarkup.movie(id: 2) {// 	A closure represents the children elements of a given node
            name("the godfather")
        }
//println xmlWriter.toString()

        def movie = new XmlSlurper().parseText(xmlWriter.toString())

        assert movie.@id == 2
        assert movie.name.text() == 'the godfather'

// Namespace aware
        xmlWriter = new java.io.StringWriter()
        xmlMarkup = new MarkupBuilder(xmlWriter)

        xmlMarkup
                .'x:movies'('xmlns:x': 'http://www.groovy-lang.org') {// 	Creating a node with a given namespace xmlns:x
                    'x:movie'(id: 1, 'the godfather')
                    'x:movie'(id: 2, 'ronin')
                }

        def movies = new XmlSlurper()
                .parseText(xmlWriter.toString())
                .declareNamespace(x: 'http://www.groovy-lang.org')// registering the namespace
//println xmlWriter.toString()

        assert movies.'x:movie'.last().@id == 2
        assert movies.'x:movie'.last().text() == 'ronin'


// Mix code,more elements, to have some logic when creating our XML:
        xmlWriter = new java.io.StringWriter()
        xmlMarkup = new MarkupBuilder(xmlWriter)

        xmlMarkup
                .'x:movies'('xmlns:x': 'http://www.groovy-lang.org') {
                    (1..3).each { n ->// range
                        'x:movie'(id: n, "the godfather $n")
                        if (n % 2 == 0) {// condition
                            'x:movie'(id: n, "the godfather $n (Extended)")
                        }
                    }
                }

        movies = new XmlSlurper()
                .parseText(xmlWriter.toString())
                .declareNamespace(x: 'http://www.groovy-lang.org')

        assert movies.'x:movie'.size() == 4
        assert movies.'x:movie'*.text().every { name -> name.startsWith('the') }

// Mix code2
        xmlWriter = new java.io.StringWriter()
        xmlMarkup = new MarkupBuilder(xmlWriter)

        Closure<MarkupBuilder> buildMovieList = { MarkupBuilder builder ->
            (1..3).each { n ->
                builder.'x:movie'(id: n, "the godfather $n")
                if (n % 2 == 0) {
                    builder.'x:movie'(id: n, "the godfather $n (Extended)")
                }
            }

            return builder
        }

        xmlMarkup.'x:movies'('xmlns:x': 'http://www.groovy-lang.org') {
            buildMovieList(xmlMarkup)// 使用closure
        }

        movies =
                new XmlSlurper()
                        .parseText(xmlWriter.toString())
                        .declareNamespace(x: 'http://www.groovy-lang.org')

        assert movies.'x:movie'.size() == 4
        assert movies.'x:movie'*.text().every { name -> name.startsWith('the') }

// 2.2StreamingMarkupBuilder,a builder class for creating XML markup，应该是戴上了流的功能，不用xxWriter了？

// Using StreamingMarkupBuilder
        xml = new StreamingMarkupBuilder().bind {
// bind returns a Writable instance that may be used to stream the markup to a Writer
            records {
                car(name: 'HSV Maloo', make: 'Holden', year: 2006) {
                    country('Australia')
                    record(type: 'speed', 'Production Pickup Truck with speed of 271kph')
                }
                car(name: 'P50', make: 'Peel', year: 1962) {
                    country('Isle of Man')
                    record(type: 'size', 'Smallest Street-Legal Car at 99cm wide and 59 kg in weight')
                }
                car(name: 'Royale', make: 'Bugatti', year: 1931) {
                    country('France')
                    record(type: 'price', 'Most Valuable Car at $15 million')
                }
            }
        }

        re = new XmlSlurper().parseText(xml.toString())

        assert re.car.size() == 3
        assert re.car.find { it.@name == 'P50' }.country.text() == 'Isle of Man'

// The groovy.xml.MarkupBuilderHelper is a helper for groovy.xml.MarkupBuilder.
// This helper normally can be accessed from within an instance of class groovy.xml.MarkupBuilder or an instance of groovy.xml.StreamingMarkupBuilder.
// This helper could be handy in situations when you may want to:
// Produce a comment in the output
//Produce an XML processing instruction in the output
//Produce an XML declaration in the output
//Print data in the body of the current tag, escaping XML entities
//Print data in the body of the current tag

// In both MarkupBuilder and StreamingMarkupBuilder this helper is accessed by the property mkp:
// Using MarkupBuilder’s 'mkp'
        xmlWriter = new java.io.StringWriter()
        xmlMarkup = new MarkupBuilder(xmlWriter).rules {
            mkp.comment('THIS IS THE MAIN RULE')
            rule(sentence: mkp.yield('3 > n'))// generate an escaped value
        }

        assert xmlWriter.toString().contains('3 &gt; n')
        assert xmlWriter.toString().contains('<!-- THIS IS THE MAIN RULE -->')

// Using StreamingMarkupBuilder’s 'mkp'
        xml = new StreamingMarkupBuilder().bind {
            records {
                car(name: mkp.yield('3 < 5'))
                car(name: mkp.yieldUnescaped('1 < 3'))
            }
        }

        assert xml.toString().contains('3 &lt; 5')
        assert xml.toString().contains('1 < 3')

// Building MarkupBuilder from DOMToGroovy，读取xml然后转换成MarkupBuilder
        def songs = """
    <songs>
      <song>
        <title>Here I go</title>
        <band>Whitesnake</band>
      </song>
    </songs>
"""

        def builder =
                javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder()

        def inputStream = new ByteArrayInputStream(songs.bytes)
        def document = builder.parse(inputStream)
        def output = new java.io.StringWriter()
        def converter = new org.apache.groovy.xml.tools.DomToGroovy(new PrintWriter(output))
        converter.print(document)

        String xmlRecovered =
                new GroovyShell()
                        .evaluate("""
           def writer1 = new StringWriter()
           def builder1 = new groovy.xml.MarkupBuilder(writer1)
           builder1.${output}

           return writer1.toString()
        """)

        assert new XmlSlurper().parseText(xmlRecovered).song.title.text() == 'Here I go'

// 3.Manipulating XML
        xml = """
<response version-api="2.0">
    <value>
        <books>
            <book id="2">
                <title>Don Quixote</title>
                <author id="1">Miguel de Cervantes</author>
            </book>
        </books>
    </value>
</response>
"""
// Adding nodes
// If you needed to see a node right after creating it then XmlParser should be your choice, but if you’re planning to do many changes to the XML and send the result to another process maybe XmlSlurper would be more efficient.
// You can’t create a new node directly using the XmlSlurper instance, but you can with XmlParser.
        def parser = new groovy.xml.XmlParser()
        response = parser.parseText(xml)
        def numberOfResults = parser.createNode(
                response,
                new QName("numberOfResults"),// 要创建的node的名称
                [:]// 属性
        )

        numberOfResults.value = "1"
        assert response.numberOfResults.text() == "1"

// 用node.appendNode
        parser = new groovy.xml.XmlParser()
        response = parser.parseText(xml)

        response.appendNode(
                new QName("numberOfResults"),
                [:],
                "1"
        )

        response.numberOfResults.text() == "1"
//println groovy.xml.XmlUtil.serialize(response)

// Modifying / Removing nodes,
// using XmlParser and Node,changes the first book information to actually another book.
        response = new groovy.xml.XmlParser().parseText(xml)

/* Use the same syntax as groovy.xml.MarkupBuilder */
        response.value.books.book[0].replaceNode {
// the closure,should follow the same rules as if we were using groovy.xml.MarkupBuilder:
            book(id: "3") {
                title("To Kill a Mockingbird")
                author(id: "3", "Harper Lee")
            }
        }

        def newNode = response.value.books.book[0]

        assert newNode.name() == "book"
        assert newNode.@id == "3"
        assert newNode.title.text() == "To Kill a Mockingbird"
        assert newNode.author.text() == "Harper Lee"
        assert newNode.author.@id.first() == "3"

// using XmlSlurper，重写上面
        response = new XmlSlurper().parseText(books)

/* Use the same syntax as groovy.xml.MarkupBuilder */
        response.value.books.book[0].replaceNode {
            book(id: "3") {
                title("To Kill a Mockingbird")
                author(id: "3", "Harper Lee")
            }
        }

        assert response.value.books.book[0].title.text() == "Don Quixote"

/* That mkp is a special namespace used to escape away from the normal building mode
   of the builder and get access to helper markup methods
   'yield', 'pi', 'comment', 'out', 'namespaces', 'xmlDeclaration' and 'yieldUnescaped' */
        def result = new StreamingMarkupBuilder().bind { mkp.yield response }.toString()
        println result
        def changedResponse = new XmlSlurper().parseText(result)

        assert changedResponse.value.books.book[0].title.text() == "To Kill a Mockingbird"


// adding a new attribute
// XmlParser
        parser = new XmlParser()
        response = parser.parseText(xml)

        response.@numberOfResults = "1"

        assert response.@numberOfResults == "1"

// XmlSlurper
        response = new XmlSlurper().parseText(books)
        response.@numberOfResults = "2"

        assert response.@numberOfResults == "2"

// Printing XML
        response = new groovy.xml.XmlParser().parseText(xml)
        def nodeToSerialize = response.'**'.find { it.name() == 'author' }
        def nodeAsText = groovy.xml.XmlUtil.serialize(nodeToSerialize)

        assert nodeAsText ==
                groovy.xml.XmlUtil.serialize('<?xml version="1.0" encoding="UTF-8"?><author id="1">Miguel de Cervantes</author>')


// ===简单实验
        def rootNode = new XmlSlurper().parseText(
                '<root><one a1="uno!"/><two>Some text!</two></root>')
        assert rootNode.name() == 'root'
        assert rootNode.one.@a1 == 'uno!'
        assert rootNode.one[0].@a1 == 'uno!'
        assert rootNode.two.text() == 'Some text!'
        rootNode.children().each { assert it.name() in ['one', 'two'] }

// 获取多值
        rootNode = new XmlSlurper().parseText(
                '''<root>
           <a>one!</a>
           <a>two!</a>
         </root>''')

        assert rootNode.a.size() == 2
        rootNode.a.each { assert it.text() in ['one!', 'two!'] }


        text = """
<data>
   <common-tables>
    <table name="address"/>
    <table name="phone"/>
  </common-tables>

  <special-tables>
    <table name="person"/>
  </special-tables>

  <other-tables>
    <table name="business"/>
  </other-tables>
</data>
"""

        xml = new XmlSlurper().parse(new ByteArrayInputStream(text.getBytes()))
        def tables = xml.'**'.findAll { it.name() == "table" }.collect { it.@name }
        println tables
    }

    @Test
    void testProperty() {
        def list = [["property": 1], ["property": 2], null, ["property": 3]]
        println list.property
        println list.collect { it?.property }
    }

    @Test
    void testList() {
        def addr1 = new Address(province: 'Guangdong', city: 'Shenzhen')
        def addr2 = new Address(province: 'Gunagdong', city: 'Guangzhou')
        def addr3 = new Address(province: 'Hunan', city: 'Changsha')
        def addr4 = new Address(province: 'Hubei', city: 'Wuhan')

        def books = [new Book(name: 'A glance at Java', authors: [new Author(name: 'Tom', addr: addr1)]),
                     new Book(name: 'Deep into Groovy', authors: [new Author(name: 'Tom', addr: addr1), new Author(name: 'Mike', addr: addr3)]),
                     new Book(name: 'A compare of Struts and Grails', authors: [new Author(name: 'Wallace', addr: addr4), new Author(name: 'Bill', addr: addr2)]),
                     new Book(name: 'learning from Groovy to Grails', authors: [new Author(name: 'Wallace', addr: addr3)])]

        // 找出作者是"Tom"的书籍的书名
        def booksOfTom = books.grep {
            it.authors.any { it.name == 'Tom' }
        }// [Book@32811494, Book@4795ded0]
                .name// 等同于*.name
        println booksOfTom

        // 作者是"Wallace"的作者所在的城市
        def citiesOfWallace = books.authors// [[Author@2eced48b], [Author@47c4ecdc, Author@42f33b5d], [Author@5c8504fd, Author@4b7e96a], [Author@6475472c]]
                .flatten().grep {
            it.name == 'Wallace'
        }.addr.city
        println citiesOfWallace
    }

    @Test
    void testNullProcess() {
        //用Gpath操作bean
        def addr = new Address(province: '1111', city: '2222')
        def person = new Person7(id: 123, name: '333', addr: addr)
        println person.name
        println person.addr.city
        // 若访问不存在的对象可能空指针，需要用?
        def person2 = new Person7(id: 123, name: '333')
        println person2.addr?.city

        //用Gpath操作xml
        def text = """
            <persons>
               <person sex='male'>
                   <id>1</id>
                   <name>Tom</name>
               </person>
               <person sex='female'>
                  <id>2</id>
                  <name>Alice</name>
               </person>
           </persons>
      """
        def node = new XmlSlurper().parseText(text)
        node.person.each {
            println it.name
        }
        // 对xml用gpath操作，不会出现空指针
        println node.p.x == ""
    }

    // 用Gpath访问GroovyBean对象的属性是通过“set”和“get”方法进行的
    @Test
    void testGroovyBean() {
        def person = new Person7(id: 123, name: 'Wallace')
        println person.name
        // 访问属性的真实值
        println person.@name// 这个是操作GroovyBean的属性，用@
    }

    @Test
    void testXml() {
        def text = """
            <persons>
               <person sex='male'>
                   <id>1</id>
                   <name>Tom</name>
               </person>
               <person sex='female'>
                  <id>2</id>
                  <name>Alice</name>
               </person>
           </persons>
      """

        def node = new XmlSlurper().parseText(text)

        node.person.each {
            println it.@sex// 这是获取node的attr
        }
    }

    // 展开操作
    // list*.member 相同 list.collect{ item -> item?.member }
    @Test
    void testSpreadOperator() {
        def list = [new SpreadDotDemo(count: 1), new SpreadDotDemo(count: 2), new SpreadDotDemo(count: 5)]
        println list*.count// [1, 2, 5]
        assert 8 == list*.count.sum()
        assert 8 == list.count.sum()//去掉*也可以的

        // Safe Dereference Operator
        def SpreadDotDemo demo
        println demo?.count
        println demo*.count
    }
}

class Book {
    String name
    List authors
}


class Author {
    String name
    Address addr
}


class Address {
    String province
    String city
}

class Person7 {
    String id
    String name
    Address addr

    String getName() {
        println "getName"
        return 'Tom'
    }
}

class SpreadDotDemo {
    def count
}

