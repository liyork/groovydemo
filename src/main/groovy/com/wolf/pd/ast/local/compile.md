cd groovydemo/src/main
groovyc -d classes groovy/com/wolf/pd/ast/local/*.groovy
jar -cf eam.jar -C classes com