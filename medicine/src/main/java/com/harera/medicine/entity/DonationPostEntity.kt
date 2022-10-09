package com.harera.medicine.entity

import java.sql.Date
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Donation_Post", schema = "public", catalog = "Dwaa")
class DonationPostEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "post_id")
    var postId: Int? = null

    @Basic
    @Column(name = "post_title")
    var postTitle: String? = null

    @Basic
    @Column(name = "post_description")
    var postDescription: String? = null

    @Basic
    @Column(name = "post_date")
    var postDate: Date? = null

    @Basic
    @Column(name = "user_id")
    var userId: String? = null

    @Basic
    @Column(name = "medicine_production_date")
    var medicineProductionDate: Date? = null

    @Basic
    @Column(name = "medicine_validity_months")
    var medicineValidityMonths: Int? = null

    @Basic
    @Column(name = "medicine_validity_days")
    var medicineValidityDays: Int? = null

    @Basic
    @Column(name = "medicine_validity_years")
    var medicineValidityYears: Int? = null

    @Basic
    @Column(name = "user_location_id")
    var userLocationId: Int? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as DonationPostEntity
        return postId == that.postId && postTitle == that.postTitle && postDescription == that.postDescription && postDate == that.postDate && userId == that.userId && medicineProductionDate == that.medicineProductionDate && medicineValidityMonths == that.medicineValidityMonths && medicineValidityDays == that.medicineValidityDays && medicineValidityYears == that.medicineValidityYears && userLocationId == that.userLocationId
    }

    override fun hashCode(): Int {
        return Objects.hash(
            postId,
            postTitle,
            postDescription,
            postDate,
            userId,
            medicineProductionDate,
            medicineValidityMonths,
            medicineValidityDays,
            medicineValidityYears,
            userLocationId
        )
    }
}