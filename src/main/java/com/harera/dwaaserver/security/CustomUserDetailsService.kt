package com.harera.dwaaserver.security

import com.harera.dwaaserver.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        try {
            val uid: Int = username.toInt()
            val user = userRepository.getUserWithUid(uid).get()
            return CustomUserDetails(user.uid.toString(), user.password!!)
        } catch (exception: Exception) {
            exception.printStackTrace()
            return null
        }
    }
}