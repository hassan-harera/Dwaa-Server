package com.harera.hayatserver.model.dto

data class UserLocation(
    val userId: String,
    val userName: String,
    val userPhoneNumber: String,
    val locationFloor: String,
    val locationAddress: String,
    val locationCity: String,
    val locationState: String,
    val locationPostalCode: String,
    val locationLatitude: Double,
    val locationLongitude: Double,
)
