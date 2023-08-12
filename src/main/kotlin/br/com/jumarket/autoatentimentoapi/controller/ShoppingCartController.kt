package br.com.jumarket.autoatentimentoapi.controller

import br.com.jumarket.autoatentimentoapi.controller.request.ShoppingCartRequest
import br.com.jumarket.autoatentimentoapi.service.ShoppingCartService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/shopping-carts")
class ShoppingCartController(
    private val shoppingCartService: ShoppingCartService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: UUID) = shoppingCartService.findById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody shoppingCartRequest: ShoppingCartRequest) =
        shoppingCartService.createShoppingCart(shoppingCartRequest)

    @PutMapping("/{id}")
    fun add(@PathVariable("id") id: UUID, @RequestBody shoppingCartRequest: ShoppingCartRequest) =
        shoppingCartService.addItemShoppingCart(id, shoppingCartRequest)

}