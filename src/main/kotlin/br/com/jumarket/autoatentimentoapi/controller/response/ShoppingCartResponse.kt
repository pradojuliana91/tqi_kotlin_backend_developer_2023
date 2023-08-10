package br.com.jumarket.autoatentimentoapi.controller.response

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class ShoppingCartResponse(
    var id: UUID,
    var createAt: LocalDateTime,
    var totSaleValue: BigDecimal,
    var items: Set<ShoppingCartItemsResponse>
)


