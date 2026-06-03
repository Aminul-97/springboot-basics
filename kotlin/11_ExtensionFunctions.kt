fun String.addExcitement(): String {
    return "$this!"
}

fun Int.isEven(): Boolean {
    return this % 2 == 0
}

fun List<String>.printEachItem() {
    for (item in this) {
        println(item)
    }
}

fun main() {
    println("Extension Functions")

    val message = "Kotlin is fun"
    println(message.addExcitement())

    val number = 10
    println("$number is even: ${number.isEven()}")

    val topics = listOf("Variables", "Functions", "Classes")
    topics.printEachItem()
}
