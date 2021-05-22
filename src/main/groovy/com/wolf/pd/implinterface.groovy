package com.wolf.pd

import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import java.awt.Button
import java.awt.FlowLayout
import java.awt.event.ActionListener
import java.awt.event.FocusListener
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener

new Button().addActionListener(//代码块
        { println "You clicked!" } as ActionListener
)
// 使用相同代码+as变成不同对象
//displayMouseLocation = { positionLabel.setText("$it.x, $it.y") }
//frame.addMouseListener(displayMouseLocation as MouseListener)
//frame.addMouseMotionListener(displayMouseLocation as MouseMotionListener)

// 实现接口
handleFocus = [// 只用实现关心的方法,若没有实现的方法被调用则NullPointerException
               focusGained: { msgLoabel.setText("Good to see you!") },
               focusLost  : { msgLoabel.setText("Come back soon") }
]
new Button().addFocusListener(handleFocus as FocusListener)

// 运行时才知道接口的名字,asType参数为：欲实现接口的Class元对象，可以
//events = ['WindowListener', 'ComponentListener'] // 列表可能动态的，也可能来自某些输入
//handler = { msgLabel.setText("$it") }
//for (event in events) {
//    handlerImpl = handler.asType(Class.forName("java.awt.event.${event}"))// 把代码块或映射转化为目标接口类型
//    frame."add${event}"(handlerImpl)
//}

// swing.groovy
frame = new JFrame(size: [300, 300],// 命名参数
        layout: new FlowLayout(),
        defaultCloseOperation: javax.swing.WindowConstants.EXIT_ON_CLOSE)
button = new JButton("click")
positionLabel = new JLabel("")// 构造对象
msgLabel = new JLabel("")
frame.contentPane.add button// 调用get获取contentPane，调用add增加button
frame.contentPane.add positionLabel
frame.contentPane.add msgLabel

// lambda实现接口并转成目标类型
button.addActionListener({ JOptionPane.showMessageDialog(frame, "You clicked!") } as ActionListener)
displayMouseLocation = { positionLabel.setText("$it.x $it.y") }
frame.addMouseListener(displayMouseLocation as MouseListener)// 一段代码转成不同实现类型
frame.addMouseMotionListener(displayMouseLocation as MouseMotionListener)

// 实现接口
handleFocus = [
        focusGained: { msgLabel.setText("Good to see you!") },
        focusLost  : { msgLabel.setText("Come back soon") }
]
button.addFocusListener(handleFocus as FocusListener)

events = ['WindowListener', 'ComponentListener']
handler = { msgLabel.setText("$it") }
for (event in events) {
    handlerImpl = handler.asType(Class.forName("java.awt.event.${event}"))// 动态转成目标类型
    frame."add${event}"(handlerImpl)// 动态调用方法，如addWindowListener并传入参数handlerImpl
}
frame.show()

