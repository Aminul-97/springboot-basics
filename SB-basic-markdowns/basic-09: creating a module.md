Got it — Kotlin changes a few important details, but the multi-module idea stays the same.

Below is a **clean step-by-step guide to add a new module to an existing Spring Boot + Gradle (Kotlin DSL) project**.

---

# 1. Your Existing Kotlin Spring Boot Project

Typical structure:

```txt
my-project/
├── build.gradle.kts
├── settings.gradle.kts
├── src/
```

Now you want to add:

```txt
user-module/
```

---

# 2. Create the Module Folder

Inside your project root:

```bash
mkdir user-module
mkdir -p user-module/src/main/kotlin/com/example/user/service
```

---

# 3. Register the Module in `settings.gradle.kts`

Open:

```kotlin
settings.gradle.kts
```

Add:

```kotlin
rootProject.name = "my-project"

include("user-module")
```

That’s what makes Gradle “see” the module.

---

# 4. Create `user-module/build.gradle.kts`

Create:

```txt
user-module/build.gradle.kts
```

Use:

```kotlin
plugins {
    kotlin("jvm") version "2.3.10"
    kotlin("plugin.spring") version "2.3.10"
    id("org.springframework.boot") version "4.1.0-M2"
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation(kotlin("stdlib"))
}

repositories {
    mavenCentral()
}
```

### Important note:

* `apply false` is important because only your **main app module** should run Spring Boot.

---

# 5. Add Module to Main App

Open your root `build.gradle.kts` or app module build file.

Add:

```kotlin
dependencies {
    implementation(project(":user-module"))
}
```

---

# 6. Create a Service in the Module

Inside:

```txt
user-module/src/main/kotlin/com/example/user/service/UserService.kt
```

```kotlin
package com.example.user.service

import org.springframework.stereotype.Service

@Service
class UserService {

    fun getMessage(): String {
        return "Hello from User Module"
    }
}
```

---

# 7. Ensure Spring Boot Scans the Module

### Case A (BEST CASE — recommended)

If your main app package is:

```txt
com.example
```

And module is:

```txt
com.example.user
```

👉 You don’t need anything extra — Spring will auto-scan.

---

### Case B (if packages differ)

In your main app:

```kotlin
@SpringBootApplication(scanBasePackages = ["com.example"])
class Application
```

---

# 8. Use Module Service in Main App

Create controller in main app:

```txt
src/main/kotlin/com/example/controller/TestController.kt
```

```kotlin
package com.example.controller

import com.example.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val userService: UserService
) {

    @GetMapping("/test")
    fun test(): String {
        return userService.getMessage()
    }
}
```

---

# 9. Sync Gradle

Run:

```bash
./gradlew clean build
```

Or in IntelliJ:

> Gradle → Reload All Gradle Projects

---

# 10. Run the App

```bash
./gradlew bootRun
```

---

# 11. Test Endpoint

Open:

```txt
http://localhost:8080/test
```

Expected output:

```txt
Hello from User Module
```

---

# Important Kotlin-Specific Notes

## 1. Use `kotlin("plugin.spring")`

This automatically makes Spring annotations work properly (no need for open classes manually).

---

## 2. Always prefer `implementation(project())`

```kotlin
implementation(project(":user-module"))
```

Not Maven-style dependencies.

---

## 3. Keep module packages aligned

GOOD:

```txt
com.example.user
com.example.payment
```

BAD:

```txt
com.random.user
com.other.app
```

---

## 4. Modules do NOT need Spring Boot plugin

Only main app should have:

```kotlin
id("org.springframework.boot")
```

---

# Recommended Multi-Module Layout (Kotlin Best Practice)

```txt
my-project/
├── app/               (Spring Boot entry point)
├── modules/
│   ├── user-module
│   ├── auth-module
│   └── payment-module
└── shared/
    └── common-module
```

---

# Optional: If You Want a Clean Enterprise Setup

I can help you upgrade this to:

* Hexagonal Architecture (Ports & Adapters)
* Spring Modulith (very clean modular design)
* Microservice-ready Gradle setup
* Shared DTO + API contracts module
* Kafka-based module communication
* Separate database per module

Just tell me 👍

# FAQ 1: Why I'm getting the error? Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.

### The Fix

To fix this, you need to configure Spring Boot to scan both package hierarchies. You can achieve this using either of the
following approaches:

#### Option 1: Specify  scanBasePackages  on  @SpringBootApplication  (Recommended)

Modify SbdummyApplication.kt to include the additional package in the base package scan:

```kotlin
     package com.test.sbdummy

     import org.springframework.boot.autoconfigure.SpringBootApplication
     import org.springframework.boot.runApplication

    -@SpringBootApplication
    +@SpringBootApplication(scanBasePackages = ["com.test.sbdummy", "com.testmodule"])
     class SbdummyApplication

     fun main(args: Array<String>) {
         runApplication<SbdummyApplication>(*args)
     }
```

#### Option 2: Add the  @ComponentScan  annotation

Alternatively, you can achieve the same result using the explicit  @ComponentScan  annotation on SbdummyApplication.kt:

```kotlin
     package com.test.sbdummy

     import org.springframework.boot.autoconfigure.SpringBootApplication
     import org.springframework.boot.runApplication
    +import org.springframework.context.annotation.ComponentScan

     @SpringBootApplication
    +@ComponentScan(basePackages = ["com.test.sbdummy", "com.testmodule"])
     class SbdummyApplication

     fun main(args: Array<String>) {
         runApplication<SbdummyApplication>(*args)
     }
```