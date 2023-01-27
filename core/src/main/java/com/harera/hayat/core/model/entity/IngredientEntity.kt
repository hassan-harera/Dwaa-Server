package com.harera.hayat.core.model.entity

import javax.persistence.*

@Entity
@Table(name = "Ingredient", schema = "public", catalog = "Dwaa")
open class IngredientEntity {

    @get:Id
    @get:Column(name = "ingredient_id", nullable = false)
    open var ingredientId: Int? = null

    @get:Basic
    @get:Column(name = "ingredient_name", nullable = false)
    open var ingredientName: String? = null

    constructor()
}

