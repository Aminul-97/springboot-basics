## ✅ Overview

In Spring MVC, you **shouldn’t handle exceptions inside every controller method**.
Instead, you centralize error handling using `@ControllerAdvice`.

Think of it like this:

> Your controllers focus on business logic.
> Your global exception handler focuses on failure logic.

Let’s build a **minimal working example** so the concept sticks long-term.

---

# 🧩 Simple Example: Product Controller + Global Exception Handler

We’ll build:

* A controller that throws exceptions
* A global exception handler
* A simple HTML response

---

## 1️⃣ Product Controller

```kotlin
package com.example.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class ProductController {

    @GetMapping("/product/{id}")
    fun getProduct(@PathVariable id: Int): String {

        if (id < 0) {
            throw IllegalArgumentException("Product ID cannot be negative")
        }

        if (id == 0) {
            throw NoSuchElementException("Product not found")
        }

        return "product-success"
    }
}
```

### What happens?

* `/product/-1` → throws `IllegalArgumentException`
* `/product/0` → throws `NoSuchElementException`
* `/product/1` → success page

Notice: **No try-catch blocks.**

---

## 2️⃣ Global Exception Handler

```kotlin
package com.example.demo.advice

import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice(basePackages = ["com.example.demo.controller"])
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(
        ex: IllegalArgumentException,
        model: Model,
        response: HttpServletResponse
    ): String {

        logger.error("Bad Request: ${ex.message}")
        response.status = HttpStatus.BAD_REQUEST.value()
        model.addAttribute("errorMessage", ex.message)

        return "error-page"
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(
        ex: NoSuchElementException,
        model: Model,
        response: HttpServletResponse
    ): String {

        logger.error("Not Found: ${ex.message}")
        response.status = HttpStatus.NOT_FOUND.value()
        model.addAttribute("errorMessage", ex.message)

        return "error-page"
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralError(
        ex: Exception,
        model: Model,
        response: HttpServletResponse
    ): String {

        logger.error("Unexpected Error", ex)
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        model.addAttribute("errorMessage", "Something went wrong")

        return "error-page"
    }
}
```

---

## 3️⃣ Simple Error View (Thymeleaf Example)

`error-page.html`

```html
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h2>Error Occurred</h2>
    <p th:text="${errorMessage}"></p>
</body>
</html>
```

---

# 🔄 Runtime Flow (Very Important to Understand)

Let’s say user visits:

```
/product/-1
```

### Step-by-step:

1. `ProductController.getProduct()` executes
2. It throws `IllegalArgumentException`
3. Spring scans for `@ExceptionHandler`
4. Finds `handleBadRequest()`
5. Sets HTTP status 400
6. Sends message to model
7. Returns `error-page`
8. Browser shows error page

---

# 🧠 Why This Is Powerful

Without `@ControllerAdvice`:

```kotlin
try {
   ...
} catch(e: Exception) {
   ...
}
```

You’d repeat that in every controller method.

With it:

✔ Clean controllers
✔ Centralized error logic
✔ Proper HTTP status codes
✔ Consistent UI
✔ Production-ready structure

---

# 🎯 Mental Model to Remember

```
Controller throws exception
        ↓
Spring detects matching @ExceptionHandler
        ↓
Global handler executes
        ↓
Response status + Model updated
        ↓
Error view rendered
```

---

If you want next, I can show:

* The REST API version using `@RestControllerAdvice`
* How to return JSON instead of HTML
* How to reduce repetition even more using a base handler
* How to test this with MockMvc

Just tell me which direction fits your current learning goal.

