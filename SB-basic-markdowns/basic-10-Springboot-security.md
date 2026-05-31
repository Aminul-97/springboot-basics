# Springboot Security

To set up security in a Kotlin + Gradle Spring Boot application, the standard approach is to use Spring Security.

Here’s a clean modern setup using:

* Kotlin
* Gradle Kotlin DSL (`build.gradle.kts`)
* Spring Boot 3+
* Stateless API security
* Basic authentication example
* Password encoding

---

# 1. Add Spring Security Dependency

In your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
}
```

Then refresh Gradle.

---

# 2. Create a Security Configuration

Spring Boot 3 uses `SecurityFilterChain` instead of the older `WebSecurityConfigurerAdapter`.

Create:

```kotlin
package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/public/**").permitAll()
                    .anyRequest().authenticated()
            }
            .httpBasic(Customizer.withDefaults())

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
```

---

# 3. Create a Test Controller

```kotlin
package com.example.demo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/public/hello")
    fun publicHello(): String {
        return "Public endpoint"
    }

    @GetMapping("/private/hello")
    fun privateHello(): String {
        return "Private endpoint"
    }
}
```

---

# 4. Configure Default User

Add to `application.yml`:

```yaml
spring:
  security:
    user:
      name: admin
      password: admin123
```

Or `application.properties`:

```properties
spring.security.user.name=admin
spring.security.user.password=admin123
```

---

# 5. Run the Application

Now:

| Endpoint         | Access                  |
| ---------------- | ----------------------- |
| `/public/hello`  | Public                  |
| `/private/hello` | Requires authentication |

Test with curl:

```bash
curl http://localhost:8080/public/hello
```

Protected route:

```bash
curl -u admin:admin123 http://localhost:8080/private/hello
```

---

# 6. Recommended Next Steps

For real-world applications, you usually add:

* JWT authentication
* Database-backed users
* Role-based authorization
* OAuth2 / Google login
* Method-level security
* Refresh tokens

---

# 7. JWT Setup (Recommended for APIs)

For modern REST APIs, replace Basic Auth with JWT.

Add dependency:

```kotlin
implementation("io.jsonwebtoken:jjwt-api:0.12.5")
runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
```

Typical JWT flow:

```text
Login -> Generate JWT -> Client stores token
-> Send Authorization: Bearer TOKEN
-> Spring validates token
```

---

# 8. Add Method-Level Security

Enable:

```kotlin
@EnableMethodSecurity
@Configuration
class SecurityConfig
```

Example:

```kotlin
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin")
fun adminOnly(): String {
    return "Admin"
}
```

---

# 9. Important Security Best Practices

* Never store plain text passwords
* Always use BCrypt
* Disable CSRF only for stateless APIs
* Use HTTPS in production
* Prefer JWT for frontend/mobile apps
* Add rate limiting for auth endpoints
* Store secrets in environment variables

---

# 10. Official Documentation

* [Spring Security Documentation](https://spring.io/projects/spring-security?utm_source=chatgpt.com)
* [Spring Boot Documentation](https://spring.io/projects/spring-boot?utm_source=chatgpt.com)
* [Spring Security Reference Guide](https://docs.spring.io/spring-security/reference/index.html?utm_source=chatgpt.com)

## FAQ: How to authorize multiple links?

In Spring Security, you can authorize multiple URLs by passing multiple patterns to `requestMatchers()`.

### Permit multiple public endpoints

```kotlin
@Bean
fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it
                .requestMatchers(
                    "/",
                    "/login",
                    "/register",
                    "/api/public/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()
                .anyRequest().authenticated()
        }

    return http.build()
}
```

### Different permissions for different routes

```kotlin
@Bean
fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
        .csrf { it.disable() }
        .authorizeHttpRequests {
            it
                .requestMatchers("/", "/login", "/register")
                .permitAll()

                .requestMatchers("/admin/**")
                .hasRole("ADMIN")

                .requestMatchers("/user/**")
                .hasAnyRole("USER", "ADMIN")

                .requestMatchers("/api/**")
                .authenticated()

                .anyRequest()
                .denyAll()
        }

    return http.build()
}
```

### Using Kotlin arrays

If you have many routes:

```kotlin
private val publicEndpoints = arrayOf(
    "/",
    "/login",
    "/register",
    "/health",
    "/swagger-ui/**",
    "/v3/api-docs/**"
)

@Bean
fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
        .authorizeHttpRequests {
            it.requestMatchers(*publicEndpoints)
                .permitAll()
                .anyRequest()
                .authenticated()
        }

    return http.build()
}
```

Notice the `*` before `publicEndpoints`. That's Kotlin's spread operator, which passes the array as individual arguments.

### Common wildcard patterns

| Pattern     | Matches                               |
| ----------- | ------------------------------------- |
| `/api/*`    | `/api/users`, `/api/posts`            |
| `/api/**`   | `/api/users/1`, `/api/admin/settings` |
| `/admin/**` | Everything under `/admin`             |
| `/**`       | All routes                            |

For a REST API, a common setup is:

```kotlin
.requestMatchers(
    "/auth/**",
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/actuator/health"
).permitAll()
.anyRequest().authenticated()
```

If you're using JWT authentication, I can also show a complete Kotlin + Spring Boot 3 security configuration with JWT filters.

# FAQ; Why I'm getting: Encoded password does not look like BCrypt. I was using the password defined in application.yml

──────
### Why this happens

1. In your  SecurityConfig.kt , you have registered a  BCryptPasswordEncoder  as a Spring  @Bean :
   @Bean
   fun passwordEncoder(): PasswordEncoder {
   return BCryptPasswordEncoder()
   }

2. In your  application.yaml  (and  application-dev.yaml ), the default in-memory user is configured with the raw plaintext
   password  abc123 :
   spring:
   security:
   user:
   password: abc123

3. During authentication, Spring Security uses the registered  BCryptPasswordEncoder  bean to match the entered password against
   the configured in-memory password. Because  abc123  is a raw plaintext string and does not conform to the BCrypt hashing format
   (which starts with  $2a$ ,  $2b$ , or  $2y$ ), the encoder fails and outputs:
   │  Encoded password does not look like BCrypt

──────
### How to solve it

Here are the standard solutions you can choose from to fix this issue (without us changing any of your code or running any git
commands).

#### Option 1: Hashing the Password (Recommended)

If you want to keep using  BCryptPasswordEncoder  (which is standard and secure), you need to replace the plaintext password in
your configuration files with a valid BCrypt hash of  abc123 .

An example of a valid BCrypt hash for  abc123  is:

```yaml
    $2a$10$wK1mJz4j0g0mS3u7S/qWDeK/7w1l/lZ5xV5M.v0Yw7y5oW3uE2eTq
```

Update your  application.yaml  (and  application-dev.yaml ) to use the BCrypt hash instead:

```yaml
    spring:
      security:
        user:
          name: admin
          password: "$2a$10$wK1mJz4j0g0mS3u7S/qWDeK/7w1l/lZ5xV5M.v0Yw7y5oW3uE2eTq"
```

(When you log in, you will still type  abc123  in the browser or HTTP basic auth prompt, and Spring Security will successfully    
verify it against the hashed password).
──────
#### Option 2: Prefix the Password with  {noop}  (No-op Encoder)

If you prefer to keep the password as  abc123  in plaintext for easy local development, you can use Spring Security's             
DelegatingPasswordEncoder  format by prefixing the password with  {noop} :

In  application.yaml  /  application-dev.yaml :

```yaml
    spring:
      security:
        user:
	  name: admin
	  password: "{noop}abc123"
```

And in your  SecurityConfig.kt , use Spring Security's delegating password encoder:

```kotlin
    @Bean
    fun passwordEncoder(): PasswordEncoder {
	return org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
```

The  `{noop}`  prefix tells the encoder to treat this specific password as plaintext (no-op) without throwing BCrypt verification
errors.
