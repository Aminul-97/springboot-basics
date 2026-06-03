open class Animal(
    val name: String
) {
    open fun makeSound() {
        println("$name makes a sound.")
    }
}

class Dog(name: String) : Animal(name) {
    override fun makeSound() {
        println("$name says: Woof!")
    }
}

class Cat(name: String) : Animal(name) {
    override fun makeSound() {
        println("$name says: Meow!")
    }
}

fun main() {
    println("Inheritance")

    val animals: List<Animal> = listOf(
        Dog("Buddy"),
        Cat("Misty"),
        Animal("Some animal")
    )

    for (animal in animals) {
        animal.makeSound()
    }
}
