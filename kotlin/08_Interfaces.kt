interface Drivable {
    val maxSpeed: Int

    fun drive()

    fun stop() {
        println("The vehicle stopped.")
    }
}

class Car(
    override val maxSpeed: Int
) : Drivable {
    override fun drive() {
        println("The car is driving up to $maxSpeed km/h.")
    }
}

class Bicycle(
    override val maxSpeed: Int
) : Drivable {
    override fun drive() {
        println("The bicycle is moving up to $maxSpeed km/h.")
    }
}

fun main() {
    println("Interfaces")

    val vehicles: List<Drivable> = listOf(
        Car(180),
        Bicycle(30)
    )

    for (vehicle in vehicles) {
        vehicle.drive()
        vehicle.stop()
    }
}
