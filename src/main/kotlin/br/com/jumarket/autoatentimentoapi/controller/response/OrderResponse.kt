package br.com.jumarket.autoatentimentoapi.controller.response

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class OrderResponse(
    var id: UUID,
    var createAt: LocalDateTime,
    var typesPayments: List<String>,
    var status: String,
    var totOrderValue: BigDecimal,
    var shoppingCart: ShoppingCartResponse
)