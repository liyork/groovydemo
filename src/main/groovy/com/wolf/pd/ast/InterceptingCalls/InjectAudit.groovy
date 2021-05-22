package com.wolf.pd.ast.InterceptingCalls

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

// 编译时拦截方法并避免运行时拦截造成的轻微影响
// 实现：在类CheckingAccount每个非audit调用都会触发调用audit

// 这是一个全局变换，编译器会在被编译的代码中发现的所有节点上触发该转换
// 在语义分析阶段的最后应用该变换
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class InjectAudit implements ASTTransformation {
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        def checkingAccountClassNode = astNodes[0].classes.find { it.name == 'CheckingAccount' }
        injectAuditMethod(checkingAccountClassNode)
    }

    static void injectAuditMethod(checkingAccountClassNode) {
        def nonAuditMethods = checkingAccountClassNode?.methods.findAll { it.name != 'audit' }
        nonAuditMethods?.each { injectMethodWithAudit(it) }
    }

    static void injectMethodWithAudit(methodNode) {
        //System.out.println("===>${methodNode.parameters}")
        def callToAudit = new ExpressionStatement(
                new MethodCallExpression(
                        new VariableExpression('this'),// 指出这个调用要在此执行环境中的当前对象(this)上进行
                        'audit',// 要调用方法名
                        new ArgumentListExpression(methodNode.parameters)// 参数
                )
        )
        methodNode.code.statements.add(0, callToAudit)// 为方法调用创建AST，插入到目标方法AST中的语句列表中
    }

    // 对injectMethodWithAudit进行优化
    // 使用AstBuilder
    static void injectMethodWithAudit1(methodNode) {
        List<Statement> statements = new AstBuilder().buildFromSpec {// 创建AST
            expression {
                methodCall {
                    variable 'this'
                    constant 'audit'
                    argumentList {
                        methodNode.parameters.each {
                            variable it.name
                        }
                    }
                }
            }
        }
        def callToCheck = statements[0]
        methodNode.code.statements.add(0, callToCheck)
    }

// 使用buildFromString
    static void injectMethodWithAudit2(methodNode) {
        def codeAsString = 'audit(amount)'
        List<Statement> statements = new AstBuilder().buildFromString(codeAsString)

        def callToCheck = statements[0].statements[0].expression
        methodNode.code.statements.add(0, new sun.tools.tree.ExpressionStatement(callToCheck))
    }

// 使用buildFromCode，生成的代码没有迷失在AST结构的细节中
// 不过：仍需要对AST结构了解，从产生的AST中提取正确的部分。
// 对于可以使用该方法生成的代码，也存在限制：生成的代码会放在该变换编译后的代码中。
// ASTBuilder的构建方法本身也要经过一次编译时的AST变换，这限制只能使用groovy编写变换代码，而没有使用ASTBuilder的变换则可以使用任何jvm语言编写
    static void injectMethodWithAudit3(methodNode) {
        List<Statement> statements = new AstBuilder().buildFromString { audit(amount) }
        def callToCheck = statements[0].statements[0].expression
        methodNode.code.statements.add(0, new sun.tools.tree.ExpressionStatement(callToCheck))
    }
}


