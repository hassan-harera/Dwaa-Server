package com.harera.hayatserver.mapper

import com.harera.hayatserver.model.dto.model.Packaging
import com.harera.hayatserver.model.entity.PackagingEntity

object PackagingMapper {

    @Throws(NullPointerException::class)
    fun map(packagingEntity: PackagingEntity): Packaging {
        return Packaging(
            packagingEntity.packagingId!!,
            packagingEntity.packagingType!!
        )
    }
}