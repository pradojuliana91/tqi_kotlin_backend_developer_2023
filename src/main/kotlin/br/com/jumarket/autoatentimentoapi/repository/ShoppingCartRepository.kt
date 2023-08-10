package br.com.jumarket.autoatentimentoapi.repository

import br.com.jumarket.autoatentimentoapi.model.ShoppingCartEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ShoppingCartRepository : CrudRepository<ShoppingCartEntity, UUID>