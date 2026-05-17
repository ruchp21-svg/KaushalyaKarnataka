package com.example.kaushalyakarnataka.data

data class Worker(
    val id: String = "",
    val name: String = "",
    val role: String = "",
    val location: String = "",
    val rating: Double = 0.0,
    val reviewsCount: Int = 0,
    val jobsDone: Int = 0,
    val initials: String = "",
    val phone: String = "",
    val isVerified: Boolean = false,
    val services: List<Service> = emptyList(),
    val reviews: List<Review> = emptyList(),
    val gallery: List<String> = emptyList()
)

data class Service(
    val id: String = "",
    val serviceName: String = "",
    val price: String = ""
)

data class Review(
    val id: String = "",
    val authorName: String = "",
    val date: String = "",
    val starRating: Int = 0,
    val commentText: String = ""
)
