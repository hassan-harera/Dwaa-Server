package com.harera.dwaaserver.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user", schema = "public", catalog = "Dwaa")
class UserEntity {

    @Id
    @Column(name = "uid")
    var uid: Int? = null

    @Column(name = "username", unique = true)
    var username: String? = null

    @Column(name = "email", unique = true)
    var email: String? = null

    @Column(name = "phone_number", unique = true)
    var phoneNumber: String? = null

    @Column(name = "first_name")
    var firstName: String? = null

    @Column(name = "last_name")
    var lastName: String? = null

    @Column(name = "password")
    var password: String? = null

    constructor() {}

    constructor(
        uid: Int,
        username: String,
        password: String,
        phoneNumber: String,
        lastName: String,
        firstName: String,
    ) {
        this.uid = uid
        this.username = username
        this.password = password
        this.phoneNumber = phoneNumber
        this.lastName = lastName
        this.firstName = firstName
    }
}