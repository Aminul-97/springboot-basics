# Setting Up Postgres Database
To work with a PostgreSQL database in a Spring Boot project (using Kotlin), the typical approach is **Spring Data JPA**. It lets you map database tables to Kotlin classes and query them easily.

Below is a **simple demo using a `demo` table** with fields like **name, email, account number**.

---

# 1. Add Required Dependencies

In `build.gradle.kts`:

```kotlin
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.postgresql:postgresql")
}
```

Then **reload Gradle** in IntelliJ IDEA.

---

# 2. Configure PostgreSQL Connection

In `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/demo_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Explanation:

* `ddl-auto=update` → automatically creates tables from entities
* `show-sql=true` → prints SQL queries in logs

---

# 3. Create the Database Table

In PostgreSQL:

```sql
CREATE TABLE demo (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    account_num VARCHAR(50)
);
```

---

# 4. Create an Entity (Table Mapping)

```kotlin
import jakarta.persistence.*

@Entity
@Table(name = "demo")
data class Demo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    val email: String,

    val accountNum: String
)
```

This maps the Kotlin class to the `demo` table.

---

# 5. Create a Repository

```kotlin
import org.springframework.data.jpa.repository.JpaRepository

interface DemoRepository : JpaRepository<Demo, Long>
```

Spring Boot automatically generates:

* `save()`
* `findAll()`
* `findById()`
* `delete()`

---

# 6. Create a Controller

```kotlin
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class DemoController(private val repo: DemoRepository) {

    @PostMapping
    fun addUser(@RequestBody demo: Demo): Demo {
        return repo.save(demo)
    }

    @GetMapping
    fun getUsers(): List<Demo> {
        return repo.findAll()
    }
}
```

---

# 7. Test the API

### Add user

POST:

```
http://localhost:8080/users
```

Body:

```json
{
  "name": "Amin",
  "email": "amin@example.com",
  "accountNum": "ACC12345"
}
```

### Get users

```
http://localhost:8080/users
```

Response example:

```json
[
  {
    "id": 1,
    "name": "Amin",
    "email": "amin@example.com",
    "accountNum": "ACC12345"
  }
]
```

---

# Typical Spring Boot Database Architecture

```
Controller  →  Service (optional)  →  Repository  →  Database
```

---

💡 **Important for real projects**

Most production apps add a **service layer** between controller and repository for business logic.

---

If you'd like, I can also show:

* **The full Spring Boot + PostgreSQL project structure (best practice)**
* **How to build CRUD (Create, Read, Update, Delete)**
* **How to use Spring Boot with PostgreSQL using Docker** (very common in modern setups).

# Flyway DB Migration

To create database tables **automatically using Flyway in a Spring Boot project with PostgreSQL**, you use **versioned SQL migration files**. Flyway runs them automatically when the application starts.

This is the **recommended production approach** instead of `hibernate.ddl-auto`.

---

# 1. Add Flyway Dependency

In `build.gradle.kts`:

```kotlin
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")
}
```

Reload Gradle in IntelliJ IDEA.

---

# 2. Disable Hibernate Table Creation

In `application.properties`:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

Explanation:

* Hibernate **checks schema**
* Flyway **creates schema**

---

# 3. Create Flyway Migration Folder

Spring Boot automatically scans this location:

```
src/main/resources/db/migration
```

---

# 4. Create Migration File

Flyway requires a **specific naming convention**.

Example file:

```
V1__create_demo_table.sql
```

Inside:

```sql
CREATE TABLE demo (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(150),
    account_num VARCHAR(50)
);
```

---

# 5. Run the Application

When your Spring Boot app starts:

1. Flyway checks the database
2. Creates a table called:

```
flyway_schema_history
```

3. Runs the migration

You will see logs like:

```
Flyway Community Edition
Successfully applied 1 migration
```

---

# 6. Verify in PostgreSQL

Connect to PostgreSQL:

```bash
psql -U postgres -d demo_db
```

Check tables:

```sql
\dt
```

You should see:

```
demo
flyway_schema_history
```

---

# 7. Add More Migrations Later

Example new migration:

```
V2__add_phone_column.sql
```

```sql
ALTER TABLE demo
ADD COLUMN phone VARCHAR(20);
```

Flyway runs **only new migrations** automatically.

---

# Typical Flyway Project Structure

```
src
 └─ main
     ├─ kotlin/com/example/demo
     │   ├─ Application.kt
     │   └─ DemoController.kt
     └─ resources
         ├─ db
         │   └─ migration
         │       ├─ V1__create_demo_table.sql
         │       └─ V2__add_phone_column.sql
         └─ application.properties
```

---

# Why Flyway Is Better Than Hibernate Auto-DDL

| Hibernate Auto DDL           | Flyway                 |
| ---------------------------- | ---------------------- |
| Hard to track schema changes | Version controlled     |
| Risky in production          | Safe migrations        |
| No history                   | Full migration history |

---

💡 Since you're learning **Spring Boot + PostgreSQL**, the **best real-world stack** is:

```
Spring Boot
Spring Data JPA
Flyway
PostgreSQL
```

---

If you'd like, I can also show you **how to auto-insert demo data (name, email, account number) using Flyway migrations**, which is very useful for development and testing.


