Use this repo pattern for any new module (example: `analytics`).

## Step - 1
Create module directory structure.

  - `analytics/build.gradle.kts`
  - `analytics/src/main/kotlin/no/eteo/...`
  - Optional: `analytics/src/main/resources/...`

## Step - 2
Register the module in Gradle settings.

  - Edit `settings.gradle.kts` and add: `include("analytics")`

## Step - 3
Add module build config (copy from `comm/payment` style).

- In `analytics/build.gradle.kts`:

```kts
  plugins {
      kotlin("jvm")
      kotlin("plugin.spring")
      id("io.spring.dependency-management")
  }

  dependencies {
      implementation(project(":i18n")) // only if needed
      implementation("org.springframework.boot:spring-boot-starter")
      implementation("org.springframework.boot:spring-boot-starter-jdbc") // for JdbcClient
      implementation("org.springframework.boot:spring-boot-starter-webmvc") // only if controllers
      implementation("org.springframework.boot:spring-boot-starter-thymeleaf") // only if templates
      implementation("org.jetbrains.kotlin:kotlin-reflect")
  }
```
## Step - 4
Wire it into the app module.

If web-app should use it, add in `web-app/build.gradle.kts`: `implementation(project(":analytics"))`

## Step - 5
Follow package/component convention.

Keep classes under `no.eteo...` (same as existing modules), so web-app’s `@SpringBootApplication` scan picks them up automatically (`web-app/src/main/kotlin/no/eteo/App.kt`).

## Step - 6
Add DB changes in Flyway migrations (not inside module).

Put new SQL files in `web-app/src/main/resources/db/migration`, next versioned file like `V11__...sql`.

Keep using `JdbcClient` in module services.

## Step - 7
If UI is involved, keep `Thymeleaf/web` resources in web-app.

  - Templates: `web-app/src/main/resources/templates`
  - Static files: `web-app/src/main/resources/static`
  - Use Thymeleaf URL syntax `@{...}`.

## Step - 8
Verify module is wired.

  - Run:
  ```shell
    ./gradlew clean build
    ./gradlew :web-app:bootRun
  ```

