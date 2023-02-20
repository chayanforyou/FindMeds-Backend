package io.github.chayanforyou.findmeds.service

import io.github.chayanforyou.findmeds.models.Product
import io.github.chayanforyou.findmeds.payload.BaseResponse
import io.github.chayanforyou.findmeds.repositories.ProductRepository
import io.github.chayanforyou.findmeds.utils.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Paths
import java.time.LocalDateTime


@Service
class ProductService(private var repository: ProductRepository) {

    @Autowired
    lateinit var env: Environment

    fun get(): List<Product> = repository.findAll()

    fun add(product: Product): ResponseEntity<*> {
        repository.save(product)
        return ResponseEntity.ok(BaseResponse(true, "Successfully added"))
    }

    fun add(name: String, price: Double, isAvailable: Boolean, image: MultipartFile): ResponseEntity<*> {
        val uploadDirectory = env.getProperty("image.dir") ?: throw Exception("Image upload directory is not defined")

        if (!FileUtils.isValidImageType(image)) {
            return ResponseEntity.ok(BaseResponse(false, "The product image is not valid"))
        }

        var imageName: String? = null
        val fileName = image.originalFilename
        if (!fileName.isNullOrBlank()) {
            val timeMillis = System.currentTimeMillis().toString()
            imageName = timeMillis.plus(fileName.substring(fileName.lastIndexOf(".")))
            val filePath = Paths.get(uploadDirectory, imageName)
            FileUtils.saveFile(filePath, image)
        }

        val product = Product()
        product.name = name
        product.image = imageName
        product.price = price
        product.isAvailable = isAvailable
        repository.save(product)

        return ResponseEntity.ok(BaseResponse(true, "Successfully added"))
    }

    fun get(id: Long): ResponseEntity<*> {
        return when (val product = repository.findByIdOrNull(id)) {
            null -> ResponseEntity.ok(BaseResponse(false, "No product found"))
            else -> ResponseEntity.ok(BaseResponse(true, product))
        }
    }

    fun edit(product: Product): ResponseEntity<*> {
        val productOptional = repository.findById(product.id!!)
        return when {
            productOptional.isPresent -> {
                val mProduct = productOptional.get()
                mProduct.name = product.name
                mProduct.price = product.price
                mProduct.isAvailable = product.isAvailable
                mProduct.updatedAt = LocalDateTime.now()
                repository.save(mProduct)
                //ResponseEntity.status(HttpStatus.CREATED).body(repository.save(mProduct))
                ResponseEntity.ok(BaseResponse(true, "Successfully updated"))
            }

            else -> ResponseEntity.ok(BaseResponse(false, "Can't update product"))
        }
    }

    fun delete(id: Long): ResponseEntity<*> {
        val productOptional = repository.findById(id)
        return when {
            productOptional.isPresent -> {
                repository.delete(productOptional.get())
                ResponseEntity.ok(BaseResponse(true, "Successfully deleted"))
            }

            else -> ResponseEntity.ok(BaseResponse(false, "Can't delete product"))
        }
    }
}