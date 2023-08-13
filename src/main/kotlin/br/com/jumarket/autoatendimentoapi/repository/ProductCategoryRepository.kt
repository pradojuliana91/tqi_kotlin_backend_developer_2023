package br.com.jumarket.autoatendimentoapi.repository

import br.com.jumarket.autoatendimentoapi.model.ProductCategoryEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ProductCategoryRepository : CrudRepository<ProductCategoryEntity, UUID>