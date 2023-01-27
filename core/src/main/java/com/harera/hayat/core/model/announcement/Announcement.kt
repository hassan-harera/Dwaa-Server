package com.harera.hayat.core.model.announcement

import com.harera.hayat.core.model.BaseEntity
import java.time.OffsetDateTime
import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "announcement")
open class Announcement : BaseEntity {

    @Basic
    @Column(name = "end_date", nullable = false)
    var endDate: OffsetDateTime? = null

    @Basic
    @Column(name = "start_date", nullable = false)
    var startDate: OffsetDateTime? = null

    @Basic
    @Column(name = "description", nullable = true)
    open var description: String? = null

    @Basic
    @Column(name = "title", nullable = false)
    lateinit var title: String

    constructor()
}