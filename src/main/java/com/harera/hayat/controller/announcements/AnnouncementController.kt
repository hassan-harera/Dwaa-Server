package com.harera.hayat.controller.announcements

import com.harera.hayat.util.Parameter
import com.harera.hayat.model.announcement.AnnouncementResponse
import com.harera.hayat.service.announcement.AnnouncementService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/announcements")
class AnnouncementController(
    private val announcementService: AnnouncementService
) {

    @GetMapping
    fun get(): ResponseEntity<List<AnnouncementResponse>> {
        return announcementService.getAnnouncements().let {
            ResponseEntity.ok(it)
        }
    }

    @GetMapping("/{${Parameter.ANNOUNCEMENT_ID}}")
    fun get(
        @PathVariable(
            value = Parameter.ANNOUNCEMENT_ID,
            required = true
        ) announcementId: Long
    ): ResponseEntity<AnnouncementResponse> {
        return announcementService
            .getAnnouncement(announcementId)
            .let {
                ResponseEntity.ok(it)
            }
    }
}