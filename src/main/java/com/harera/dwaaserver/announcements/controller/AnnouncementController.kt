package com.harera.dwaaserver.announcements.controller

import com.englizya.announcements.utils.Parameter
import com.harera.dwaaserver.announcements.dto.Announcement
import com.harera.dwaaserver.announcements.service.AnnouncementService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/announcements")
class AnnouncementController(
    private val announcementService: AnnouncementService
) {

    @GetMapping
    fun get(): ResponseEntity<List<Announcement>> {
        return announcementService.getAnnouncements().let {
            ResponseEntity.ok(it)
        }
    }

    @GetMapping("/{${Parameter.ANNOUNCEMENT_ID}}")
    fun get(
        @PathVariable(
            value = Parameter.ANNOUNCEMENT_ID,
            required = true
        ) announcementId: Int
    ): ResponseEntity<Announcement> {
        return announcementService
            .getAnnouncement(announcementId)
            .let {
                ResponseEntity.ok(it)
            }
    }
}