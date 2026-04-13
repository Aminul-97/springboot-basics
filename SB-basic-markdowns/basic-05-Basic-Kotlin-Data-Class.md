
# 1. Basic Kotlin Data Class (DTO)

This is the most common use:

```kotlin
data class UserRequest(
    val name: String,
    val age: Int
)
```

### Controller Example

```kotlin
@RestController
@RequestMapping("/users")
class UserController {

    @PostMapping
    fun createUser(@RequestBody request: UserRequest): String {
        return "User: ${request.name}, Age: ${request.age}"
    }
}
```

👉 Spring Boot (via Jackson) automatically:

* Converts JSON → `UserRequest`
* No setters/getters needed

---

# 2. Handling Optional Fields

```kotlin
data class UserRequest(
    val name: String,
    val age: Int?,
    val email: String? = null
)
```

✔ Use `?` for nullable fields
✔ Provide defaults to avoid errors when fields are missing

---

# 3. Validation (Very Important)

Add validation annotations:

```kotlin
import jakarta.validation.constraints.*

data class UserRequest(
    @field:NotBlank
    val name: String,

    @field:Min(18)
    val age: Int
)
```

### Enable Validation

```kotlin
@PostMapping
fun createUser(@Valid @RequestBody request: UserRequest): String {
    return "Valid user"
}
```

👉 Note: `@field:` is required in Kotlin

---

# 4. Response Data Class

```kotlin
data class UserResponse(
    val id: Long,
    val name: String
)
```

```kotlin
@GetMapping("/{id}")
fun getUser(@PathVariable id: Long): UserResponse {
    return UserResponse(id, "Aminul")
}
```

---

# 5. Data Class vs Entity (IMPORTANT ⚠️)

You *can* use data classes for JPA entities, but be careful.

### Example:

```kotlin
@Entity
data class User(
    @Id @GeneratedValue
    val id: Long = 0,

    val name: String,
    val age: Int
)
```

### ⚠️ Problems:

* `data class` generates `equals/hashCode` → can break JPA
* Immutable `val` fields → JPA sometimes needs `var`
* Lazy loading issues

👉 Best practice:

* ✅ Use `data class` for **DTOs**
* ⚠️ Use regular class for **Entities** (recommended)

---

# 6. Mapping DTO ↔ Entity

```kotlin
fun UserRequest.toEntity() = User(
    name = this.name,
    age = this.age
)

fun User.toResponse() = UserResponse(
    id = this.id,
    name = this.name
)
```

---

# 7. JSON Example

### Request:

```json
{
  "name": "Aminul",
  "age": 25
}
```

### Automatically becomes:

```kotlin
UserRequest("Aminul", 25)
```

---

# 8. Pro Tips (From Real Projects)

* Use **data classes only for API layer**
* Keep them **immutable (`val`)**
* Always add **validation**
* Use **separate request & response classes**
* Avoid exposing database entities directly

---

# Clean Architecture Structure

```
controller/
  UserController.kt

dto/
  UserRequest.kt
  UserResponse.kt

entity/
  User.kt

service/
  UserService.kt
```

---

If you want next level, I can show:

* Full CRUD example (Spring Boot + Kotlin)
* Error handling for validation
* Best practices for large-scale apps

Just tell me 👍

