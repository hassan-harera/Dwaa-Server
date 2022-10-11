package com.harera.dwaaserver.entity

import com.harera.dwaaserver.entity.pk.MedicineIngredientPK
import javax.persistence.*

@Entity
@IdClass(MedicineIngredientPK::class)
@Table(name = "Medicine_Ingredient", schema = "public", catalog = "Dwaa")
open class MedicineIngredientEntity {

    @get:Id
    @get:Basic
    @get:Column(name = "medicine_id", nullable = false)
    open var medicineId: Int? = null

    @get:Id
    @get:Basic
    @get:Column(name = "ingredient_id", nullable = false)
    open var ingredientId: Int? = null

    @get:Basic
    @get:Column(name = "concentration", nullable = false)
    open var concentration: Double? = null

    constructor()
}

