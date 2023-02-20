package io.github.chayanforyou.findmeds.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.StreamUtils
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import javax.servlet.http.HttpServletResponse

@Service
class FileService {

    @Autowired
    lateinit var env: Environment

    fun downloadImage(fileName: String): ResponseEntity<Resource> {
        val uploadDirectory = env.getProperty("image.dir") ?: throw Exception("Image directory is not defined")
        val path = Paths.get(uploadDirectory, fileName)
        val resource = FileSystemResource(path)
        val contentType = Files.probeContentType(path)

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .body(resource)
    }

    fun downloadImage(fileName: String, response: HttpServletResponse) {
        val uploadDirectory = env.getProperty("image.dir") ?: throw Exception("Image directory is not defined")
        val path = Paths.get(uploadDirectory, fileName)
        val inputStream = FileInputStream(path.toFile())
        val contentType = Files.probeContentType(path)

        response.contentType = contentType
        StreamUtils.copy(inputStream, response.outputStream)
    }
}