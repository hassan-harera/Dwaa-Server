package com.harera.hayat.core.repository.announcement

import com.harera.hayat.core.model.announcement.Announcement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AnnouncementRepository : JpaRepository<Announcement, Long> {

    @Query("select a from Announcement a where a.active = true")
    fun findActiveOffers(): List<Announcement>

    @Query("select a from Announcement a where a.active = false")
    fun findInactiveOffers(): List<Announcement>
}