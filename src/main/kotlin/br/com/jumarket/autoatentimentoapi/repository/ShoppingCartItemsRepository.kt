package br.com.jumarket.autoatentimentoapi.repository

import br.com.jumarket.autoatentimentoapi.model.ShoppingCartItemsEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ShoppingCartItemsRepository : CrudRepository<ShoppingCartItemsEntity, UUID>