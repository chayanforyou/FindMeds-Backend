package io.github.chayanforyou.findmeds.utils

import org.springframework.http.MediaType
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

object FileUtils {

    fun saveFile(fileName: String, multipartFile: MultipartFile) {
        val uploadPath = Paths.get("uploads")
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath)
        }
        try {
            multipartFile.inputStream.use { inputStream ->
                val filePath = uploadPath.resolve(fileName)
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
            }
        } catch (e: IOException) {
            throw IOException("Could not save image file: $fileName", e)
        }
    }

    fun saveFile(filePath: Path, multipartFile: MultipartFile) {
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath)
        }

        try {
            multipartFile.inputStream.use { inputStream ->
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
            }
        } catch (e: IOException) {
            throw IOException("Could not save image file: ${filePath.fileName}", e)
        }
    }

    fun getExtension(filename: String): Optional<String>? {
        return Optional.ofNullable(filename)
            .filter { f -> f.contains(".") }
            .map { f -> f.substring(filename.lastIndexOf(".") + 1) }
    }

    fun isValidImageType(multipartFile: MultipartFile): Boolean {
        val allowedMimeTypes = listOf(MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE)

        val contentType: String? = multipartFile.contentType
        for (i in allowedMimeTypes.indices) {
            if (contentType.equals(allowedMimeTypes[i], ignoreCase = true)) {
                return true
            }
        }
        return false
    }
}