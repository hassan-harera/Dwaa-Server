package com.harera.hayatserver.service.announcement

import com.harera.hayatserver.model.announcement.AnnouncementDto
import com.harera.hayatserver.repository.announcement.AnnouncementRepository
import com.harera.hayatserver.exception.EntityNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

interface AnnouncementService {
    fun getAnnouncements(): List<AnnouncementDto>
    fun getAnnouncement(announcementId: Long): AnnouncementDto
}

@Service
class AnnouncementServiceImpl(
    private val announcementRepository: AnnouncementRepository,
    private val modelMapper: ModelMapper
) : AnnouncementService {

    override fun getAnnouncements(): List<AnnouncementDto> {
        return getActiveAnnouncements();
    }

    override fun getAnnouncement(announcementId: Long): AnnouncementDto {
        return announcementRepository
            .findById(announcementId)
            .orElseThrow {
                EntityNotFoundException("Announcement with id $announcementId not found")
            }
            .let {
                modelMapper.map(it, AnnouncementDto::class.java)
            }
    }

    private fun getInactiveAnnouncements(): List<AnnouncementDto> {
        return announcementRepository.findInactiveOffers().map {
            modelMapper.map(it, AnnouncementDto::class.java)
        }
    }

    private fun getActiveAnnouncements(): List<AnnouncementDto> {
        return announcementRepository.findActiveOffers().map {
            modelMapper.map(it, AnnouncementDto::class.java)
        }
    }

    private fun getAllAnnouncements(): List<AnnouncementDto> {
        return announcementRepository.findAll().map {
            modelMapper.map(it, AnnouncementDto::class.java)
        }
    }
}


