package com.wolf.pd.ext.usepack

def ticker = "ORCL"
println "Price for $ticker using instance method i ${String.getPrice(ticker)}"// 调用实例方法
println "Price for $ticker using static method i ${ticker.getPrice()}"// 调用静态方法