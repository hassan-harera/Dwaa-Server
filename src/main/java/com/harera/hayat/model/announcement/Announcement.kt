package com.harera.hayat.model.announcement

import com.harera.hayat.model.BaseEntity
import java.time.ZonedDateTime
import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "announcement")
open class Announcement : BaseEntity {

    @Basic
    @Column(name = "end_date", nullable = false)
    lateinit var endDate: ZonedDateTime

    @Basic
    @Column(name = "start_date", nullable = false)
    lateinit var startDate: ZonedDateTime

    @Basic
    @Column(name = "description", nullable = true)
    open var description: String? = null

//    @Basic
//    @Column(name = "image", nullable = false)
//    lateinit var imageUrl: String

    @Basic
    @Column(name = "title", nullable = false)
    lateinit var title: String

    constructor()
}