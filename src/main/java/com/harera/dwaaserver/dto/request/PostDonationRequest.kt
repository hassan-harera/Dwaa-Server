package com.harera.dwaaserver.dto.request

import com.harera.dwaaserver.dto.UserLocation

data class PostDonationRequest (
    val medicineId: Int,
    val packagingId: Int,
    val quantity: Int,
    val price: Int,
    val description: String,
    val validityInDays: Int,
    val validityInMonths: Int,
    val validityInYears: Int,
    val productionDate: String,
    val location: UserLocation
)