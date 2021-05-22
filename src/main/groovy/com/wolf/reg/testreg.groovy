package com.wolf.reg
//"/dev/xxx".find((?m)/xxvdsv/){
//    print it[1]
//}
//


def filePath = "/Users/chaoli/intellijWrkSpace/groovydemo/src/com/wolf/reg"
new File(filePath, "reg.groovy").eachLine { line ->
    println line
}

// 限制结果，即按照:分割，够了数量就返回了
println "11.11.11.11.11".split('\\.', 2)
println "1.11.11.11.11".split('\\.', 2)
println "1111.11.11.11.11".split('\\.', 2)

println "111 / 48".trim()