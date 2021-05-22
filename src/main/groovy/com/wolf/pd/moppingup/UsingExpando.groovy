// Expando提供了动态合成类的能力
// 运行时合成类

// 属性
carA = new Expando()
carB = new Expando(year: 2012, miles: 0)
carA.year = 2012
carA.miles = 10

println "carA: " + carA
println "carB: " + carB

// 方法
car = new Expando(year: 2012, miles: 0, turn: { println 'turning...' })
car.drive = {
    miles += 10
    println "$miles miles driven"
}
car.drive()
car.turn()


