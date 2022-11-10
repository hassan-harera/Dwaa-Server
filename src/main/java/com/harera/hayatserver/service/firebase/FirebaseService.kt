package com.harera.hayatserver.service.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.google.firebase.auth.UserRecord
import com.harera.hayatserver.exception.LoginException
import com.harera.hayatserver.util.ErrorCode
import org.springframework.stereotype.Service

interface FirebaseService {

    fun verifyToken(token: String?): FirebaseToken
    fun getUser(firebaseToken: FirebaseToken?): UserRecord
}

@Service
class FirebaseServiceImpl(
    private val firebaseAuth: FirebaseAuth
) : FirebaseService {


    override fun verifyToken(token: String?): FirebaseToken {
        return FirebaseAuth.getInstance().verifyIdToken(token, true)
            ?: throw LoginException(ErrorCode.INVALID_TOKEN, "Invalid token")
    }

    override fun getUser(firebaseToken: FirebaseToken?): UserRecord {
        return FirebaseAuth.getInstance().getUser(firebaseToken?.uid)
            ?: throw LoginException(ErrorCode.INVALID_TOKEN, "Invalid token")
    }

//    override fun sentVerificationCode(email: String?): UserRecord {
//        val createRequest: UserRecord.CreateRequest? = UserRecord
//            .CreateRequest()
//            .setPassword("secretPassword")
//            .setEmail(email)
//
//        FirebaseAuth.getInstance().generateEmailVerificationLink(email, null)
//        FirebaseAuth.getInstance().tenantManager.getgenerateEmailVerificationLink(email, null)
//        return FirebaseAuth.getInstance().createUser(createRequest)
//    }
}
