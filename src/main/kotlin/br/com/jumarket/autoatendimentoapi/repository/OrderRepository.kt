package br.com.jumarket.autoatendimentoapi.repository

import br.com.jumarket.autoatendimentoapi.model.OrderEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface OrderRepository : CrudRepository<OrderEntity, UUID>