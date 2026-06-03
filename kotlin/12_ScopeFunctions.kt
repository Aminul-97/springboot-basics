data class User(
    var name: String = "",
    var age: Int = 0,
    var city: String = ""
)

fun main() {
    println("Scope Functions")

    val nullableName: String? = "Aminul"
    nullableName?.let { name ->
        println("let: runs only when nullableName is not null. Name length = ${name.length}")
    }

    val runResult = run {
        val first = 10
        val second = 20
        first + second
    }
    println("run: result = $runResult")

    val user = User().apply {
        name = "Aminul"
        age = 25
        city = "Dhaka"
    }
    println("apply: $user")

    val sameUser = user.also {
        println("also: user was created as $it")
    }
    println("also returns the same object: ${sameUser == user}")

    val description = with(user) {
        "$name is $age years old and lives in $city."
    }
    println("with: $description")
}
