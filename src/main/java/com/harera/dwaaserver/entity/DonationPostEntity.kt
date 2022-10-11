package com.harera.dwaaserver.entity

import java.sql.Date
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Donation_Post", schema = "public", catalog = "Dwaa")
open class DonationPostEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "post_id")
    open var postId: Int? = null

    @Basic
    @Column(name = "post_title")
    open var postTitle: String? = null

    @Basic
    @Column(name = "post_description")
    open var postDescription: String? = null

    @Basic
    @Column(name = "post_date")
    open var postDate: Date? = null

    @Basic
    @Column(name = "user_id")
    open var userId: String? = null

    @Basic
    @Column(name = "medicine_production_date")
    open var medicineProductionDate: Date? = null

    @Basic
    @Column(name = "medicine_validity_months")
    open var medicineValidityMonths: Int? = null

    @Basic
    @Column(name = "medicine_validity_days")
    open var medicineValidityDays: Int? = null

    @Basic
    @Column(name = "medicine_validity_years")
    open var medicineValidityYears: Int? = null

    @Basic
    @Column(name = "user_location_id")
    open var userLocationId: Int? = null

    constructor()
}