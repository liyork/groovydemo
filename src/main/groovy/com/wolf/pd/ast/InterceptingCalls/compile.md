cd groovydemo/src/main

1. groovyc -d classes groovy/com/wolf/pd/ast/InterceptingCalls/InjectAudit.groovy
2. jar -cf injectAudit.jar -C classes com -C resources .
3. groovy -classpath injectAudit.jar groovy/com/wolf/pd/ast/InterceptingCalls/UsingCheckingAccount.groovy
