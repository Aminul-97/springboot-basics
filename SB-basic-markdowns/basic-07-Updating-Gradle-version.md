
## ✅ Method 1: Update using Gradle Wrapper (Recommended)

This keeps your project consistent across environments.

### Step 1: Check current Gradle version

```bash
./gradlew --version
```

---

### Step 2: Update to the latest version

Run:

```bash
./gradlew wrapper --gradle-version latest
```

Or specify a version (more stable approach):

```bash
./gradlew wrapper --gradle-version 8.7
```

👉 This updates:

* `gradle/wrapper/gradle-wrapper.properties`
* Wrapper scripts (`gradlew`, `gradlew.bat`)

---

### Step 3: Verify update

```bash
./gradlew --version
```

---

## ✅ Method 2: Manually update wrapper config

Edit this file:

```
gradle/wrapper/gradle-wrapper.properties
```

Update:

```properties
distributionUrl=https\://services.gradle.org/distributions/gradle-8.7-bin.zip
```

Then run:

```bash
./gradlew --version
```

---

## ⚠️ Important: Spring Boot compatibility

Not every Gradle version works with every Spring Boot version.

Check compatibility:

| Spring Boot Version | Supported Gradle |
| ------------------- | ---------------- |
| 3.x                 | 7.5+ to 8.x      |
| 2.x                 | 6.x to 7.x       |

👉 If you upgrade Gradle too far, your build may break.

---

## 🧠 Pro Tips

* Always update **Gradle + Spring Boot together** for major upgrades.
* Run after upgrade:

```bash
./gradlew clean build
```

* If something breaks:

```bash
./gradlew build --stacktrace
```

---

## 🚀 Bonus: Find latest stable version

Check:

* [https://gradle.org/releases/](https://gradle.org/releases/)

Avoid `latest` in production—pin a version instead.

---

## 🔧 Common issues after upgrade

* Plugin incompatibility
* Deprecated APIs
* Kotlin/Java version mismatch

Fix by updating:

```gradle
plugins {
    id 'org.springframework.boot' version '3.2.5'
}
```

