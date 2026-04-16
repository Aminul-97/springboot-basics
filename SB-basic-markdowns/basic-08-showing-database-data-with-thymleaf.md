To show database data in a **Spring Boot + Flyway + Thymeleaf** setup, you basically follow this flow:

> **Database (Flyway migrations) → Entity → Repository → Service → Controller → Thymeleaf View**

Let’s walk through it step-by-step with a clean, practical example.

---

# 1. Flyway: Create & Seed Your Table

Flyway handles schema + initial data.

**`src/main/resources/db/migration/V1__init.sql`**

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);

INSERT INTO users (name, email) VALUES
('Amin', 'amin@example.com'),
('Sara', 'sara@example.com');
```

---

# 2. Create Entity

Map your table to a Java class.

```java
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // getters & setters
}
```

---

# 3. Create Repository

Spring Data JPA will fetch data automatically.

```java
import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository : JpaRepository<User, Long>
```

---

# 4. Create Service (Optional but recommended)

```java
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }
}
```

---

# 5. Controller: Send Data to Thymeleaf

```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", service.getAllUsers());
        return "users"; // refers to users.html
    }
}
```

---

# 6. Thymeleaf Template

**`src/main/resources/templates/users.html`**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Users</title>
</head>
<body>

<h2>User List</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
    </tr>

    <tr th:each="user : ${users}">
        <td th:text="${user.id}"></td>
        <td th:text="${user.name}"></td>
        <td th:text="${user.email}"></td>
    </tr>

</table>

</body>
</html>
```

---

# 7. application.properties (Important)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/testdb
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

spring.flyway.enabled=true
```

---

# How It Works (Simple Mental Model)

* **Flyway** → creates + seeds DB
* **JPA Repository** → fetches data
* **Controller** → puts data into `Model`
* **Thymeleaf** → renders it using `th:each`

---

# Common Mistakes (Watch Out ⚠️)

* ❌ Returning JSON instead of template → use `@Controller`, not `@RestController`
* ❌ Wrong template name → must match `return "users"`
* ❌ Missing Thymeleaf dependency
* ❌ Flyway not running → check migration naming (`V1__...`)
* ❌ `${users}` mismatch with `model.addAttribute("users", ...)`

---

# Bonus: Quick Debug Tip

If nothing shows:

```java
System.out.println(service.getAllUsers());
```

Or enable:

```properties
spring.jpa.show-sql=true
```

---

If you want, I can:

* Add pagination
* Add CRUD (create/edit/delete)
* Show API + frontend combo
* Or debug your exact code (just paste it)

