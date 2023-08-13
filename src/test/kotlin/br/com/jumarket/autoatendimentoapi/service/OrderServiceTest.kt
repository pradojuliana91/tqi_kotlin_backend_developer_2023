package br.com.jumarket.autoatendimentoapi.service

import br.com.jumarket.autoatendimentoapi.AutoAtendimentoApiApplicationTests
import br.com.jumarket.autoatendimentoapi.controller.request.OrderCreateRequest
import br.com.jumarket.autoatendimentoapi.controller.request.OrderPaymentRequest
import br.com.jumarket.autoatendimentoapi.controller.request.ShoppingCartRequest
import br.com.jumarket.autoatendimentoapi.exception.BadRequestException
import br.com.jumarket.autoatendimentoapi.exception.NotFoundException
import br.com.jumarket.autoatendimentoapi.model.enums.OrderStatusEnum
import br.com.jumarket.autoatendimentoapi.model.enums.TypePaymentEnum
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import java.util.*

class OrderServiceTest : AutoAtendimentoApiApplicationTests() {

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var shoppingCartService: ShoppingCartService

    @Test
    fun `Find By Id Success`() {
        val shoppingCartResponseCreate = shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = "1"))

        val orderResponse = orderService.createOrder(OrderCreateRequest(shoppingCartResponseCreate.id))

        assertNotNull(orderResponse)
        assertNotNull(orderResponse.typesPayments)
        assertTrue(orderResponse.typesPayments.isNotEmpty())
        assertTrue(orderResponse.totOrderValue.compareTo(BigDecimal.ZERO) > 0)
    }

    @Test
    fun `Find By Id Not Found`() {
        assertThrows<NotFoundException> { orderService.findById(UUID.randomUUID()) }
    }

    @Test
    fun `Create Success`() {
        val shoppingCartResponseCreate = shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = "1"))

        val orderResponse = orderService.createOrder(OrderCreateRequest(shoppingCartResponseCreate.id))

        assertNotNull(orderResponse)
        assertNotNull(orderResponse.typesPayments)
        assertTrue(orderResponse.typesPayments.isNotEmpty())
        assertTrue(orderResponse.totOrderValue.compareTo(BigDecimal.ZERO) > 0)
    }

    @Test
    fun `Create Bad Request Shopping Cart Not Exists`() {
        assertThrows<BadRequestException> { orderService.createOrder(OrderCreateRequest(UUID.randomUUID())) }
    }

    @Test
    fun `Cancel Success`() {
        val shoppingCartResponseCreate = shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = "1"))

        val orderId = orderService.createOrder(OrderCreateRequest(shoppingCartResponseCreate.id)).id

        orderService.cancel(orderId)

        assertEquals(OrderStatusEnum.CANCELADO.name, orderService.findById(orderId).status)
    }

    @Test
    fun `Cancel Not Found Order`() {
        assertThrows<NotFoundException> { orderService.cancel(UUID.randomUUID()) }
    }

    @Test
    fun `Cancel Bad Request Order Has Canceled`() {
        val shoppingCartResponseCreate = shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = "1"))

        val orderId = orderService.createOrder(OrderCreateRequest(shoppingCartResponseCreate.id)).id
        orderService.cancel(orderId)

        assertThrows<BadRequestException> {
            orderService.cancel(orderId)
        }
    }

    @Test
    fun `Cancel Bad Request Order Has Payment`() {
        val shoppingCartResponseCreate = shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = "1"))

        val orderId = orderService.createOrder(OrderCreateRequest(shoppingCartResponseCreate.id)).id
        orderService.payment(orderId, OrderPaymentRequest(TypePaymentEnum.PIX.name))

        assertThrows<BadRequestException> {
            orderService.cancel(orderId)
        }
    }

    @Test
    fun `Payment Success`() {
        TypePaymentEnum.values().forEach { paymentEnum ->
            val shoppingCartResponseCreate =
                shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = "1"))

            val orderId = orderService.createOrder(OrderCreateRequest(shoppingCartResponseCreate.id)).id

            orderService.payment(orderId, OrderPaymentRequest(paymentEnum.name))

            assertEquals(OrderStatusEnum.PAGO.name, orderService.findById(orderId).status)
        }
    }

    @Test
    fun `Payment Not Found Order`() {
        assertThrows<NotFoundException> {
            orderService.payment(
                UUID.randomUUID(),
                OrderPaymentRequest(TypePaymentEnum.PIX.name)
            )
        }
    }

    @Test
    fun `Payment Bad Request Payment Type Not Exists`() {
        val shoppingCartResponseCreate = shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = "1"))

        val orderId = orderService.createOrder(OrderCreateRequest(shoppingCartResponseCreate.id)).id

        assertThrows<BadRequestException> {
            orderService.payment(orderId, OrderPaymentRequest("New Payment"))
        }
    }

    @Test
    fun `Payment Bad Request Order Has Payments`() {
        val shoppingCartResponseCreate = shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = "1"))

        val orderId = orderService.createOrder(OrderCreateRequest(shoppingCartResponseCreate.id)).id
        orderService.payment(orderId, OrderPaymentRequest(TypePaymentEnum.PIX.name))

        assertThrows<BadRequestException> {
            orderService.payment(orderId, OrderPaymentRequest(TypePaymentEnum.PIX.name))
        }
    }

    @Test
    fun `Payment Bad Request Order Has Canceled`() {
        val shoppingCartResponseCreate = shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = "1"))

        val orderId = orderService.createOrder(OrderCreateRequest(shoppingCartResponseCreate.id)).id
        orderService.cancel(orderId)

        assertThrows<BadRequestException> {
            orderService.payment(orderId, OrderPaymentRequest(TypePaymentEnum.PIX.name))
        }
    }
}