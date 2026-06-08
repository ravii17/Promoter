package com.example.eventhostmodule.data.model

/**
 * Domain model for Promotion, used throughout the app.
 * Add role-based logic here (e.g., User/Admin fields) if needed in the future.
 */
data class Promotion(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String?
    // Add more fields as needed
)
