package com.wolf.reg

var hostname = "abababa.bbbbbbb\n"
def hostnameMatcher = hostname =~ /(\w+)/
// 从左到右，依次匹配，匹配到就算一个捕获组，对应下面就是第一个[]
println hostnameMatcher[0]// 第一个[]是getAt，第二个[]是数组的下标
println hostnameMatcher[1]// 第二个捕获租

// 若找到则继续，避免空指针
if (hostnameMatcher.find()) println hostnameMatcher[0]
