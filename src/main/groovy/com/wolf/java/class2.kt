package com.wolf.java

open class JaveCodeWithHeavierDependencies2 {
    open fun someAction(): Int {
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return (Math.random() * 100).toInt()
    }

    fun myMethod() {
        val value = someAction() + 10
        println(value)
    }
}