package br.com.jumarket.autoatendimentoapi.repository

import br.com.jumarket.autoatendimentoapi.model.ShoppingCartItemsEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ShoppingCartItemsRepository : CrudRepository<ShoppingCartItemsEntity, UUID> {
    fun findByShoppingCartIdAndProductSku(shoppingCartId: UUID, productSku: String): ShoppingCartItemsEntity?
    fun findByShoppingCartId(shoppingCartId: UUID): List<ShoppingCartItemsEntity>?
}