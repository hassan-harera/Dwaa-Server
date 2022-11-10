package com.harera.hayatserver.announcements.entity

import com.englizya.announcements.utils.Defaults
import com.harera.hayatserver.model.BaseEntity
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "announcement")
open class AnnouncementEntity : BaseEntity {

    @Basic
    @Column(name = "end_date", nullable = false)
    lateinit var endDate: Date

    @Basic
    @Column(name = "start_date", nullable = false)
    lateinit var startDate: Date

    @Basic
    @Column(name = "description", nullable = true)
    var description: String? = null

    @Basic
    @Column(name = "image", nullable = false)
    var imageUrl: String = Defaults.DEFAULT_IMAGE_URL

    @Basic
    @Column(name = "title", nullable = false)
    var title: String = ""

    constructor()
}