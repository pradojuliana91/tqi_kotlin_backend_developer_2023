package br.com.jumarket.autoatentimentoapi.repository

import br.com.jumarket.autoatentimentoapi.model.ProductEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ProductRepository : CrudRepository<ProductEntity, UUID> {
    fun findBySku(sku: String): ProductEntity
}