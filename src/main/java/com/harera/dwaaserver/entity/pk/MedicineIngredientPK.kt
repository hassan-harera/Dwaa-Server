package com.harera.dwaaserver.entity.pk

import java.io.Serializable


class MedicineIngredientPK : Serializable {

    var medicineId : Int?= null
    var ingredientId : Int? = null

    constructor()

    constructor(medicineId: Int?, ingredientId: Int?) {
        this.medicineId = medicineId
        this.ingredientId = ingredientId
    }
}