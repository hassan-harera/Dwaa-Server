package com.harera.medicine.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Table(name = "Medicine")
@Entity
class PackagingEntity {

    @Id
    @Column(name = "package_id")
    var packagingId: Int? = null

    @Column(name = "packaging_type")
    var packagingType: String? = null
}