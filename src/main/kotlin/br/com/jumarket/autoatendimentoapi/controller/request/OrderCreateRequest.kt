package br.com.jumarket.autoatendimentoapi.controller.request

import java.util.*

data class OrderCreateRequest(
    var shoppingCartId: UUID
)
