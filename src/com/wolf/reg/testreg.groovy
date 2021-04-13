package com.wolf.reg
//"/dev/xxx".find((?m)/xxvdsv/){
//    print it[1]
//}
//


def filePath = "/Users/chaoli/intellijWrkSpace/groovydemo/src/com/wolf/reg"
new File(filePath, "reg.groovy").eachLine { line ->
    println line
}