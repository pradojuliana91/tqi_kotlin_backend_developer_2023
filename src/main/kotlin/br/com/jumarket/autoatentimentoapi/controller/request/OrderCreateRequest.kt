package br.com.jumarket.autoatentimentoapi.controller.request

import java.util.*

data class OrderCreateRequest(
    var shoppingCartId: UUID
)
