package com.harera.dwaaserver.service.user

import com.englizya.security.AuthenticationManager
import com.harera.dwaaserver.model.user.LoginRequest
import com.harera.dwaaserver.model.user.LoginResponse
import com.harera.dwaaserver.model.user.OAuthLoginRequest
import com.harera.dwaaserver.repository.UserRepository
import com.harera.dwaaserver.util.MStringUtils.isEmail
import com.harera.dwaaserver.util.MStringUtils.isPhoneNumber
import com.harera.dwaaserver.util.MStringUtils.isUsername
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


interface UserService {

    fun login(loginRequest: LoginRequest): LoginResponse?
    fun login(oAuthLoginRequest: OAuthLoginRequest): LoginResponse?
}

@Service
open class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userValidation: UserValidation,
    private val authenticationManager: AuthenticationManager,
) : UserService {

    override fun login(loginRequest: LoginRequest): LoginResponse? {
        userValidation.validateLogin(loginRequest)
        val uid = getUid(loginRequest.subject!!)
        val loginResponse =  LoginResponse(authenticationManager.generateToken(uid!!))
        return loginResponse
    }

    override fun login(oAuthLoginRequest: OAuthLoginRequest): LoginResponse? {
        userValidation.validateLogin(oAuthLoginRequest)
//        TODO("")
        return null
    }

    private fun getUid(subject: String): String? {
        return if (isPhoneNumber(subject)) {
            userRepository.findByPhoneNumber(subject).uid.toString()
        } else if (isEmail(subject)) {
            userRepository.findByEmail(subject).uid.toString()
        } else if (isUsername(subject)){
            userRepository.findByUsername(subject).uid.toString()
        } else {
            return null
        }
    }
}