package br.com.jumarket.autoatendimentoapi.service

import br.com.jumarket.autoatendimentoapi.AutoAtendimentoApiApplicationTests
import br.com.jumarket.autoatendimentoapi.controller.request.ProductCategoryRequest
import br.com.jumarket.autoatendimentoapi.exception.NotFoundException
import br.com.jumarket.autoatendimentoapi.model.ProductCategoryEntity
import br.com.jumarket.autoatendimentoapi.repository.ProductCategoryRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.util.*

class ProductCategoryServiceTest : AutoAtendimentoApiApplicationTests() {

    @Autowired
    lateinit var productCategoryService: ProductCategoryService

    @Autowired
    lateinit var productCategoryRepository: ProductCategoryRepository

    @Test
    fun `Find All Success`() {
        val productCategoryEntity1 = ProductCategoryEntity(name = "test_category_find_all_1")
        val productCategoryEntity2 = ProductCategoryEntity(name = "test_category_find_all_2")

        productCategoryRepository.save(productCategoryEntity1)
        productCategoryRepository.save(productCategoryEntity2)

        val findAll = productCategoryService.findAll()

        assertNotNull(findAll)
        assertTrue(findAll.isNotEmpty())
    }

    @Test
    fun `Find Success`() {
        val productCategoryEntity = ProductCategoryEntity(name = "test_category_find")

        val entity = productCategoryRepository.save(productCategoryEntity)

        val response = productCategoryService.findById(entity.id)

        assertNotNull(entity)
        assertEquals(entity.id, response.id)
        assertEquals(entity.name, response.name)
    }

    @Test
    fun `Find By Id Not Found When Not Exists Product Category`() {
        assertThrows<NotFoundException> { productCategoryService.findById(UUID.randomUUID()) }
    }

    @Test
    fun `Create Success`() {
        val productCategoryRequest = ProductCategoryRequest(name = "test_category_create")

        val response = productCategoryService.save(productCategoryRequest)

        val entity = productCategoryRepository.findByIdOrNull(response.id)

        assertNotNull(entity)
        entity!!
        assertEquals(entity.id, response.id)
        assertEquals(entity.name, response.name)

    }

    @Test
    fun `Update Success`() {
        val productCategoryEntity = ProductCategoryEntity(name = "test_category_update_before")

        val entitySave = productCategoryRepository.save(productCategoryEntity)

        val productCategoryRequest = ProductCategoryRequest(name = "test_category_update_after")

        productCategoryService.update(entitySave.id, productCategoryRequest)

        val entityUpdated = productCategoryRepository.findByIdOrNull(entitySave.id)

        assertNotNull(entityUpdated)
        assertEquals(entityUpdated!!.name, productCategoryRequest.name)
    }

    @Test
    fun `Update Not Found When Not Exists Product Category`() {
        assertThrows<NotFoundException> {
            val productCategoryRequest = ProductCategoryRequest(name = "test_category_update_error_not_found")
            productCategoryService.update(UUID.randomUUID(), productCategoryRequest)
        }
    }

    @Test
    fun `Delete Success`() {
        val productCategoryEntity = ProductCategoryEntity(name = "test_category_delete")
        val entitySave = productCategoryRepository.save(productCategoryEntity)
        productCategoryService.deleteById(entitySave.id)

        val entityDeleted = productCategoryRepository.findByIdOrNull(entitySave.id)

        assertNull(entityDeleted)
    }

    @Test
    fun `Delete Not Found When Not Exists Product Category`() {
        assertThrows<NotFoundException> {
            productCategoryService.deleteById(UUID.randomUUID())
        }
    }
}