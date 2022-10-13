package com.harera.dwaaserver.dto.request

import com.harera.dwaaserver.common.utils.CustomPattern
import javax.validation.constraints.Pattern

data class PostDonationRequest (
    val medicineId: Int,
    val packagingId: Int,
    val quantity: Float,
    @field:Pattern(regexp = CustomPattern.DESCRIPTION)
    val description: String,
    @field:Pattern(regexp = CustomPattern.DATE_FORMAT)
    val expirationDate: String,
    val cityId: Int
)