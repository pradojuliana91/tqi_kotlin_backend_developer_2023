package br.com.jumarket.autoatendimentoapi.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "carrinho_itens")
data class ShoppingCartItemsEntity(
    @Id
    var id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "carrinho_id")
    var shoppingCart: ShoppingCartEntity,

    @ManyToOne
    @JoinColumn(name = "produto_id")
    var product: ProductEntity,

    @Column(name = "quantidade")
    var amount: Int,

    @Column(name = "preco_venda")
    var saleValue: BigDecimal,

    @Column(name = "valor_total")
    var totalValue: BigDecimal
)