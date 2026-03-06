Perfect 👍 that’s the correct structure.

So your setup is:

* `customer.html` → parent page (contains button)
* `customer/form.html` → child fragment (the one you shared)
* You want: click button → open popup → load form fragment via **htmx**

Let’s structure this cleanly and correctly.

---

# ✅ Clean Architecture (Recommended Pattern)

Instead of returning the modal wrapper from the server every time, keep:

* Modal container in `customer.html`
* Load only the **form fragment** inside it via htmx

This keeps things predictable and scalable.

---

# 🧱 1️⃣ Parent Page (`customer.html`)

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <script src="https://unpkg.com/htmx.org@1.9.10"></script>
</head>
<body>

<button
    hx-get="/customer/new"
    hx-target="#customer-new-dialog-body"
    hx-swap="innerHTML"
    onclick="document.getElementById('customer-new-dialog').style.display='flex'">
    New Customer
</button>

<!-- Modal Container (ALWAYS PRESENT) -->
<div id="customer-new-dialog"
     style="display:none; position:fixed; top:0; left:0;
            width:100%; height:100%;
            background:rgba(0,0,0,0.4);
            align-items:center; justify-content:center;">

    <div style="background:white; padding:20px; width:500px;">
        <h3>Create Customer</h3>

        <div id="customer-new-dialog-body">
            <!-- htmx loads form fragment here -->
        </div>

        <button onclick="closeModal()">Close</button>
    </div>
</div>

<script>
    function closeModal() {
        document.getElementById('customer-new-dialog').style.display = 'none';
        document.getElementById('customer-new-dialog-body').innerHTML = '';
    }
</script>

</body>
</html>
```

---

# 🧩 2️⃣ Your Child Fragment (Already Good)

Your fragment:

```html
<th:block th:fragment="content">
    <form
        th:action="@{/customer}"
        th:object="${createCustomerRequest}"
        method="post"
        hx-post="/customer"
        hx-target="#customer-new-dialog-body"
        hx-swap="innerHTML"
        class="wa-stack">

        <wa-input id="name" name="name" type="text" required
                  th:value="*{name}"
                  th:label="#{customer.new.name.label}">
        </wa-input>

        <wa-input id="organizationNumber" name="organizationNumber"
                  type="text"
                  th:value="*{organizationNumber}"
                  th:label="#{customer.new.organizationNumber.label}">
        </wa-input>

        <wa-input id="email" name="email" type="email"
                  th:value="*{email}"
                  th:label="#{customer.new.email.label}">
        </wa-input>

        <wa-button type="submit" variant="brand"
                   th:text="#{customer.new.submit}">
        </wa-button>
    </form>
</th:block>
```

Perfect. No change needed.

---

# 🧠 3️⃣ Controller (Kotlin)

```kotlin
@Controller
@RequestMapping("/customer")
class CustomerController {

    @GetMapping("/new")
    fun newCustomer(model: Model): String {
        model.addAttribute("createCustomerRequest", CreateCustomerRequest())
        return "customer/form :: content"
    }

    @PostMapping
    fun createCustomer(
        @ModelAttribute createCustomerRequest: CreateCustomerRequest,
        bindingResult: BindingResult,
        model: Model
    ): String {

        if (bindingResult.hasErrors()) {
            return "customer/form :: content"
        }

        // save customer

        // tell htmx to close modal
        return "customer/success :: closeModal"
    }
}
```

---

# 🧩 4️⃣ Success Fragment (Cleaner Than Inline JS)

Create:

`customer/success.html`

```html
<th:block th:fragment="closeModal">
    <script>
        closeModal();
        htmx.ajax('GET', '/customer/list', '#customer-table');
    </script>
</th:block>
```

This:

* Closes modal
* Refreshes customer list
* No page reload

---

# 🔥 Even Cleaner (Professional HTMX Way)

Instead of inline JS, use **HX-Trigger header**.

In controller:

```kotlin
@PostMapping
fun createCustomer(
    @ModelAttribute createCustomerRequest: CreateCustomerRequest,
    bindingResult: BindingResult,
    model: Model,
    response: HttpServletResponse
): String {

    if (bindingResult.hasErrors()) {
        return "customer/form :: content"
    }

    response.addHeader("HX-Trigger", "customerCreated")
    return ""
}
```

Then in parent page:

```html
<script>
    document.body.addEventListener("customerCreated", function () {
        closeModal();
        htmx.ajax('GET', '/customer/list', '#customer-table');
    });
</script>
```

⚡ This is the cleanest server-driven pattern.

---

# 🏗 Recommended Pattern for Larger Apps

Structure:

```
templates/
 ├── customer/
 │    ├── customer.html
 │    ├── form.html
 │    ├── list.html
 │    └── success.html
 └── fragments/
      └── modal.html
```

Keep:

* Layout stable
* Only fragments dynamic
* Server returns partial HTML only

---

# 🚀 Summary of Correct Flow

Click button
→ htmx GET loads form fragment
→ Modal opens
→ htmx POST
→ Validation error? re-render form
→ Success? trigger event
→ Close modal + refresh list

Fully server-driven. No SPA complexity. Very maintainable.

---

If you want, next I can show:

* Proper validation with `th:errors`
* Using `<dialog>` instead of custom div
* Integrating with your `<wa-*>` components properly
* Turning this into a reusable modal system

Which direction are you building toward?

