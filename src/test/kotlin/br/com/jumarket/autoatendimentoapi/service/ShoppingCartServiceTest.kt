package br.com.jumarket.autoatendimentoapi.service

import br.com.jumarket.autoatendimentoapi.AutoAtendimentoApiApplicationTests
import br.com.jumarket.autoatendimentoapi.controller.request.ShoppingCartRequest
import br.com.jumarket.autoatendimentoapi.exception.BadRequestException
import br.com.jumarket.autoatendimentoapi.exception.NotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.time.format.DateTimeFormatter
import java.util.*

class ShoppingCartServiceTest : AutoAtendimentoApiApplicationTests() {

    @Autowired
    lateinit var shoppingCartService: ShoppingCartService

    private val DATE_FORMAT_TO_COMPARE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @Test
    fun `Find By Id Success`() {
        val sku = "1"
        val shoppingCartResponseCreate = shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = sku))

        val shoppingCartResponse = shoppingCartService.findById(shoppingCartResponseCreate.id)

        assertNotNull(shoppingCartResponse)
        assertEquals(sku, shoppingCartResponse.items.first().product.sku)
    }

    @Test
    fun `Find By Id Not Found`() {
        assertThrows<NotFoundException> { shoppingCartService.findById(UUID.randomUUID()) }
    }

    @Test
    fun `Create Success`() {
        val sku = "1"
        val shoppingCartResponseCreate = shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = sku))

        val shoppingCartResponse = shoppingCartService.findById(shoppingCartResponseCreate.id)

        assertNotNull(shoppingCartResponse)
        assertEquals(sku, shoppingCartResponse.items.first().product.sku)
        assertEquals(shoppingCartResponseCreate.id, shoppingCartResponse.id)
        assertEquals(
            DATE_FORMAT_TO_COMPARE.format(shoppingCartResponseCreate.createAt),
            DATE_FORMAT_TO_COMPARE.format(shoppingCartResponse.createAt)
        )
        assertEquals(shoppingCartResponseCreate.totSaleValue, shoppingCartResponse.totSaleValue)
        assertEquals(shoppingCartResponseCreate.items.count(), shoppingCartResponse.items.count())
        assertEquals(shoppingCartResponseCreate.items.first().product.id, shoppingCartResponse.items.first().product.id)
        assertEquals(shoppingCartResponseCreate.items.first().amount, shoppingCartResponse.items.first().amount)
        assertEquals(shoppingCartResponseCreate.items.first().saleValue, shoppingCartResponse.items.first().saleValue)
        assertEquals(shoppingCartResponseCreate.items.first().totalValue, shoppingCartResponse.items.first().totalValue)
    }

    @Test
    fun `Create Bad Request When Not Exists Product Sku`() {
        assertThrows<BadRequestException> { shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = "444442222")) }
    }

    @Test
    fun `Add Item Shopping Cart Success`() {

        val sku = "1"
        val shoppingCartResponseCreate = shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = sku))

        val shoppingCartAdd = shoppingCartService.addItemShoppingCart(
            shoppingCartResponseCreate.id, ShoppingCartRequest(productSku = "2")
        )

        assertNotNull(shoppingCartAdd)
        assertEquals(shoppingCartResponseCreate.id, shoppingCartAdd.id)
        assertEquals(
            DATE_FORMAT_TO_COMPARE.format(shoppingCartResponseCreate.createAt),
            DATE_FORMAT_TO_COMPARE.format(shoppingCartAdd.createAt)
        )
        assertNotEquals(shoppingCartResponseCreate.totSaleValue, shoppingCartAdd.totSaleValue)
        assertNotEquals(shoppingCartResponseCreate.items.count(), shoppingCartAdd.items.count())
    }

    @Test
    fun `Add Item Shopping Cart Success Same Products`() {

        val sku = "1"
        val shoppingCartResponseCreate = shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = sku))

        shoppingCartService.addItemShoppingCart(shoppingCartResponseCreate.id, ShoppingCartRequest(productSku = "1"))
        shoppingCartService.addItemShoppingCart(shoppingCartResponseCreate.id, ShoppingCartRequest(productSku = "2"))
        val shoppingCartAdd = shoppingCartService.addItemShoppingCart(
            shoppingCartResponseCreate.id,
            ShoppingCartRequest(productSku = "2")
        )

        assertNotNull(shoppingCartAdd)
        assertEquals(shoppingCartResponseCreate.id, shoppingCartAdd.id)
        assertEquals(
            DATE_FORMAT_TO_COMPARE.format(shoppingCartResponseCreate.createAt),
            DATE_FORMAT_TO_COMPARE.format(shoppingCartAdd.createAt)
        )
        assertFalse(shoppingCartAdd.items.any { it.amount != 2 })
        assertEquals(shoppingCartAdd.items.count(), 2)
    }

    @Test
    fun `Add Item Shopping Cart Bad Request When Not Exists Product Sku`() {

        val sku = "1"
        val shoppingCartResponseCreate = shoppingCartService.createShoppingCart(ShoppingCartRequest(productSku = sku))

        assertThrows<BadRequestException> {
            shoppingCartService.addItemShoppingCart(
                shoppingCartResponseCreate.id, ShoppingCartRequest(productSku = "444442222")
            )
        }
    }
}