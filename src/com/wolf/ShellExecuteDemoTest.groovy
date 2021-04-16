package com.wolf

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4)
class ShellExecuteDemoTest {

    @Test
    def void testShellExecute() {
        def p = "ls -R".execute()// 执行shell
        def output = p.inputStream.text
        println(output)

        def fname = "/Users/chaoli/intellijWrkSpace/groovydemo/src/com/wolf/HelloGroovy"
        def f = new File(fname)
        def lines = f.readLines()
        lines.forEach({
            println(it)
        })
        println(f.text)
    }
}
