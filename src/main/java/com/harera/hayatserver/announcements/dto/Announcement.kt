package com.harera.hayatserver.announcements.dto

class Announcement {
    var id: Long = 0
    lateinit var title: String
    var description: String? = null
    lateinit var imageUrl: String
    lateinit var startDate: String
    lateinit var endDate: String
}