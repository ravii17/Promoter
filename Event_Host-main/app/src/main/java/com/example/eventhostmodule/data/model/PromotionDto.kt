package com.example.eventhostmodule.data.model

/**
 * Data Transfer Object for Promotion, used for network (Retrofit) responses.
 * If API fields differ from domain, map accordingly in repository.
 */
data class PromotionDto(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String?
)
