package com.github.chayanforyou.findmeds.controllers

import com.github.chayanforyou.findmeds.models.Product
import com.github.chayanforyou.findmeds.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/api/product")
class ProductController(private var service: ProductService) {

    @GetMapping("/list")
    fun getProducts() = service.get()

    @PostMapping("/add")
    fun addProduct(@RequestBody product: Product) = service.add(product)

    @GetMapping("{id}")
    fun getProduct(@PathVariable id: Long) = service.get(id)

    @PutMapping("/edit")
    fun editProduct(@RequestBody product: Product) = service.edit(product)

    @DeleteMapping("/delete/{id}")
    fun deleteProduct(@PathVariable id: Long) = service.delete(id)
}