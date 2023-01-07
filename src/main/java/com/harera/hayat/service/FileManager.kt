package com.harera.hayat.service

import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.*


interface FileManager {

    fun saveFile(multipartFile: MultipartFile, path: String): String
    fun convert(multipartFile: MultipartFile): File?
}

@Slf4j
@Service
class FileManagerImpl : FileManager {

    override fun saveFile(multipartFile: MultipartFile, path: String): String {
        val initialStream = multipartFile.inputStream
        val buffer = ByteArray(initialStream.available())
        initialStream.read(buffer)

        val targetFile = File(path).also { it.parentFile.mkdirs() }

        FileOutputStream(targetFile).write(buffer)
        return targetFile.absolutePath
    }

    override fun convert(multipartFile: MultipartFile): File? {
        try {
            val initialStream = multipartFile.inputStream
            val buffer = ByteArray(initialStream.available())
            initialStream.read(buffer)

            val targetFile = File.createTempFile(UUID.randomUUID().toString(), "JPG")
            FileOutputStream(targetFile).write(buffer)
            return targetFile
        } catch (ex: Exception) {
            return null;
        }
    }
}