package br.com.jumarket.autoatentimentoapi.service

import br.com.jumarket.autoatentimentoapi.controller.response.ProductCategoryResponse
import br.com.jumarket.autoatentimentoapi.controller.response.ProductResponse
import br.com.jumarket.autoatentimentoapi.repository.ProductRepository
import org.springframework.stereotype.Service


@Service
class ProductService(
    private val productRepository: ProductRepository
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
}