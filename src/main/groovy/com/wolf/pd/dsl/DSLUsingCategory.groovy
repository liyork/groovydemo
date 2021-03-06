// 分类与DSL

// 实现2.days.ago.at(4.30)流畅调用
// 使用分类注入属性days，
class DateUtil {
    static int getDays(Integer self) { self }

    static Calendar getAgo(Integer self) {
        def date = Calendar.instance
        date.add(Calendar.DAY_OF_MONTH, -self)
        date
    }

    static Date at(Calendar self, Double time) {
        def hour = (int) (time.doubleValue())
        def minute = (int) (Math.round(time.doubleValue() - hour) * 100)
        self.set(Calendar.HOUR_OF_DAY, hour)
        self.set(Calendar.MINUTE, minute)
        self.set(Calendar.SECOND, 0)
        self.time
    }
}

use(DateUtil) {
    println 2.days.ago.at(4.30)
}