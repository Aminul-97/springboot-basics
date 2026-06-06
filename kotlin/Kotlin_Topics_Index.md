# Kotlin Topics Index

This index points you to each standalone Kotlin example file. Every `.kt` file has its own `fun main()`, so you can compile and run each one separately.

Example:

```bash
kotlinc 01_Variables.kt -include-runtime -d variables.jar
java -jar variables.jar
```

## 1. Variables (`val` vs `var`)

File: [01_Variables.kt](01_Variables.kt)

Learn how Kotlin stores values using `val` and `var`.

- `val` means the variable cannot be reassigned.
- `var` means the variable can be reassigned.
- Kotlin can often guess the type automatically.

## 2. Functions

File: [02_Functions.kt](02_Functions.kt)

Learn how to create and call functions.

- Functions are declared with `fun`.
- Parameters go inside parentheses.
- Return types are written after `:`.
- Simple functions can use single-expression syntax. 

## 3. Null Safety (`?`, `?.`, `?:`, `!!`)

File: [03_NullSafety.kt](03_NullSafety.kt)

Learn how Kotlin helps prevent null pointer errors.

- `?` allows a variable to hold `null`.
- `?.` safely calls a property or function only if the value is not null.
- `?:` provides a fallback value.
- `!!` forces Kotlin to treat a value as non-null, but it can crash if the value is actually null.

## 4. Classes and Objects

File: [04_ClassesAndObjects.kt](04_ClassesAndObjects.kt)

Learn how to create custom types and single shared objects.

- A class is a blueprint for creating objects.
- An object is a real instance of a class.
- Kotlin also has `object` declarations for single shared objects.

## 5. Data Classes

File: [05_DataClasses.kt](05_DataClasses.kt)

Learn how data classes make storing data easier.

- Data classes automatically create useful functions like `toString()`, `equals()`, and `copy()`.
- They are commonly used for DTOs, API responses, and simple data containers.
- You can destructure data class objects into separate variables.

## 6. Constructors

File: [06_Constructors.kt](06_Constructors.kt)

Learn how objects receive initial values when they are created.

- The primary constructor is written in the class header.
- The `init` block runs when an object is created.
- Secondary constructors can provide alternative ways to create an object.

## 7. Inheritance

File: [07_Inheritance.kt](07_Inheritance.kt)

Learn how one class can reuse and customize another class.

- Kotlin classes are final by default.
- Use `open` to allow inheritance.
- Use `override` to replace behavior from a parent class.

## 8. Interfaces

File: [08_Interfaces.kt](08_Interfaces.kt)

Learn how interfaces define behavior that classes must provide.

- Interfaces describe what a class can do.
- A class can implement an interface.
- Interfaces can contain abstract functions and default function implementations.

## 9. Collections

File: [09_Collections.kt](09_Collections.kt)

Learn how to work with groups of values.

- `List` stores ordered values.
- `Set` stores unique values.
- `Map` stores key-value pairs.
- Functions like `filter` and `map` help transform collections.

## 10. Lambdas and Higher-Order Functions

File: [10_LambdasHigherOrder.kt](10_LambdasHigherOrder.kt)

Learn how functions can be stored in variables and passed to other functions.

- A lambda is a small function expression.
- A higher-order function accepts another function as a parameter or returns a function.
- Collection functions like `map` commonly use lambdas.

## 11. Extension Functions

File: [11_ExtensionFunctions.kt](11_ExtensionFunctions.kt)

Learn how to add new functions to existing types.

- Extension functions let you write `someValue.myFunction()`.
- They do not actually modify the original class.
- They are useful for making code cleaner and easier to read.

## 12. Scope Functions

File: [12_ScopeFunctions.kt](12_ScopeFunctions.kt)

Learn Kotlin's common scope functions.

- `let` is often used with nullable values.
- `run` executes a block and returns the result.
- `apply` configures an object and returns the object.
- `also` performs an extra action and returns the object.
- `with` works with an object and returns a result.

## 13. Coroutines

File: [13_CoroutinesBasics.kt](13_CoroutinesBasics.kt)

Learn the basic idea of coroutines.

- Coroutines help write asynchronous code in a readable way.
- `suspend` marks a function that can pause and resume.
- Real projects often use the `kotlinx.coroutines` library, but this beginner example uses only Kotlin's standard coroutine APIs so it can stay as a single standalone file.
