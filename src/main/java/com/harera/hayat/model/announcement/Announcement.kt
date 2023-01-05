package com.harera.hayat.model.announcement

import com.harera.hayat.model.BaseEntity
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
    lateinit var endDate: OffsetDateTime

    @Basic
    @Column(name = "start_date", nullable = false)
    lateinit var startDate: OffsetDateTime

    @Basic
    @Column(name = "description", nullable = true)
    open var description: String? = null

    @Basic
    @Column(name = "title", nullable = false)
    lateinit var title: String

    constructor()
}