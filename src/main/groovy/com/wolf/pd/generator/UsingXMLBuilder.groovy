bldr = new groovy.xml.MarkupBuilder()
// 调用languages()方法
bldr.languages {// 闭包，体用了一个内部上下文，被调用的任何不存在的方法，都被假定为一个子元素
    // 调用方法时传递的是Map参数，被当做元素的属性。调用单个参数值，表示的是元素内容
    language(name: 'C++') { author('Stroustrup') }
    language(name: 'Jave') { author('Gosling') }
    language(name: 'List') { author('McCarthy') }
}

