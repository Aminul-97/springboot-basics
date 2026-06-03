class Person {
    var name: String = "Unknown"
    var age: Int = 0

    fun introduce() {
        println("Hi, I am $name and I am $age years old.")
    }
}

object AppInfo {
    val appName = "Kotlin Beginner Examples"

    fun printInfo() {
        println("App name: $appName")
    }
}

fun main() {
    println("Classes and Objects")

    val person = Person()
    person.name = "Aminul"
    person.age = 25
    person.introduce()

    AppInfo.printInfo()
}
