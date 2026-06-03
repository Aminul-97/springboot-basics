fun greet(name: String): String {
    return "Hello, $name!"
}

fun add(a: Int, b: Int): Int {
    return a + b
}

fun multiply(a: Int, b: Int): Int = a * b

fun printWelcomeMessage() {
    println("Welcome to Kotlin functions.")
}

fun main() {
    println("Functions")

    printWelcomeMessage()

    val message = greet("Aminul")
    println(message)

    println("2 + 3 = ${add(2, 3)}")
    println("4 * 5 = ${multiply(4, 5)}")
}
