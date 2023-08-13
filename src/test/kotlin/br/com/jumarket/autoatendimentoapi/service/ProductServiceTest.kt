package br.com.jumarket.autoatendimentoapi.service

import br.com.jumarket.autoatendimentoapi.AutoAtendimentoApiApplicationTests
import br.com.jumarket.autoatendimentoapi.controller.request.ProductRequest
import br.com.jumarket.autoatendimentoapi.exception.BadRequestException
import br.com.jumarket.autoatendimentoapi.exception.NotFoundException
import br.com.jumarket.autoatendimentoapi.repository.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal
import java.util.*

class ProductServiceTest : AutoAtendimentoApiApplicationTests() {

    @Autowired
    lateinit var productService: ProductService

    @Autowired
    lateinit var productRepository: ProductRepository

    private val PRODUCT_CATEGORY_1_ID: UUID = UUID.fromString("f442a928-39de-410b-85ff-6be93f19e77c")

    private val PRODUCT_1_ID: UUID = UUID.fromString("66ae27af-9084-46ca-9ee9-1c78bbca170c")
    private val PRODUCT_2_ID: UUID = UUID.fromString("c492e040-6608-4b25-91e9-b71af195a5c2")
    private val PRODUCT_3_ID: UUID = UUID.fromString("2303e0a2-2cf3-4061-baa8-7a269dee1731")

    @Test
    fun `Find All Success`() {

        val findAll = productService.findAll()

        assertNotNull(findAll)
        assertTrue(findAll.isNotEmpty())
    }

    @Test
    fun `Find Success`() {
        val response = productService.findById(PRODUCT_1_ID)
        assertNotNull(response)
        assertEquals(PRODUCT_1_ID, response.id)
        assertEquals("detergente", response.name)
        assertEquals("1", response.sku)
        assertEquals("un", response.unidadeType)
        assertTrue(BigDecimal(2).compareTo(response.costValue) == 0)
        assertTrue(BigDecimal(4).compareTo(response.saleValue) == 0)
    }

    @Test
    fun `Find By Id Not Found When Not Exists Product`() {
        assertThrows<NotFoundException> { productService.findById(UUID.randomUUID()) }
    }

    @Test
    fun `Find By Sku Success`() {
        val response = productService.findBySku("1")
        assertNotNull(response)
    }

    @Test
    fun `Find By Sku Not Found When Not Exists Product`() {
        assertThrows<NotFoundException> { productService.findBySku("32423423422") }
    }

    @Test
    fun `Create Success`() {
        val productRequest = ProductRequest(
            sku = "432",
            name = "test_create",
            productCategoryId = PRODUCT_CATEGORY_1_ID,
            unidadeType = "UN",
            costValue = BigDecimal(3),
            saleValue = BigDecimal(5)
        )

        val response = productService.save(productRequest)

        val entity = productRepository.findByIdOrNull(response.id)

        assertNotNull(entity)
        entity!!
        assertEquals(entity.id, response.id)
        assertEquals(entity.name, response.name)
        assertEquals(entity.unidadeType, response.unidadeType)
        assertEquals(entity.productCategory.id, response.productCategory.id)
        assertTrue(entity.costValue.compareTo(response.costValue) == 0)
        assertTrue(entity.saleValue.compareTo(response.saleValue) == 0)
    }

    @Test
    fun `Create Bad Request When Not Exists Product Category`() {
        val productRequest = ProductRequest(
            sku = "2333",
            name = "test_create_1",
            productCategoryId = UUID.randomUUID(),
            unidadeType = "UN",
            costValue = BigDecimal(2),
            saleValue = BigDecimal(3)
        )

        assertThrows<BadRequestException> { productService.save(productRequest) }
    }

    @Test
    fun `Update Success`() {
        val productRequest = ProductRequest(
            sku = "998822",
            name = "teste_update_success",
            productCategoryId = PRODUCT_CATEGORY_1_ID,
            unidadeType = "UN",
            costValue = BigDecimal(66),
            saleValue = BigDecimal(160)
        )

        productService.update(PRODUCT_2_ID, productRequest)

        val entity = productRepository.findByIdOrNull(PRODUCT_2_ID)

        assertNotNull(entity)
        entity!!
        assertEquals(entity.name, productRequest.name)
        assertEquals(entity.unidadeType, productRequest.unidadeType)
        assertEquals(entity.productCategory.id, productRequest.productCategoryId)
        assertTrue(entity.costValue.compareTo(productRequest.costValue) == 0)
        assertTrue(entity.saleValue.compareTo(productRequest.saleValue) == 0)
    }

    @Test
    fun `Update Bad Request When Not Exists Product Category`() {
        val productRequest = ProductRequest(
            sku = "2333",
            name = "test_update_Bad_request",
            productCategoryId = UUID.randomUUID(),
            unidadeType = "UN",
            costValue = BigDecimal(2232),
            saleValue = BigDecimal(33333)
        )

        assertThrows<BadRequestException> { productService.update(PRODUCT_2_ID, productRequest) }
    }

    @Test
    fun `Update Not Found When Not Exists Product`() {
        assertThrows<NotFoundException> {
            val productRequest = ProductRequest(
                sku = "66666",
                name = "test_update_not_found",
                productCategoryId = PRODUCT_CATEGORY_1_ID,
                unidadeType = "UN",
                costValue = BigDecimal(22),
                saleValue = BigDecimal(451)
            )

            productService.update(UUID.randomUUID(), productRequest)
        }
    }

    @Test
    fun `Delete Success`() {
        productService.deleteById(PRODUCT_3_ID)

        val entityDeleted = productRepository.findByIdOrNull(PRODUCT_3_ID)

        assertNull(entityDeleted)
    }

    @Test
    fun `Delete Not Found When Not Exists Product `() {
        assertThrows<NotFoundException> {
            productService.deleteById(UUID.randomUUID())
        }
    }
}