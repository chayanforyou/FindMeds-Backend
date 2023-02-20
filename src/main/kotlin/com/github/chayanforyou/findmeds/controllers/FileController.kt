package com.github.chayanforyou.findmeds.controllers

import com.github.chayanforyou.findmeds.service.FileService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@CrossOrigin("*")
@RequestMapping("/uploads")
class FileController(private var service: FileService) {

    @GetMapping("/images/{fileName}", produces = [MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE])
    fun downloadImage(@PathVariable fileName: String): ResponseEntity<*> = service.downloadImage(fileName)

    /*@GetMapping("/images/{fileName}", produces = [MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE])
    fun downloadImage(@PathVariable fileName: String, response: HttpServletResponse) = service.downloadImage(fileName, response)*/
}