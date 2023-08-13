package br.com.jumarket.autoatendimentoapi.model


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*


@Entity
@Table(name = "produtos_categorias")
class ProductCategoryEntity(
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(name = "nome")
    var name: String
)