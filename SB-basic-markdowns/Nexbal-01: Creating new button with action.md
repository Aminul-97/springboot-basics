• Use your current HTMX approach and complete the controller/service delete flow.

  1. In [products.html](/home/aminul/Greg/nexbal/src/main/resources/templates/products.html) keep the trash as an <a> with hx-post:

```html
  <td style="padding: 0.35rem 0.5rem;">
      <form th:action="@{/product/delete/{id}(id=${item.id})}" method="post" style="display:inline;">
          <button type="submit" style="background:none; border:none; padding:0; cursor:pointer;">
              <wa-icon name="trash" style="color: var(--wa-color-red-40)"></wa-icon>
          </button>
      </form>
  </td>
```

  2. In [ProductCtrl.kt](/home/aminul/Greg/nexbal/src/main/kotlin/no/reai/nexbal/ctrl/ProductCtrl.kt) make delete actually delete, then
  force page reload:
```kotlin
  @PostMapping("/delete/{id}")
  fun deleteProduct(@PathVariable id: Int): String {
      productSvc.deleteProduct(id)
      return "redirect:/product"
  }
```


  3. In [ProductSvc.kt](/home/aminul/Greg/nexbal/src/main/kotlin/no/reai/nexbal/svc/ProductSvc.kt) add a delete method:

```kotlin
  // ProductSvc
  fun deleteProduct(productId: Int) {
      if (orderLineRepo.existsByProductId(productId)) {
          throw IllegalStateException("Product is used in order lines and cannot be deleted")
      }
      productRepo.deleteById(productId)
  }
```
  
  4. In [OrderLineRepo.kt]
  
  ```kotlin
    // OrderLineRepo
  interface OrderLineRepo : JpaRepository<OrderLine, Int> {
      fun existsByProductId(productId: Int): Boolean
  }
  ```


  That gives you:
  - delete item on trash click
  - DB updated via productRepo.deleteById(...)
  - full page reload via HX-Refresh: true, so /product is loaded again with fresh data.

