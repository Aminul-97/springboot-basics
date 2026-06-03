data class Student(
    val id: Int,
    val name: String,
    val grade: String
)

fun main() {
    println("Data Classes")

    val student1 = Student(1, "Aminul", "A")
    val student2 = Student(1, "Aminul", "A")
    val student3 = student1.copy(grade = "A+")

    println(student1)
    println("student1 == student2: ${student1 == student2}")
    println("Copied student: $student3")

    val (id, name, grade) = student1
    println("Destructured values: id=$id, name=$name, grade=$grade")
}
