package com.harera.dwaaserver.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Table(name = "Medicine")
@Entity
open class PackagingEntity {

    @Id
    @Column(name = "packaging_id")
    open var packagingId: Int? = null

    @Column(name = "packaging_type")
    open var packagingType: String? = null

    constructor()
}