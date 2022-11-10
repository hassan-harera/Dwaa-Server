package com.harera.dwaaserver.announcements.repository;

import com.harera.dwaaserver.announcements.entity.AnnouncementEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AnnouncementEntityRepository : JpaRepository<AnnouncementEntity, Int> {

    @Query("select a from AnnouncementEntity a where a.active = true")
    fun findActiveOffers(): List<AnnouncementEntity>

    @Query("select a from AnnouncementEntity a where a.active = false")
    fun findInactiveOffers(): List<AnnouncementEntity>
}