Here’s a **practical HTMX cheatsheet** you can keep open while building apps.

---

# 🚀 HTMX Cheatsheet

> Official site: htmx

HTMX lets you access AJAX, CSS Transitions, WebSockets, and SSE directly in HTML using attributes.

---

# 🔹 Core Request Attributes

## 1️⃣ `hx-get`

**What it does:**
Makes a GET request when triggered.

**When to use:**
Fetching data, loading partials, search results, pagination.

**Example:**

```html
<button hx-get="/users" hx-target="#result">
  Load Users
</button>

<div id="result"></div>
```

---

## 2️⃣ `hx-post`

**What it does:**
Sends form data via POST.

**When to use:**
Submitting forms without page reload.

**Example:**

```html
<form hx-post="/users" hx-target="#message">
  <input name="name" />
  <button type="submit">Save</button>
</form>

<div id="message"></div>
```

---

## 3️⃣ `hx-put`, `hx-patch`, `hx-delete`

**What they do:**
Send PUT, PATCH, DELETE HTTP requests.

**When to use:**
RESTful updates or deletion.

**Example:**

```html
<button hx-delete="/users/1" hx-target="#user-1">
  Delete
</button>
```

---

# 🔹 Target & Swap

## 4️⃣ `hx-target`

**What it does:**
Specifies where the response should be inserted.

**When to use:**
Updating specific sections of the page.

```html
<button hx-get="/profile" hx-target="#profile"></button>
<div id="profile"></div>
```

---

## 5️⃣ `hx-swap`

**What it does:**
Controls how content is swapped.

**Common values:**

* `innerHTML` (default)
* `outerHTML`
* `beforebegin`
* `afterbegin`
* `beforeend`
* `afterend`

**When to use:**
Precise DOM replacement control.

```html
<div hx-get="/item" hx-swap="outerHTML"></div>
```

---

# 🔹 Triggers & Events

## 6️⃣ `hx-trigger`

**What it does:**
Defines when the request fires.

**Common triggers:**

* `click`
* `change`
* `keyup`
* `load`
* `revealed`

**When to use:**
Live search, auto-save, lazy loading.

```html
<input 
  hx-get="/search"
  hx-trigger="keyup changed delay:500ms"
  hx-target="#results"
/>
```

---

# 🔹 Indicators & UX

## 7️⃣ `hx-indicator`

**What it does:**
Shows a loading indicator during request.

**When to use:**
Better UX during async calls.

```html
<button hx-get="/data" hx-indicator="#spinner">
  Load
</button>

<div id="spinner" class="htmx-indicator">Loading...</div>
```

---

# 🔹 Boosting

## 8️⃣ `hx-boost`

**What it does:**
Turns links/forms into AJAX automatically.

**When to use:**
Progressively enhance existing apps.

```html
<body hx-boost="true">
  <a href="/about">About</a>
</body>
```

---

# 🔹 Parameters & Headers

## 9️⃣ `hx-vals`

**What it does:**
Adds extra values to request.

```html
<button 
  hx-post="/vote"
  hx-vals='{"postId": 10}'
>
  Vote
</button>
```

---

## 🔟 `hx-headers`

**What it does:**
Adds custom HTTP headers.

```html
<button 
  hx-get="/secure"
  hx-headers='{"X-API-KEY": "123"}'
>
  Secure Call
</button>
```

---

# 🔹 Navigation & History

## 1️⃣1️⃣ `hx-push-url`

**What it does:**
Pushes URL to browser history.

**When to use:**
SPA-like navigation with back button support.

```html
<button 
  hx-get="/page2"
  hx-push-url="true"
  hx-target="#content"
>
  Go
</button>
```

---

# 🔹 Polling

## 1️⃣2️⃣ `hx-trigger="every 5s"`

**What it does:**
Polls the server repeatedly.

**When to use:**
Live dashboards, notifications.

```html
<div hx-get="/stats" hx-trigger="every 5s">
</div>
```

---

# 🔹 Out-of-Band Updates

## 1️⃣3️⃣ `hx-swap-oob`

**What it does:**
Updates content outside the original target.

**When to use:**
Updating navbar counters, alerts globally.

```html
<div id="cart-count" hx-swap-oob="true">
  5
</div>
```

---

# 🔹 Confirm Dialog

## 1️⃣4️⃣ `hx-confirm`

**What it does:**
Shows confirmation dialog before request.

**When to use:**
Delete or destructive actions.

```html
<button 
  hx-delete="/users/1"
  hx-confirm="Are you sure?"
>
  Delete
</button>
```

---

# 🔹 File Upload

## 1️⃣5️⃣ `hx-encoding`

**What it does:**
Sets encoding type.

**When to use:**
File uploads.

```html
<form 
  hx-post="/upload"
  hx-encoding="multipart/form-data"
>
  <input type="file" name="file" />
</form>
```

---

# 🧠 Common Patterns

### ✅ Load on page load

```html
<div hx-get="/dashboard" hx-trigger="load"></div>
```

### ✅ Lazy load when visible

```html
<div hx-get="/next-page" hx-trigger="revealed"></div>
```

### ✅ Append new item

```html
<div hx-get="/item" hx-swap="beforeend"></div>
```

---

# 🏁 Quick Mental Model

HTMX =
**Trigger → Request → Target → Swap**

Example:

```html
<button 
  hx-post="/save"
  hx-trigger="click"
  hx-target="#msg"
  hx-swap="innerHTML"
>
  Save
</button>
```

---

If you want, I can also create:

* 🔥 Advanced HTMX cheat sheet (with SSE & WebSockets)
* 🧩 HTMX + Spring Boot cheat sheet
* ⚛️ HTMX vs React comparison sheet
* 📄 Printable PDF version

Just tell me which one you want.

