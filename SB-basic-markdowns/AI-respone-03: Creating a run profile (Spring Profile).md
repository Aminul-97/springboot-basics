In a **Spring Boot project**, a **Run Profile** lets you run the application with specific settings (environment variables, JVM options, active Spring profiles like `dev`, `test`, `prod`, etc.). The exact steps depend on the IDE you are using.

I'll show the most common approach using **IntelliJ IDEA** since many Spring developers use it.

---

## 1. Create a Run Profile in IntelliJ IDEA

1. Open your Spring Boot project.
2. Click the **Run/Debug Configurations** dropdown in the top-right corner.
3. Click **Edit Configurations…**
4. Click the **➕ (Add New Configuration)** button.
5. Select **Spring Boot**.
6. Configure the following:

* **Name** → e.g., `MyApp-Dev`
* **Main class** → your main class (e.g., `Application.java` or `Application.kt`)
* **Active profiles** → `dev`, `test`, etc.

Example:

```
Active profiles: dev
```

7. Click **Apply → OK**.
8. Run the profile using the **Run ▶ button**.

---

## 2. Using Spring Profiles

Spring Boot supports environment-based configuration using:

```
spring.profiles.active
```

Example structure:

```
application.yml
application-dev.yml
application-prod.yml
```

Example `application-dev.yml`:

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:mysql://localhost/devdb
```

Then set in your run profile:

```
Active profiles: dev
```

---

## 3. Alternative: Run Profile with Environment Variables

You can also define it manually.

In **Run Configuration → Environment variables**

```
SPRING_PROFILES_ACTIVE=dev
```

---

## 4. Run Profile from Command Line

Without IDE:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

or with **Gradle**:

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

---

## 5. Kotlin Spring Boot Example

If you're using Kotlin:

```kotlin
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
```

Use this class as the **Main class** in the run profile.

---

💡 **Pro tip (used in large SaaS projects):**

Create multiple run profiles like:

* `app-dev`
* `app-staging`
* `app-local-db`
* `app-docker`

This makes switching environments much faster during development.

---

If you want, I can also show you **how to create multiple run profiles with different databases (local, Docker, staging)** — which is very useful for large **Spring Boot SaaS applications**.

