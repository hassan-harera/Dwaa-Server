package com.harera.hayatserver.service.announcement

import com.harera.hayatserver.model.announcement.AnnouncementResponse
import com.harera.hayatserver.repository.announcement.AnnouncementRepository
import com.harera.hayatserver.exception.EntityNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

interface AnnouncementService {
    fun getAnnouncements(): List<AnnouncementResponse>
    fun getAnnouncement(announcementId: Long): AnnouncementResponse
}

@Service
class AnnouncementServiceImpl(
    private val announcementRepository: AnnouncementRepository,
    private val modelMapper: ModelMapper
) : AnnouncementService {

    override fun getAnnouncements(): List<AnnouncementResponse> {
        return getActiveAnnouncements();
    }

    override fun getAnnouncement(announcementId: Long): AnnouncementResponse {
        return announcementRepository
            .findById(announcementId)
            .orElseThrow {
                EntityNotFoundException("Announcement with id $announcementId not found")
            }
            .let {
                modelMapper.map(it, AnnouncementResponse::class.java)
            }
    }

    private fun getInactiveAnnouncements(): List<AnnouncementResponse> {
        return announcementRepository.findInactiveOffers().map {
            modelMapper.map(it, AnnouncementResponse::class.java)
        }
    }

    private fun getActiveAnnouncements(): List<AnnouncementResponse> {
        return announcementRepository.findActiveOffers().map {
            modelMapper.map(it, AnnouncementResponse::class.java)
        }
    }

    private fun getAllAnnouncements(): List<AnnouncementResponse> {
        return announcementRepository.findAll().map {
            modelMapper.map(it, AnnouncementResponse::class.java)
        }
    }
}


