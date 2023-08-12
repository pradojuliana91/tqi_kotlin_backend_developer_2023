package br.com.jumarket.autoatentimentoapi.service

import br.com.jumarket.autoatentimentoapi.controller.request.ProductRequest
import br.com.jumarket.autoatentimentoapi.controller.response.ProductCategoryResponse
import br.com.jumarket.autoatentimentoapi.controller.response.ProductResponse
import br.com.jumarket.autoatentimentoapi.exception.BadRequestException
import br.com.jumarket.autoatentimentoapi.exception.NotFoundException
import br.com.jumarket.autoatentimentoapi.model.ProductEntity
import br.com.jumarket.autoatentimentoapi.repository.ProductCategoryRepository
import br.com.jumarket.autoatentimentoapi.repository.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*


@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productCategoryRepository: ProductCategoryRepository
) {

    fun findAll(): List<ProductResponse> {
        return productRepository.findAll().map { entity ->
            ProductResponse(
                id = entity.id,
                name = entity.name,
                costValue = entity.costValue,
                saleValue = entity.saleValue,
                sku = entity.sku,
                unidadeType = entity.unidadeType,
                productCategory = ProductCategoryResponse(entity.productCategory.id, entity.productCategory.name)
            )
        }
    }

    fun findById(id: UUID): ProductResponse {
        return productRepository.findByIdOrNull(id)?.let { entity ->
            productEntityToResponse(entity)
        } ?: throw NotFoundException("Produto não encontrado")
    }

    fun findBySku(sku: String): ProductResponse {
        return productRepository.findBySku(sku)?.let { entity ->
            productEntityToResponse(entity)
        } ?: throw NotFoundException("Produto não encontrado")
    }

    fun save(productRequest: ProductRequest): ProductResponse {

        val productCategoryEntity = productCategoryRepository.findByIdOrNull(productRequest.productCategoryId)
            ?: throw BadRequestException("produto categoria informado não existe.")

        val productEntity = ProductEntity(
            productCategory = productCategoryEntity,
            unidadeType = productRequest.unidadeType,
            sku = productRequest.sku,
            costValue = productRequest.costValue,
            saleValue = productRequest.saleValue,
            name = productRequest.name
        )

        return productEntityToResponse(productRepository.save(productEntity))
    }

    fun update(id: UUID, productRequest: ProductRequest) {

        val productCategoryEntity = productCategoryRepository.findByIdOrNull(productRequest.productCategoryId)
            ?: throw BadRequestException("produto categoria informado não existe.")

        productRepository.findByIdOrNull(id)?.let { entity ->
            entity.productCategory = productCategoryEntity
            entity.unidadeType = productRequest.unidadeType
            entity.sku = productRequest.sku
            entity.costValue = productRequest.costValue
            entity.saleValue = productRequest.saleValue
            entity.name = productRequest.name
            productRepository.save(entity)
        } ?: throw NotFoundException("Produto não encontrado")
    }

    fun deleteById(id: UUID) {
        productRepository.findByIdOrNull(id)?.let { entity ->
            productRepository.deleteById(entity.id)
        } ?: throw NotFoundException("Produto não encontrado")
    }

    private fun productEntityToResponse(productEntity: ProductEntity): ProductResponse {
        return ProductResponse(
            id = productEntity.id,
            name = productEntity.name,
            costValue = productEntity.costValue,
            saleValue = productEntity.saleValue,
            sku = productEntity.sku,
            unidadeType = productEntity.unidadeType,
            productCategory = ProductCategoryResponse(
                productEntity.productCategory.id,
                productEntity.productCategory.name
            )
        )
    }

}