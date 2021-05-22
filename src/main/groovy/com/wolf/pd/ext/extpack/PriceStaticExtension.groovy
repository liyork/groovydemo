package com.wolf.pd.ext.extpack

class PriceStaticExtension {
    // 第一个参数说明该方法要加入到哪个类上，第二个是实际的参数值
    public static double getPrice(String selfType, String ticker) {// 在String的静态方法上扩展
        println "static ext"
        Double.parseDouble("111")
    }
}
