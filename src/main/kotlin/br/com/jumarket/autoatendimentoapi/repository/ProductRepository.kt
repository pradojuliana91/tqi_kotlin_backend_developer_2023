package br.com.jumarket.autoatendimentoapi.repository

import br.com.jumarket.autoatendimentoapi.model.ProductEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ProductRepository : CrudRepository<ProductEntity, UUID> {
    fun findBySku(sku: String): ProductEntity?

}