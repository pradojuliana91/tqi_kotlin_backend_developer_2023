package br.com.jumarket.autoatentimentoapi.repository

import br.com.jumarket.autoatentimentoapi.model.OrderEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface OrderRepository : CrudRepository<OrderEntity, UUID>