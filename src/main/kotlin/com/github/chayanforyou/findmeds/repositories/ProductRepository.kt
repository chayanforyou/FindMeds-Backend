package com.github.chayanforyou.findmeds.repositories

import com.github.chayanforyou.findmeds.models.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {}