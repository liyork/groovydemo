package com.wolf.pd

langs = ['C++': 'Stroustrup', 'Java': 'Gosling', 'Lisp': 'McCarthy']
println langs.getClass().name

println langs['Java']
println langs.Java// 把键当做好像map的一个属性，以此访问该键对应的值

println langs.'C++'// 将key以字符串方式告诉map
// 便捷方法
langs.each { entry ->
    println "Language $entry.key was authored by $entry.value"
}
// 两个参数
langs.each { language, author ->
    println "Language $language was authored by $author"
}

println langs.collect { language, author ->
    language.replaceAll("[+]", "P")
}

println "Looking for the first language with name greater than 3 characters"
entry = langs.find { language, author ->// 找到第一个，否则返回null
    language.size() > 3
}
println "Found $entry.key written by $entry.value"

println "Looking for all languages with name greater than 3 characters"
selected = langs.findAll { language, author ->// 所有符合的
    language.size() > 3
}
selected.each { key, value ->
    println "Found $key written by $value"
}

// any判断是否存在任何一个
println langs.any { language, author ->
    language =~ "[~A-Za-z]"
}

// every检查所有
println langs.every { language, author ->
    language =~ "[~A-Za-z]"
}

// 按value的第一个列进行分组
friends = [briang: 'Brain Goetz', brains: 'Brian Sletten',
           davidb: 'David Bock', davidg: 'David Geary',
           scottd: 'Scott Davis', scottl: 'Scott Leverknight']
groupByFirstName = friends.groupBy { it.value.split(' ')[0] }
groupByFirstName.each { firstName, buddies ->
    println "$firstName : ${buddies.collect { key, fullName -> fullName }.join(', ')}"
}




