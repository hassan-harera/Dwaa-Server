package com.harera.hayat.service.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.google.firebase.auth.UserRecord
import com.harera.hayat.common.exception.LoginException
import com.harera.hayat.model.user.auth.SignupRequest
import com.harera.hayat.util.ErrorCode
import org.springframework.stereotype.Service

interface FirebaseService {

    fun verifyToken(token: String?): FirebaseToken
    fun getUser(firebaseToken: FirebaseToken?): UserRecord
    fun createUser(signupRequest: SignupRequest): UserRecord?
}

@Service
class FirebaseServiceImpl(
    private val firebaseAuth: FirebaseAuth
) : FirebaseService {


    override fun verifyToken(token: String?): FirebaseToken {
        return firebaseAuth.verifyIdToken(token, true)
            ?: throw LoginException(
                ErrorCode.INVALID_FIREBASE_TOKEN,
                "Invalid token"
            )
    }

    override fun getUser(firebaseToken: FirebaseToken?): UserRecord {
        return FirebaseAuth.getInstance().getUser(firebaseToken?.uid)
            ?: throw LoginException(
                ErrorCode.INVALID_FIREBASE_TOKEN,
                "Invalid token"
            )
    }

    override fun createUser(signupRequest: SignupRequest): UserRecord? {
        val userRecord = UserRecord.CreateRequest()
            .setPhoneNumber(signupRequest.mobile)
            .setEmail(signupRequest.email)
            .setEmailVerified(false)
            .setPassword(signupRequest.password)
            .setDisplayName(signupRequest.firstName + " " + signupRequest.lastName)
            .setPassword(signupRequest.password)
            .setDisabled(false)

        return firebaseAuth.createUser(userRecord)
    }
}
