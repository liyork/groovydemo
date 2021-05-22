// 解析car.dat然后动态构造car对象，一旦对象创建出来，就可以动态地访问其属性或调用其方法

data = new File('car.dat').readLines()

props = data[0].split(', ')
data -= data[0]

def averageMilesDrivenPerYear = { miles.toLong() / (2008 - year.toLong()) }

cars = data.collect {// 构造每一个car，有三个属性和值
    car = new Expando()
    it.split(", ").eachWithIndex { value, index ->
        car[props[index]] = value
    }
    car.ampy = averageMilesDrivenPerYear
    car
}
props.each { name -> print " $name" }
println " Avg. MPY"

ampyMethod = 'ampy'
cars.each { car ->
    for (String property : props) {
        print " ${car[property]}"
    }
    println car."$ampyMethod"()
}

car = cars[0]
println "$car.miles $car.year $car.make ${car.ampy()}"


