package com.wolf.pd.ext.extpack
// 在String实例上扩展方法，见org.codehaus.groovy.runtime.ExtensionModule中
class PriceExtension {
    // 第一个参数说明该方法将被添加到哪个类上
    public static double getPrice(String self) {
        println "instance ext"
        Double.parseDouble("222")
    }
}


