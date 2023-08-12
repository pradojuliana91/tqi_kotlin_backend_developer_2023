package br.com.jumarket.autoatentimentoapi.controller

import br.com.jumarket.autoatentimentoapi.controller.request.OrderCreateRequest
import br.com.jumarket.autoatentimentoapi.controller.request.OrderPaymentRequest
import br.com.jumarket.autoatentimentoapi.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: UUID) = orderService.findById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody orderCreateRequest: OrderCreateRequest) = orderService.createOrder(orderCreateRequest)

    @PostMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun cancel(@PathVariable("id") id: UUID) = orderService.cancel(id)

    @PostMapping("/{id}/payment")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun payment(@PathVariable("id") id: UUID, @RequestBody orderPaymentRequest: OrderPaymentRequest) =
        orderService.payment(id, orderPaymentRequest)
}