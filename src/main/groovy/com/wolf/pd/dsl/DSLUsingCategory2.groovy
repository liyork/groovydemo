package com.wolf.pd.dsl

// 使用4:30
class DateUtil {
    static int getDays(Integer self) { self }

    static Calendar getAgo(Integer self) {
        def date = Calendar.instance
        date.add(Calendar.DAY_OF_MONTH, -self)
        date
    }

    static Date at(Calendar self, Map time) {
        def hour = 0
        def minute = 0
        time.each { key, value ->
            hour = key.toInteger()
            minute = value.toInteger()
        }
        self.set(Calendar.HOUR_OF_DAY, hour)
        self.set(Calendar.MINUTE, minute)
        self.set(Calendar.SECOND, 0)
        self.time
    }
}

use(DateUtil) {// 分类只能在use内用该DSL，离开代码块则注入的方法会被从上下文中丢弃
    println 2.days.ago.at(4: 30)
}

