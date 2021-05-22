import groovy.json.JsonSlurper

def sluper = new JsonSlurper()
def person = sluper.parse(new FileReader('person.json'))// 返回包含数据的hashMap实例
println "${person.first} ${person.last} is interested in ${person.sigs.join(', ')}"

// 还可以用parseText读取包含在String中的json数据
// 用parse从Reader或文件中读取json数据