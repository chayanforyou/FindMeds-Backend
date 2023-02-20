package com.github.chayanforyou.findmeds.models

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "product")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String? = null,
    var price: Double = 0.0,
    var isAvailable: Boolean = true,
    image: String? = null,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    var image: String? = image
        get() = if (field.isNullOrBlank()) null else "http://localhost:8080/uploads/images/$field"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Product) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
