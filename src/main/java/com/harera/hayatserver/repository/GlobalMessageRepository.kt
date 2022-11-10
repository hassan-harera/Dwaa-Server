package com.harera.hayatserver.repository;

import com.harera.hayatserver.model.GlobalMessage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GlobalMessageRepository : JpaRepository<GlobalMessage, Long> {

    fun findByLanguageAndCode(language: String?, code: String?): Optional<GlobalMessage>
}
