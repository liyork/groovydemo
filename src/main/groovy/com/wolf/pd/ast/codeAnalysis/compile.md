cd groovydemo/src/main

1. groovyc -d classes groovy/com/wolf/pd/ast/codeAnalysis/CodeCheck.groovy
    + 编译生成到classes中
    + 编译groovy/com/wolf/pd/ast/codeAnalysis/CodeCheck.groovy文件
2. 打开org.codehaus.groovy.transform.ASTTransformation中的注释
3. jar -cf checkcode.jar -C classes com -C resources .
    + -c创建jar
    + -f指定文件名
    + -C 跳转到classes目录进行jar
    + -C 跳转到manifest目录进行jar
4. groovyc -classpath checkcode.jar groovy/com/wolf/pd/ast/codeAnalysis/smelly.groovy

// 代码检查发生在编一阶段。