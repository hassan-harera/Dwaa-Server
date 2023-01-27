package com.harera.hayat.core.service.announcement

import com.harera.hayat.exception.EntityNotFoundException
import com.harera.hayat.model.announcement.AnnouncementResponse
import com.harera.hayat.repository.announcement.AnnouncementRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

interface AnnouncementService {
    fun list(): List<AnnouncementResponse>
    fun get(announcementId: Long): AnnouncementResponse
}

@Service
class AnnouncementServiceImpl(
    private val announcementRepository: AnnouncementRepository,
    private val modelMapper: ModelMapper
) : AnnouncementService {

    override fun list(): List<AnnouncementResponse> {
        return getActiveAnnouncements();
    }

    override fun get(announcementId: Long): AnnouncementResponse {
        return announcementRepository
            .findById(announcementId)
            .orElseThrow {
                EntityNotFoundException("Announcement with id $announcementId not found")
            }
            .let {
                modelMapper.map(it, AnnouncementResponse::class.java)
            }
    }

    private fun getActiveAnnouncements(): List<AnnouncementResponse> {
        return announcementRepository.findActiveOffers().map {
            modelMapper.map(it, AnnouncementResponse::class.java)
        }
    }
}
