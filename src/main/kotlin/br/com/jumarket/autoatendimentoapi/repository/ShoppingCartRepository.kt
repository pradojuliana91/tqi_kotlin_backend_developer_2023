package br.com.jumarket.autoatendimentoapi.repository

import br.com.jumarket.autoatendimentoapi.model.ShoppingCartEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ShoppingCartRepository : CrudRepository<ShoppingCartEntity, UUID>