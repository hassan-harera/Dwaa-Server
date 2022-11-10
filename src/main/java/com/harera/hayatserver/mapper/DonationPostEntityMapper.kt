package com.harera.hayatserver.mapper

import com.harera.hayatserver.common.exception.NullDateException
import com.harera.hayatserver.common.mapper.DateMapper
import com.harera.hayatserver.model.dto.request.PostDonationRequest
import com.harera.hayatserver.model.entity.DonationPostEntity
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