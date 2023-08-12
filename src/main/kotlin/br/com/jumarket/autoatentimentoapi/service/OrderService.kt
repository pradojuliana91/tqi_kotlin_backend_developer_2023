package br.com.jumarket.autoatentimentoapi.service

import br.com.jumarket.autoatentimentoapi.controller.request.OrderCreateRequest
import br.com.jumarket.autoatentimentoapi.controller.request.OrderPaymentRequest
import br.com.jumarket.autoatentimentoapi.controller.response.*
import br.com.jumarket.autoatentimentoapi.exception.BadRequestException
import br.com.jumarket.autoatentimentoapi.exception.NotFoundException
import br.com.jumarket.autoatentimentoapi.model.OrderEntity
import br.com.jumarket.autoatentimentoapi.model.enums.OrderStatusEnum
import br.com.jumarket.autoatentimentoapi.model.enums.TypePaymentEnum
import br.com.jumarket.autoatentimentoapi.repository.OrderRepository
import br.com.jumarket.autoatentimentoapi.repository.ShoppingCartRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*


@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) {

    fun findById(id: UUID): OrderResponse {
        return orderRepository.findByIdOrNull(id)?.let { order ->
            OrderResponse(
                id = order.id,
                createAt = order.createdAt,
                typesPayments = TypePaymentEnum.values().toList().map { it.name },
                status = order.status.name,
                totOrderValue = order.totOrderValue,
                shoppingCart = ShoppingCartResponse(id = order.shoppingCart.id,
                    createAt = order.shoppingCart.createdAt,
                    totSaleValue = order.shoppingCart.totSaleValue,
                    items = order.shoppingCart.items.map { cartItem ->
                        ShoppingCartItemsResponse(
                            id = cartItem.id,
                            product = ProductResponse(
                                id = cartItem.product.id,
                                name = cartItem.product.name,
                                costValue = cartItem.product.costValue,
                                saleValue = cartItem.product.saleValue,
                                sku = cartItem.product.sku,
                                unidadeType = cartItem.product.unidadeType,
                                productCategory = ProductCategoryResponse(
                                    cartItem.product.productCategory.id, cartItem.product.productCategory.name
                                )
                            ),
                            amount = cartItem.amount,
                            saleValue = cartItem.saleValue,
                            totalValue = cartItem.totalValue
                        )
                    })
            )
        } ?: throw NotFoundException("Pedido não encontrado")
    }

    fun createOrder(orderCreateRequest: OrderCreateRequest): OrderResponse {

        val shoppingCart = shoppingCartRepository.findByIdOrNull(orderCreateRequest.shoppingCartId)
            ?: throw BadRequestException("Carrinho não encontrado")

        val order = OrderEntity(
            shoppingCart = shoppingCart,
            status = OrderStatusEnum.AGUARDANDO_PAGAMENTO,
            totOrderValue = shoppingCart.totSaleValue,
            typePayment = null
        )

        orderRepository.save(order)

        return OrderResponse(
            id = order.id,
            createAt = order.createdAt,
            typesPayments = TypePaymentEnum.values().toList().map { it.name },
            status = order.status.name,
            totOrderValue = order.totOrderValue,
            shoppingCart = ShoppingCartResponse(id = shoppingCart.id,
                createAt = shoppingCart.createdAt,
                totSaleValue = shoppingCart.totSaleValue,
                items = shoppingCart.items.map { cartItem ->
                    ShoppingCartItemsResponse(
                        id = cartItem.id, product = ProductResponse(
                            id = cartItem.product.id,
                            name = cartItem.product.name,
                            costValue = cartItem.product.costValue,
                            saleValue = cartItem.product.saleValue,
                            sku = cartItem.product.sku,
                            unidadeType = cartItem.product.unidadeType,
                            productCategory = ProductCategoryResponse(
                                cartItem.product.productCategory.id, cartItem.product.productCategory.name
                            )
                        ), amount = cartItem.amount, saleValue = cartItem.saleValue, totalValue = cartItem.totalValue
                    )
                })
        )
    }

    fun cancel(id: UUID) {
        val order = orderRepository.findByIdOrNull(id) ?: throw NotFoundException("Pedido não encontrado")

        if (OrderStatusEnum.AGUARDANDO_PAGAMENTO != order.status) {
            throw BadRequestException("Para cancelar o pedido deve estar com status de aguardando pagamento")
        }

        order.status = OrderStatusEnum.CANCELADO
        orderRepository.save(order)
    }

    fun payment(id: UUID, orderPaymentRequest: OrderPaymentRequest) {

        var typePaymentEnum = TypePaymentEnum.values().find { it.name == orderPaymentRequest.typePayment }
            ?: throw BadRequestException("Meio de pagamento não existe")

        val order = orderRepository.findByIdOrNull(id) ?: throw NotFoundException("Pedido não encontrado")

        if (OrderStatusEnum.AGUARDANDO_PAGAMENTO != order.status) {
            throw BadRequestException("Para informar pagamento o pedido deve estar com status de aguardando pagamento")
        }

        order.status = OrderStatusEnum.PAGO
        order.typePayment = typePaymentEnum
        orderRepository.save(order)
    }
}