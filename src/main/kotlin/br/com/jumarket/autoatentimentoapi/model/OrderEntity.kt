package br.com.jumarket.autoatentimentoapi.model

import br.com.jumarket.autoatentimentoapi.model.enums.OrderStatusEnum
import br.com.jumarket.autoatentimentoapi.model.enums.TypePaymentEnum
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "pedidos")
data class OrderEntity(
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(name = "data_criacao")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "carrinho_id")
    var shoppingCart: ShoppingCartEntity,

    @Enumerated(EnumType.STRING)
    var status: OrderStatusEnum,

    @Column(name = "valor_total_pedido")
    var totOrderValue: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    var typePayment: TypePaymentEnum?,
)