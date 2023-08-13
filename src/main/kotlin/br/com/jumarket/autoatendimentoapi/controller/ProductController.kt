package br.com.jumarket.autoatendimentoapi.controller

import br.com.jumarket.autoatendimentoapi.controller.request.ProductRequest
import br.com.jumarket.autoatendimentoapi.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {
    @GetMapping
    fun findAll() = productService.findAll()

    @GetMapping("/sku/{sku}")
    fun findBySku(@PathVariable("sku") sku: String) = productService.findBySku(sku)

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: UUID) = productService.findById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody productRequest: ProductRequest) =
        productService.save(productRequest)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable("id") id: UUID, @RequestBody productRequest: ProductRequest) =
        productService.update(id, productRequest)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable("id") id: UUID) = productService.deleteById(id)
}