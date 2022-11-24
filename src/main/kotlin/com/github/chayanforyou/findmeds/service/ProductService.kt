package com.github.chayanforyou.findmeds.service

import com.github.chayanforyou.findmeds.models.Product
import com.github.chayanforyou.findmeds.payload.BaseResponse
import com.github.chayanforyou.findmeds.repositories.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProductService(private var repository: ProductRepository) {

    fun get(): List<Product> = repository.findAll()

    fun add(product: Product): ResponseEntity<*> {
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
        val productOptional = repository.findById(product.id)
        return when {
            productOptional.isPresent -> {
                val mProduct = productOptional.get()
                mProduct.name = product.name
                mProduct.price = product.price
                mProduct.isAvailable = product.isAvailable
                mProduct.updateddAt = LocalDateTime.now()
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