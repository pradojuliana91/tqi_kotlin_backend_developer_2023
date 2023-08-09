package br.com.jumarket.autoatentimentoapi.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*


@Entity
@Table(name = "produtos")
data class ProductEntity(
    @Id
    var id: UUID = UUID.randomUUID(),

    var sku: String,

    @Column(name = "nome")
    var name: String,

    @ManyToOne
    @JoinColumn(name = "produto_categoria_id")
    var productCategory: ProductCategoryEntity,

    @Column(name = "unidade_medida")
    var unidadeType: String,

    @Column(name = "preco_unitario")
    var costValue: BigDecimal,

    @Column(name = "preco_venda")
    var saleValue: BigDecimal
)