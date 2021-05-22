package com.wolf.pd.ast.local

import com.sun.org.apache.bcel.internal.Constants
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

// 让AST变换是局部的。不必创建额外的清单文件

// 会触发变换的注解
@Retention(RetentionPolicy.SOURCE)
// 指明此注解只能放在类上
@Target([ElementType.TYPE])
// 告知编译器，参数中变换类应该作用于使用这个注解标记的类
@GroovyASTTransformationClass("com.wolf.pd.ast.local.EAMTransformation")
public @interface EAM {}

// 将静态的use()方法插入到被注解的类中。
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class EAMTransformation implements ASTTransformation {
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        astNodes.findAll { node -> node instanceof ClassNode }.each { classNode ->
            def useMethodBody = new AstBuilder().buildFromCode {
                def instance = newInstance()
                try {
                    instance.open()// 假设目标类有open和close方法
                    instance.with block
                } finally {
                    instance.close()
                }
            }
            def useMethod = new MethodNode(
                    'use',// 方法名
                    Constants.ACC_PUBLIC | Constants.ACC_STATIC,// 方法修饰符
                    ClassHelper.OBJECT_TYPE,// 返回参数类型
                    [new Parameter(ClassHelper.OBJECT_TYPE, 'block')] as Parameter[],// 入参
                    [] as ClassNode[],// 异常
                    useMethodBody[0]// 方法体
            )
            classNode.addMethod(useMethod)
        }
    }
}