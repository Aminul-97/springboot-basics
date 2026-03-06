## Removing a table row (+ Perform delete operation) on button click without refreshing the entire page.

customers.html
```html
<tr>
    <td style="padding: 0.35rem 0.5rem;">
        <form   th:hx-post="@{/customer/delete/{id}(id=${item.id})}"
                hx-target="closest tr"
                hx-swap="outerHTML"
                style="display:inline;">
            <button type="submit" style="background:none; border:none; padding:0; cursor:pointer;">
                <wa-icon name="trash" style="color: var(--wa-color-red-40)"></wa-icon>
            </button>
        </form>
    </td>
</tr>
```

CustomerCtrl.kt
```kotlin
    @PostMapping("/delete/{id}")
    fun deleteCustomer(@PathVariable id: Int): ResponseEntity<String> {
        customerSvc.deleteCustomer(id)
        return ResponseEntity.ok("")
    }
```
