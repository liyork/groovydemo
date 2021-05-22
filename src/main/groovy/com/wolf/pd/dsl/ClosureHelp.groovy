// 闭包与DSL

// with可以在一个比包内辅助实现委托调用，并提供一个执行上下文。
// 想创建一种自然流动的语法，但是不想创建一个PizzaShop实例，因为太偏于实现细节。
// 希望上下文是隐士的。
time = getPizza {
    setSize Size.LARGE
    setCurst Crust.THIN
    setTopping "Olives", "Onions", "Bell Pepper"
    setAddress "101 Main St., ..."
    setCard(CardType.VISA, "1234-1234-1234-1234")
}
printf "Pizza will arrive in %d minutes\n", time

class PizzaShop {

}
// getPizza方法接受一个闭包，闭包内使用PizzaShop类的实例方法来实现功能。
// 这里的PizzaShop实例是隐式的。delegate负责将方法路由到隐式的实例。
def getPizza(closure) {
    PizzaShop pizzaShop = new PizzaShop()
    closure.delegate = pizzaShop
    closure()
}