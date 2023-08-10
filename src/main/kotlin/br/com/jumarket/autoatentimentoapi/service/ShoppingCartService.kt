package br.com.jumarket.autoatentimentoapi.service

import br.com.jumarket.autoatentimentoapi.controller.request.CreateShoppingCartRequest
import br.com.jumarket.autoatentimentoapi.controller.response.ProductCategoryResponse
import br.com.jumarket.autoatentimentoapi.controller.response.ProductResponse
import br.com.jumarket.autoatentimentoapi.controller.response.ShoppingCartItemsResponse
import br.com.jumarket.autoatentimentoapi.controller.response.ShoppingCartResponse
import br.com.jumarket.autoatentimentoapi.exception.BadRequestException
import br.com.jumarket.autoatentimentoapi.model.ShoppingCartEntity
import br.com.jumarket.autoatentimentoapi.model.ShoppingCartItemsEntity
import br.com.jumarket.autoatentimentoapi.repository.ProductRepository
import br.com.jumarket.autoatentimentoapi.repository.ShoppingCartItemsRepository
import br.com.jumarket.autoatentimentoapi.repository.ShoppingCartRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


@Service
class ShoppingCartService(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val shoppingCartItemsRepository: ShoppingCartItemsRepository,
    private val productRepository: ProductRepository
) {

    @Transactional
    fun createShoppingCart(createShoppingCartRequest: CreateShoppingCartRequest): ShoppingCartResponse {

        val product = productRepository.findBySku(createShoppingCartRequest.productSku)
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
                    product.productCategory.id,
                    product.productCategory.name
                )
            ), amount = shoppingCartItemsEntity.amount,
            saleValue = shoppingCartItemsEntity.saleValue,
            totalValue = shoppingCartItemsEntity.totalValue
        )

        val cartResponse = ShoppingCartResponse(
            id = shoppingCart.id,
            createAt = shoppingCart.createdAt,
            totSaleValue = shoppingCart.totSaleValue,
            items = setOf(itemResponse)
        )

        return cartResponse
    }

    @Transactional
    fun addItemShoppingCart(id: UUID, createShoppingCartRequest: CreateShoppingCartRequest): ShoppingCartResponse {

        val product = productRepository.findBySku(createShoppingCartRequest.productSku)
            ?: throw BadRequestException("Produto não cadastrado com este códio de barras")

        //busca se ja existe o mesmo produto dentro do carrinho

        // se existe o mesmo produto entao eu devo acrescentar na qtd e somar valores e salvar o carrinho e o item

        // se nao é o mesmo segue o mesmo fluxo de ciração inicial sem a parte de criar o carrinho somente atualizar o carrinho e criar o item
    }
}