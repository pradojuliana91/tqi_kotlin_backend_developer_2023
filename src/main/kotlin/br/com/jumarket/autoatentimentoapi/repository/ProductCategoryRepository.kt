package br.com.jumarket.autoatentimentoapi.repository

import br.com.jumarket.autoatentimentoapi.model.ProductCategoryEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ProductCategoryRepository : CrudRepository<ProductCategoryEntity, UUID>