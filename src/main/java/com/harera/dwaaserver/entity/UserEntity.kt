package com.harera.dwaaserver.entity

import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "user", schema = "public", catalog = "Dwaa")
class UserEntity {

    @Id
    @Column(name = "uid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uid: Int? = null

    @Column(name = "username", unique = true)
    var username: String? = null

    @Email(message = "Email should be valid")
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
        username: String,
        password: String,
        lastName: String,
        firstName: String,
        phoneNumber: String? = null,
    ) {
        this.username = username
        this.password = password
        this.phoneNumber = phoneNumber
        this.lastName = lastName
        this.firstName = firstName
    }
}