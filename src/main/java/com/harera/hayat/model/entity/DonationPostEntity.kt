package com.harera.hayat.model.entity

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.harera.hayat.config.OffsetDateTimeSerializer
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "donation_post", schema = "public", catalog = "Dwaa")
open class DonationPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    open var postId: Int? = null

    @Basic
    @Column(name = "post_description")
    open var postDescription: String? = null

    @Basic
    @Column(name = "post_date")
    open var postDate: Date? = null

    @Basic
    @Column(name = "medicine_id")
    open var medicineId: Int? = null

    @Basic
    @Column(name = "medicine_expiration_date")
    @JsonSerialize(using = OffsetDateTimeSerializer::class)
    open var medicineExpirationDate: Date? = null

    @Basic
    @Column(name = "city_id")
    open var cityId: Int? = null

    @Basic
    @Column(name = "uid")
    open var uid: Int? = null

    @Basic
    @Column(name = "post_state_id")
    open var postStateId: Int = 1

    constructor()

    constructor(
        uid: Int,
        medicineId: Int,
        medicineExpirationDate: Date,
        postDescription: String? = null,
        postDate: Date,
        cityId: Int,
        postStateId: Int = 1,
    ) {
        this.postDescription = postDescription
        this.postDate = postDate
        this.medicineId = medicineId
        this.medicineExpirationDate = medicineExpirationDate
        this.cityId = cityId
        this.uid = uid
        this.postStateId = postStateId
    }
}