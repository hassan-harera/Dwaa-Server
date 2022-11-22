package com.harera.hayatserver.model.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Table(name = "Medicine")
@Entity
open class MedicineEntity {

    @Id
    @Column(name = "id")
    open var medicineId: Int? = null

    @Column(name = "name")
    open var medicineName: String? = null

    @Column(name = "unit_id", insertable = false, updatable = false)
    open var packageId: Int? = null

    @Column(name = "unit_id")
    lateinit open var medicineDescription: String

    @Column(name = "inserting_date")
    open var insertingDate: Int? = null

    @Column(name = "category_id")
    open var categoryId: Int? = null

    constructor()
}