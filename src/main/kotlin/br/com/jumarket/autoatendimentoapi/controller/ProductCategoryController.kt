package br.com.jumarket.autoatendimentoapi.controller

import br.com.jumarket.autoatendimentoapi.controller.request.ProductCategoryRequest
import br.com.jumarket.autoatendimentoapi.service.ProductCategoryService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/product-categories")
class ProductCategoryController(
    private val productCategoryService: ProductCategoryService
) {
    @GetMapping
    fun findAll() = productCategoryService.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: UUID) = productCategoryService.findById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody productCategoryRequest: ProductCategoryRequest) =
        productCategoryService.save(productCategoryRequest)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable("id") id: UUID, @RequestBody productCategoryRequest: ProductCategoryRequest) =
        productCategoryService.update(id, productCategoryRequest)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable("id") id: UUID) = productCategoryService.deleteById(id)
}