package br.com.jumarket.autoatentimentoapi.service

import br.com.jumarket.autoatentimentoapi.controller.request.ProductCategoryRequest
import br.com.jumarket.autoatentimentoapi.controller.response.ProductCategoryResponse
import br.com.jumarket.autoatentimentoapi.exception.NotFoundException
import br.com.jumarket.autoatentimentoapi.model.ProductCategoryEntity
import br.com.jumarket.autoatentimentoapi.repository.ProductCategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*


@Service
class ProductCategoryService(
    private val productCategoryRepository: ProductCategoryRepository
) {

    fun findAll(): List<ProductCategoryResponse> {
        return productCategoryRepository.findAll().map { entity ->
            ProductCategoryResponse(entity.id, entity.name)
        }
    }

    fun findById(id: UUID): ProductCategoryResponse {
        return productCategoryRepository.findByIdOrNull(id)?.let { entity ->
            ProductCategoryResponse(entity.id, entity.name)
        } ?: throw NotFoundException("Categoria de Produto não encontrada")
    }

    fun save(productCategoryRequest: ProductCategoryRequest): ProductCategoryResponse {
        return ProductCategoryEntity(name = productCategoryRequest.name).let { entity ->
            productCategoryRepository.save(entity)
            ProductCategoryResponse(entity.id, entity.name)
        }
    }

    fun update(id: UUID, productCategoryRequest: ProductCategoryRequest) {
        productCategoryRepository.findByIdOrNull(id)?.let { entity ->
            entity.name = productCategoryRequest.name
            productCategoryRepository.save(entity)
        } ?: throw NotFoundException("Categoria de Produto não encontrada")
    }

    fun deleteById(id: UUID) {
        productCategoryRepository.findByIdOrNull(id)?.let { entity ->
            productCategoryRepository.deleteById(entity.id)
        } ?: throw NotFoundException("Categoria de Produto não encontrada")
    }
}