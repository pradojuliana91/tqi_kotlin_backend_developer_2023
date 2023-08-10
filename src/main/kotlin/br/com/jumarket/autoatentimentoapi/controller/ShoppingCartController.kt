package br.com.jumarket.autoatentimentoapi.controller

import br.com.jumarket.autoatentimentoapi.controller.request.CreateShoppingCartRequest
import br.com.jumarket.autoatentimentoapi.controller.request.ProductRequest
import br.com.jumarket.autoatentimentoapi.service.ProductService
import br.com.jumarket.autoatentimentoapi.service.ShoppingCartService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/shopping-carts")
class ShoppingCartController(
    private val shoppingCartService: ShoppingCartService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody shoppingCartRequest: CreateShoppingCartRequest) =
        shoppingCartService.createShoppingCart(shoppingCartRequest)

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@PathVariable("id") id: UUID, @RequestBody shoppingCartRequest: CreateShoppingCartRequest) =
        shoppingCartService.createShoppingCart(shoppingCartRequest)

}