package br.com.jumarket.autoatentimentoapi.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "carrinhos")
data class ShoppingCartEntity(
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(name = "data_criacao")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "valor_total_venda")
    var totSaleValue: BigDecimal = BigDecimal.ZERO,

    @OneToMany(mappedBy = "shoppingCart", fetch = FetchType.EAGER)
    var items: Set<ShoppingCartItemsEntity> = emptySet()
)