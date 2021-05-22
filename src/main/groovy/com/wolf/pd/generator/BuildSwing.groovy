// groovy为创建swing应用提供了一个生成器
bldr = new groovy.swing.SwingBuilder()
frame = bldr.frame(
        title: 'Swing',
        size: [50, 100],
        layout: new java.awt.FlowLayout(),
        defaultCloseOperation: javax.swing.WindowConstants.EXIT_ON_CLOSE
) {
    lbl = label(text: 'text')
    btn = button(text: 'Click me', actionPerformed: {// 注册事件处理器
        btn.text = 'Clicked'
        lbl.text = 'Groovy!'
    })
}
frame.show()
