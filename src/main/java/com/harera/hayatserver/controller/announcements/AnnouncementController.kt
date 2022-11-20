package com.harera.hayatserver.controller.announcements

import com.harera.hayatserver.util.Parameter
import com.harera.hayatserver.model.announcement.AnnouncementDto
import com.harera.hayatserver.service.announcement.AnnouncementService
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
    fun get(): ResponseEntity<List<AnnouncementDto>> {
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
    ): ResponseEntity<AnnouncementDto> {
        return announcementService
            .getAnnouncement(announcementId)
            .let {
                ResponseEntity.ok(it)
            }
    }
}