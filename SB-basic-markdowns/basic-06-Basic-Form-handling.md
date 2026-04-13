# 🧩 1. Database Setup (Flyway Migration)

Create a migration file:

```
src/main/resources/db/migration/V1__create_users.sql
```

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    age INT
);
```

👉 Flyway will automatically run this when your app starts.

---

# 🧱 2. Entity (Database Model)

Avoid `data class` here — use a regular class for JPA:

```kotlin
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    var name: String = "",
    var age: Int = 0
)
```

---

# 📦 3. Repository (DB Access)

```kotlin
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
```

---

# 🔄 4. DTO (Form Data)

```kotlin
data class UserForm(
    val name: String = "",
    val age: Int = 0
)
```

---

# ⚙️ 5. Service Layer

```kotlin
import org.springframework.stereotype.Service

@Service
class UserService(private val repo: UserRepository) {

    fun save(form: UserForm) {
        val user = User(
            name = form.name,
            age = form.age
        )
        repo.save(user)
    }
}
```

---

# 🌐 6. Controller (Handles Form)

```kotlin
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class UserController(private val service: UserService) {

    @GetMapping("/form")
    fun showForm(model: Model): String {
        model.addAttribute("userForm", UserForm())
        return "form"
    }

    @PostMapping("/submit")
    fun submitForm(@ModelAttribute userForm: UserForm): String {
        service.save(userForm)
        return "redirect:/success"
    }

    @GetMapping("/success")
    fun success(): String = "success"
}
```

---

# 🎨 7. Thymeleaf Form (HTML)

```
src/main/resources/templates/form.html
```

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<body>

<h2>User Form</h2>

<form th:action="@{/submit}" th:object="${userForm}" method="post">

    <label>Name:</label>
    <input type="text" th:field="*{name}" />

    <label>Age:</label>
    <input type="number" th:field="*{age}" />

    <button type="submit">Submit</button>

</form>

</body>
</html>
```

---

# ✅ 8. Success Page

```html
<h2>Saved successfully!</h2>
```

---

# 🔁 Full Flow (Important)

1. User opens `/form`
2. Thymeleaf renders form
3. User submits form
4. `@ModelAttribute` maps → `UserForm`
5. Service converts → `User` entity
6. Repository saves → DB
7. Redirect to success page

---

# ⚠️ Common Mistakes (Avoid These)

* ❌ Using `@RequestBody` instead of `@ModelAttribute` (that’s for JSON APIs)
* ❌ Using `data class` for JPA entity
* ❌ Missing `th:object` in form
* ❌ Not matching field names (`name`, `age`)

---

# 🚀 Pro Upgrade (Validation)

```kotlin
data class UserForm(
    @field:NotBlank
    val name: String = "",

    @field:Min(18)
    val age: Int = 0
)
```

Controller:

```kotlin
fun submitForm(
    @Valid @ModelAttribute userForm: UserForm,
    result: BindingResult
): String {
    if (result.hasErrors()) return "form"

    service.save(userForm)
    return "redirect:/success"
}
```

---

If you want, I can show:

* Edit/update form (real CRUD)
* Delete + list users in Thymeleaf
* Bootstrap-styled form UI
* Clean architecture for production apps

Just tell me 👍

