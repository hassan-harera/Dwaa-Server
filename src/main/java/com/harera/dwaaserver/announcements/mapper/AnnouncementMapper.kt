package com.harera.dwaaserver.announcements.mapper

import com.harera.dwaaserver.announcements.dto.Announcement
import com.harera.dwaaserver.announcements.entity.AnnouncementEntity

//object AnnouncementMapper {
//
//    fun toAnnouncement(announcementEntity: AnnouncementEntity): Announcement {
//        return Announcement(
//            announcementId = announcementEntity.id,
//            announcementTitle = announcementEntity.title,
//            announcementDescription = announcementEntity.description,
//            announcementImageUrl = announcementEntity.imageUrl,
//            startDate = announcementEntity.announcementStartDate.let { DateTimeMapper.map(it).toString() },
//            endDate = announcementEntity.announcementEndDate.let { DateTimeMapper.map(it).toString() },
//        )
//    }
//}