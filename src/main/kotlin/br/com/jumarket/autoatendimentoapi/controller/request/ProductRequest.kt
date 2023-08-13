package br.com.jumarket.autoatendimentoapi.controller.request

import java.math.BigDecimal
import java.util.*

data class ProductRequest(
    var sku: String,
    var name: String,
    var productCategoryId: UUID,
    var unidadeType: String,
    var costValue: BigDecimal,
    var saleValue: BigDecimal
)
