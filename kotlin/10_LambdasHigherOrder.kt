fun calculate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
    return operation(a, b)
}

fun repeatAction(times: Int, action: (Int) -> Unit) {
    for (count in 1..times) {
        action(count)
    }
}

fun main() {
    println("Lambdas and Higher-Order Functions")

    val add = { a: Int, b: Int -> a + b }
    val multiply = { a: Int, b: Int -> a * b }

    println("10 + 5 = ${calculate(10, 5, add)}")
    println("10 * 5 = ${calculate(10, 5, multiply)}")

    val numbers = listOf(1, 2, 3, 4, 5)
    val squares = numbers.map { number -> number * number }
    println("Squares: $squares")

    repeatAction(3) { count ->
        println("Action number $count")
    }
}
