package com.harera.hayatserver.announcements.service

import com.harera.hayatserver.announcements.dto.Announcement
import com.harera.hayatserver.announcements.repository.AnnouncementEntityRepository
import com.harera.hayatserver.exception.EntityNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

interface AnnouncementService {
    fun getAnnouncements(): List<Announcement>
    fun getAnnouncement(announcementId: Int): Announcement
}

@Service
class AnnouncementServiceImpl(
    private val announcementEntityRepository: AnnouncementEntityRepository,
    private val modelMapper: ModelMapper
) : AnnouncementService {

    override fun getAnnouncements(): List<Announcement> {
        return getActiveAnnouncements();
    }

    override fun getAnnouncement(announcementId: Int): Announcement {
        return announcementEntityRepository
            .findById(announcementId)
            .orElseThrow {
                EntityNotFoundException("Announcement with id $announcementId not found")
            }
            .let {
                modelMapper.map(it, Announcement::class.java)
            }
    }

    private fun getInactiveAnnouncements(): List<Announcement> {
        return announcementEntityRepository.findInactiveOffers().map {
            modelMapper.map(it, Announcement::class.java)
        }
    }

    private fun getActiveAnnouncements(): List<Announcement> {
        return announcementEntityRepository.findActiveOffers().map {
            modelMapper.map(it, Announcement::class.java)
        }
    }

    private fun getAllAnnouncements(): List<Announcement> {
        return announcementEntityRepository.findAll().map {
            modelMapper.map(it, Announcement::class.java)
        }
    }
}


