import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.startCoroutine

suspend fun fetchUserName(): String {
    println("fetchUserName started")

    return suspendCoroutine { continuation ->
        println("Pretend this is waiting for network or database work.")
        continuation.resume("Aminul")
    }
}

suspend fun printUserGreeting() {
    val name = fetchUserName()
    println("Hello, $name!")
}

fun main() {
    println("Coroutines: basic understanding")

    val continuation = object : Continuation<Unit> {
        override val context = EmptyCoroutineContext

        override fun resumeWith(result: Result<Unit>) {
            result
                .onSuccess { println("Coroutine finished successfully.") }
                .onFailure { error -> println("Coroutine failed: ${error.message}") }
        }
    }

    ::printUserGreeting.startCoroutine(continuation)
}
