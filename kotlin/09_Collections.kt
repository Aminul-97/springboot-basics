fun main() {
    println("Collections")

    val readOnlyNumbers = listOf(1, 2, 3, 4, 5)
    println("List: $readOnlyNumbers")
    println("First item: ${readOnlyNumbers.first()}")

    val mutableNames = mutableListOf("Aminul", "Kotlin")
    mutableNames.add("Spring Boot")
    println("Mutable list: $mutableNames")

    val uniqueNumbers = setOf(1, 1, 2, 3, 3)
    println("Set removes duplicates: $uniqueNumbers")

    val capitals = mapOf(
        "Bangladesh" to "Dhaka",
        "Japan" to "Tokyo",
        "France" to "Paris"
    )
    println("Capital of Bangladesh: ${capitals["Bangladesh"]}")

    val evenNumbers = readOnlyNumbers.filter { number -> number % 2 == 0 }
    val doubledNumbers = readOnlyNumbers.map { number -> number * 2 }

    println("Even numbers: $evenNumbers")
    println("Doubled numbers: $doubledNumbers")
}
