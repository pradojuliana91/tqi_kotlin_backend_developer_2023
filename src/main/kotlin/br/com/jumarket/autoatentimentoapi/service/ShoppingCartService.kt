package br.com.jumarket.autoatentimentoapi.service

import br.com.jumarket.autoatentimentoapi.controller.request.ShoppingCartRequest
import br.com.jumarket.autoatentimentoapi.controller.response.ProductCategoryResponse
import br.com.jumarket.autoatentimentoapi.controller.response.ProductResponse
import br.com.jumarket.autoatentimentoapi.controller.response.ShoppingCartItemsResponse
import br.com.jumarket.autoatentimentoapi.controller.response.ShoppingCartResponse
import br.com.jumarket.autoatentimentoapi.exception.BadRequestException
import br.com.jumarket.autoatentimentoapi.exception.NotFoundException
import br.com.jumarket.autoatentimentoapi.model.ShoppingCartEntity
import br.com.jumarket.autoatentimentoapi.model.ShoppingCartItemsEntity
import br.com.jumarket.autoatentimentoapi.repository.ProductRepository
import br.com.jumarket.autoatentimentoapi.repository.ShoppingCartItemsRepository
import br.com.jumarket.autoatentimentoapi.repository.ShoppingCartRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*


@Service
class ShoppingCartService(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val shoppingCartItemsRepository: ShoppingCartItemsRepository,
    private val productRepository: ProductRepository
) {

    fun findById(id: UUID): ShoppingCartResponse {

        val shoppingCart = shoppingCartRepository.findByIdOrNull(id) ?: throw NotFoundException("Carrinho não existe")

        val shoppingCartItems = shoppingCartItemsRepository.findByShoppingCartId(id)
            ?: throw NotFoundException("Não existe item no carrinho")

        val itemsResponse = shoppingCartItems.map { cartItem ->
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
        }

        return ShoppingCartResponse(
            id = shoppingCart.id,
            createAt = shoppingCart.createdAt,
            totSaleValue = shoppingCart.totSaleValue,
            items = itemsResponse
        )
    }

    @Transactional
    fun createShoppingCart(shoppingCartRequest: ShoppingCartRequest): ShoppingCartResponse {

        val product = productRepository.findBySku(shoppingCartRequest.productSku)
            ?: throw BadRequestException("Produto não cadastrado com este códio de barras")

        val shoppingCart = ShoppingCartEntity(totSaleValue = product.saleValue)

        shoppingCartRepository.save(shoppingCart)

        val shoppingCartItemsEntity = ShoppingCartItemsEntity(
            shoppingCart = shoppingCart,
            product = product,
            amount = 1,
            saleValue = product.saleValue,
            totalValue = product.saleValue
        )

        shoppingCartItemsRepository.save(shoppingCartItemsEntity)

        val itemResponse = ShoppingCartItemsResponse(
            id = shoppingCartItemsEntity.id,
            product = ProductResponse(
                id = product.id,
                name = product.name,
                costValue = product.costValue,
                saleValue = product.saleValue,
                sku = product.sku,
                unidadeType = product.unidadeType,
                productCategory = ProductCategoryResponse(
                    product.productCategory.id, product.productCategory.name
                )
            ),
            amount = shoppingCartItemsEntity.amount,
            saleValue = shoppingCartItemsEntity.saleValue,
            totalValue = shoppingCartItemsEntity.totalValue
        )

        return ShoppingCartResponse(
            id = shoppingCart.id,
            createAt = shoppingCart.createdAt,
            totSaleValue = shoppingCart.totSaleValue,
            items = listOf(itemResponse)
        )
    }

    @Transactional
    fun addItemShoppingCart(id: UUID, shoppingCartRequest: ShoppingCartRequest): ShoppingCartResponse {

        val product = productRepository.findBySku(shoppingCartRequest.productSku)
            ?: throw BadRequestException("Produto não cadastrado com este código de barras")

        val shoppingCart = shoppingCartRepository.findByIdOrNull(id) ?: throw NotFoundException("Carrinho não existe")

        val shoppingCartItem =
            shoppingCartItemsRepository.findByShoppingCartIdAndProductSku(id, shoppingCartRequest.productSku)
                ?.let { shoppingCartItem ->
                    shoppingCartItem.amount += 1
                    shoppingCartItem.totalValue =
                        shoppingCartItem.saleValue.multiply(BigDecimal(shoppingCartItem.amount))
                    shoppingCartItem
                } ?: ShoppingCartItemsEntity(
                shoppingCart = shoppingCart,
                product = product,
                amount = 1,
                saleValue = product.saleValue,
                totalValue = product.saleValue
            )

        shoppingCartItemsRepository.save(shoppingCartItem)

        val shoppingCartItems = shoppingCartItemsRepository.findByShoppingCartId(id)
            ?: throw NotFoundException("Não existe item no carrinho")

        shoppingCart.totSaleValue = shoppingCartItems.map { item -> item.totalValue }.sumOf { it }

        shoppingCartRepository.save(shoppingCart)

        val itemsResponse = shoppingCartItems.map { cartItem ->
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
        }

        return ShoppingCartResponse(
            id = shoppingCart.id,
            createAt = shoppingCart.createdAt,
            totSaleValue = shoppingCart.totSaleValue,
            items = itemsResponse
        )
    }
}