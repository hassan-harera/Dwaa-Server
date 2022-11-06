package com.harera.dwaaserver.mapper

import com.harera.dwaaserver.common.exception.NullDateException
import com.harera.dwaaserver.common.mapper.DateMapper
import com.harera.dwaaserver.model.dto.request.PostDonationRequest
import com.harera.dwaaserver.model.entity.DonationPostEntity
import java.util.*

object DonationPostEntityMapper {

    @Throws(NullDateException::class)
    fun map(uid: Int, request: PostDonationRequest): DonationPostEntity {
        return DonationPostEntity(
            uid = uid,
            medicineId = request.medicineId,
            medicineExpirationDate = DateMapper.map(request.expirationDate) ?: throw NullDateException(),
            postDescription = request.description,
            postDate = Date(),
            cityId = request.cityId,
            postStateId = 1,
        )
    }
}