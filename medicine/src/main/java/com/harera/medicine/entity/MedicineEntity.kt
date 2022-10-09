package com.harera.medicine.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Table(name = "Medicine")
@Entity
class MedicineEntity {

    @Id
    @Column(name = "medicine_id")
    var medicineId: Int? = null

    @Column(name = "medicine_name")
    val medicineName: String? = null

    @Column(name = "package_id")
    val packageId: Int? = null

    @Column(name = "package_id")
    lateinit var medicineDescription: String

    @Column(name = "inserting_date")
    val insertingDate: Int? = null

    @Column(name = "category_id")
    val categoryId: Int? = null
}