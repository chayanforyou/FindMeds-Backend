package com.github.chayanforyou.findmeds.models

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "product")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    var price: Double,
    var isAvailable: Boolean,
    var imageUrl: String?,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updateddAt: LocalDateTime = LocalDateTime.now()
)
