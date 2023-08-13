package br.com.jumarket.autoatendimentoapi.controller.response

import java.math.BigDecimal
import java.util.*

data class ShoppingCartItemsResponse(
    var id: UUID,
    var product: ProductResponse,
    var amount: Int,
    var saleValue: BigDecimal,
    var totalValue: BigDecimal
)