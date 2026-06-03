fun main() {
    println("Variables: val vs var")

    val name = "Aminul"
    var age = 25

    println("name = $name")
    println("age = $age")

    age = 26
    println("age after changing it = $age")

    val language: String = "Kotlin"
    var score: Int = 10

    score += 5
    println("language = $language")
    println("score = $score")

    // This would not compile because val cannot be reassigned:
    // name = "Someone else"
}
