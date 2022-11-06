package com.harera.dwaaserver.data

import com.harera.dwaaserver.model.dto.request.PostDonationRequest
import com.harera.dwaaserver.model.entity.DonationPostEntity
import java.util.*


val cityId = 1
val postDescription = "post-description"
val postDate = Date()

val donationPostEntity = DonationPostEntity(
    uid,
    medicineId,
    medicineExpirationDate,
    postDescription,
    postDate,
    cityId,
)

val donationPostRequest = PostDonationRequest(
    medicineId = 999999999,
    packagingId = 1,
    quantity = 1.0f,
    description = "description",
    expirationDate = "2020-12",
    cityId = 1
)