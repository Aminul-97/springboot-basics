class Book(
    val title: String,
    val author: String
) {
    init {
        println("A Book object was created.")
    }

    constructor(title: String) : this(title, "Unknown Author")

    fun printDetails() {
        println("\"$title\" by $author")
    }
}

fun main() {
    println("Constructors")

    val book1 = Book("Kotlin Basics", "JetBrains")
    val book2 = Book("Mystery Book")

    book1.printDetails()
    book2.printDetails()
}
