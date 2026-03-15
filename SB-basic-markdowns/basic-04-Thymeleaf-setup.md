To include **Thymeleaf** in a Spring Boot project (with Kotlin), you just need to add the **Spring Boot Thymeleaf starter** and create templates.

---

# 1. Add Thymeleaf Dependency

## Gradle Kotlin DSL (`build.gradle.kts`)

```kotlin
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
}
```

Also ensure you have the web starter:

```kotlin
implementation("org.springframework.boot:spring-boot-starter-web")
```

After adding it, **reload Gradle** in IntelliJ IDEA.

---

# 2. Create Templates Folder

Spring Boot automatically looks for templates in:

```
src/main/resources/templates
```

Create a file:

```
src/main/resources/templates/index.html
```

Example:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Spring Boot Thymeleaf</title>
</head>
<body>
<h1 th:text="'Hello, ' + ${name}"></h1>
</body>
</html>
```

---

# 3. Create a Controller

Example Kotlin controller:

```kotlin
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("name", "Spring Boot")
        return "index"
    }
}
```

Important:

* `@Controller` → returns **HTML view**
* `@RestController` → returns **JSON**

So for Thymeleaf you must use **`@Controller`**.

---

# 4. Run the Application

Start your app and open:

```
http://localhost:8080
```

Output:

```
Hello, Spring Boot
```

---

# 5. Optional (Enable Hot Reload for Templates)

Add:

```kotlin
developmentOnly("org.springframework.boot:spring-boot-devtools")
```

This reloads Thymeleaf pages automatically during development.

---

✅ **Typical Spring Boot + Thymeleaf project structure**

```
src
 └─ main
     ├─ kotlin/com/example/demo
     │   ├─ Application.kt
     │   └─ HomeController.kt
     └─ resources
         ├─ templates
         │   └─ index.html
         └─ application.properties
```

---

💡 If you want, I can also show you:

* **How to build a full CRUD web app with Thymeleaf + Spring Boot**
* **How Thymeleaf works internally with Spring MVC**
* **How to use Thymeleaf layouts (like React components)** which most beginners miss.

