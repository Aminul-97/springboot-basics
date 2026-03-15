If you're using **Kotlin with Spring Boot**, the Hello World example is very similar, just written in Kotlin instead of Java.

---

## 1. Create a Spring Boot Kotlin Project

Use Spring Initializr and choose:

* **Project:** Gradle or Maven
* **Language:** Kotlin
* **Dependency:** Spring Web

---

## 2. Main Application File

Create `HelloApplication.kt`:

```kotlin
package com.example.hello

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloApplication

fun main(args: Array<String>) {
    runApplication<HelloApplication>(*args)
}
```

---

## 3. Create a Controller

Create `HelloController.kt`:

```kotlin
package com.example.hello

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/")
    fun hello(): String {
        return "Hello, World!"
    }
}
```

---

## 4. Run the Application

If you are using **Gradle**:

```bash
./gradlew bootRun
```

If using **Maven**:

```bash
./mvnw spring-boot:run
```

---

## 5. Open in Browser

Visit:

```
http://localhost:8080
```

Output:

```
Hello, World!
```

---

✅ **Why Kotlin works well with Spring Boot**

* Less boilerplate than Java
* Null safety
* Data classes for APIs
* Official support from JetBrains and the Spring Framework team.

---

If you want, I can also show you a **more “Kotlin-style” Spring Boot example** (using **data classes + JSON API**) which is how most modern Kotlin Spring projects are written.

# FAQ: I'm getting unresolved reference 'web', 'RestController', and 'GetMapping'

If you see **`unresolved reference: web`, `RestController`, or `GetMapping`** in a Kotlin Spring Boot project, it almost always means the **Spring Web dependency is missing**.

Those annotations come from **Spring Web**, so Kotlin cannot find them unless the dependency is added.

---

# 1. Add Spring Web Dependency

## If you are using Gradle (Kotlin DSL)

Open **`build.gradle.kts`** and add:

```kotlin
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
}
```

Then reload Gradle.

---

## If you are using Gradle (Groovy)

`build.gradle`

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

---

## If you are using Maven

Add this dependency inside `<dependencies>`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

---

# 2. Reload Dependencies

After adding the dependency:

**Gradle**

```bash
./gradlew build
```

or refresh Gradle in your IDE.

**Maven**

```bash
mvn clean install
```

---

# 3. Correct Imports in Kotlin

Your controller should import:

```kotlin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
```

Example:

```kotlin
@RestController
class HelloController {

    @GetMapping("/")
    fun hello(): String {
        return "Hello World"
    }
}
```

---

# 4. Common Cause (Very Frequent)

When creating the project in Spring Initializr, the **Spring Web dependency was not selected**.

So the project compiles, but **web annotations are missing**.


