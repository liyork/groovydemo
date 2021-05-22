package com.wolf.pd.ast.codeAnalysis

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.SyntaxException
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

// 若想检查AST，使用注解GroovyASTTransformation告知编译器
// groovy编译器包括多个阶段，支持开发者在任何阶段介入：初始化、解析、转换、语义分析、规范化、指令选择、class生成、输出和结束
// 首个合理的时机是在语义分析之后，AST在此时被创建出来。
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
// 指明该AST变换必须在语义分析阶段之后应用
class CodeCheck implements ASTTransformation {
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {// 随着编译器到达指定阶段，他将调用用于变换的visit
        sourceUnit.ast.classes.each { classNode ->
            classNode.visitContents(new OurClassVisitor(sourceUnit))
        }
    }
}

// AST变换API提供了GroovyClassVisitor的访问者，简化了操作，对于每个类节点、方法节点、字段节点等，该访问者的方法将被调用。
// 当导航到一个类中的类元素时，AST变换API将调用访问者的方法
class OurClassVisitor implements GroovyClassVisitor {
    SourceUnit sourceUnit

    OurClassVisitor(theSourceUnit) { sourceUnit = theSourceUnit }

    private void reportError(message, lineNumber, columnNumber) {
        sourceUnit.addError(new SyntaxException(message, lineNumber, columnNumber))
    }

    void visitMethod(MethodNode node) {// 检查只有一个字母的方法名。
        if (node.name.size() == 1) {
            reportError "Make method name descriptive, avoid single letter names",
                    node.lineNumber, node.columnNumber
        }
        node.parameters.each { parameter ->
            if (parameter.name.size() == 1) {
                reportError "Single letter parameters are mornally wrong!",
                        parameter.lineNumber, parameter.columnNumber
            }
        }
    }

    void visitClass(ClassNode node) {

    }

    void visitConstructor(ConstructorNode node) {

    }

    void visitField(FieldNode node) {

    }

    void visitProperty(PropertyNode node) {

    }
}
// 在编译过程中，必须帮助编译器发现并应用这个AST变换。
// 这里将使用一种全局变换的方式，此时变换可以应用于任何代码片段中，不需要代码上的任何特殊标记。
// groovy编译器将在classpath中的每个jar文件中查找这样的全局变换。
// 为使动查找更高效，编译器期望在各个jar文件中的一个位置(manifest/META-INF/services/org.codehaus.groovy.transform.ASTTransformation
// 中声明这个变换的类名。


