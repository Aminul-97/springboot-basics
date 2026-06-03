fun main() {
    println("Null Safety")

    var normalName: String = "Aminul"
    // normalName = null // This would not compile.

    var nullableName: String? = "Kotlin Learner"
    println("nullableName = $nullableName")

    nullableName = null

    val lengthUsingSafeCall = nullableName?.length
    println("Length using ?. = $lengthUsingSafeCall")

    val lengthUsingElvis = nullableName?.length ?: 0
    println("Length using ?: = $lengthUsingElvis")

    val userInput: String? = "hello"
    println("Uppercase when not null = ${userInput?.uppercase()}")

    val definitelyNotNull: String? = "This has a value"
    println("Using !! = ${definitelyNotNull!!.length}")

    val dangerousValue: String? = null
    // This would crash with KotlinNullPointerException:
    // println(dangerousValue!!.length)

    println("normalName is still safe: $normalName")
    println("dangerousValue = $dangerousValue")
}
