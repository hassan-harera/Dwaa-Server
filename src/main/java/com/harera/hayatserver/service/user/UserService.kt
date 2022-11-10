package com.harera.hayatserver.service.user

import com.englizya.security.AuthenticationManager
import com.harera.hayatserver.model.entity.UserEntity
import com.harera.hayatserver.model.user.*
import com.harera.hayatserver.repository.UserRepository
import com.harera.hayatserver.service.firebase.FirebaseService
import com.harera.hayatserver.util.StringRegexUtils.isEmail
import com.harera.hayatserver.util.StringRegexUtils.isPhoneNumber
import com.harera.hayatserver.util.StringRegexUtils.isUsername
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


interface UserService {

    fun login(loginRequest: LoginRequest): LoginResponse?
    fun login(oAuthLoginRequest: OAuthLoginRequest): LoginResponse?

    fun signup(signupRequest: SignupRequest): SignupResponse?
}

@Service
open class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userValidation: UserValidation,
    private val authenticationManager: AuthenticationManager,
    private val firebaseService: FirebaseService,
) : UserService {

    override fun login(loginRequest: LoginRequest): LoginResponse? {
        userValidation.validate(loginRequest)
        val uid = getUid(loginRequest.subject!!)
        return LoginResponse(authenticationManager.generateToken(uid!!))
    }

    override fun login(oAuthLoginRequest: OAuthLoginRequest): LoginResponse? {
        userValidation.validate(oAuthLoginRequest)
        //TODO
        return null
    }

    override fun signup(signupRequest: SignupRequest): SignupResponse? {
        userValidation.validate(signupRequest)
        val firebaseToken = firebaseService.verifyToken(signupRequest.token)
        val firebaseUser = firebaseService.getUser(firebaseToken)
        val userEntity = UserEntity(
            username = firebaseUser.uid,
            firstName = signupRequest.firstName!!,
            lastName = signupRequest.lastName!!,
            password = passwordEncoder.encode(signupRequest.password!!),
            email = firebaseUser.email,
            phoneNumber = firebaseUser.phoneNumber
        )
        userRepository.save(userEntity)
        return SignupResponse(authenticationManager.generateToken(firebaseUser.uid))
    }

    private fun getUid(subject: String): String? {
        return if (isPhoneNumber(subject)) {
            userRepository.findByPhoneNumber(subject).uid.toString()
        } else if (isEmail(subject)) {
            userRepository.findByEmail(subject).uid.toString()
        } else if (isUsername(subject)) {
            userRepository.findByUsername(subject).uid.toString()
        } else {
            return null
        }
    }
}