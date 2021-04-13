package com.wolf.reg

def hostPattern = ~/\/(.*)$/
// 不能对null进行matcher
//def port = hostPattern.matcher(null)

def portMatcher = hostPattern.matcher("")
// 没有匹配到但是还用了[]当然数组越界
//portMatcher[0][1]

// 进行判断
println portMatcher.matches()
