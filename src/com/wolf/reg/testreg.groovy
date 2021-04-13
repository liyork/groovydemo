package com.wolf.reg
//"/dev/xxx".find((?m)/xxvdsv/){
//    print it[1]
//}
//


def filePath = "/Users/chaoli/intellijWrkSpace/HelloGroovy/src/hello/groovy"
new File(filePath, "com.wolf").eachLine { line ->
    println line
}