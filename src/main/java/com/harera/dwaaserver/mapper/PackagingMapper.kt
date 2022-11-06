package com.harera.dwaaserver.mapper

import com.harera.dwaaserver.model.dto.model.Packaging
import com.harera.dwaaserver.model.entity.PackagingEntity

object PackagingMapper {

    @Throws(NullPointerException::class)
    fun map(packagingEntity: PackagingEntity): Packaging {
        return Packaging(
            packagingEntity.packagingId!!,
            packagingEntity.packagingType!!
        )
    }
}