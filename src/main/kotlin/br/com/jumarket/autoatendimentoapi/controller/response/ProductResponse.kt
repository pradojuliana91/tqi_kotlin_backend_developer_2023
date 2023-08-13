package br.com.jumarket.autoatendimentoapi.controller.response

import java.math.BigDecimal
import java.util.*

data class ProductResponse(
    var id: UUID,
    var sku: String,
    var name: String,
    var productCategory: ProductCategoryResponse,
    var unidadeType: String,
    var costValue: BigDecimal,
    var saleValue: BigDecimal
)