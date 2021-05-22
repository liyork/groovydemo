package com.wolf

import java.text.SimpleDateFormat

def format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
def date = new Date()
def time = date.time
println time
def date1 = new Date(1619769348309)
String newDate = format.format(date1)
println newDate

new Date().format("yyyy-MM-dd HH:mm:ss")