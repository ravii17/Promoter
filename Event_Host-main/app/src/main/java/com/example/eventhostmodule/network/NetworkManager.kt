package com.example.eventhostmodule.network

import android.content.Context
import com.example.eventhostmodule.data.model.PromotionDto
import com.example.eventhostmodule.network.NetworkUtils
import kotlinx.coroutines.delay

/**
 * Simple NetworkManager with mock API calls. Handles connectivity checks and returns dummy data.
 */
object NetworkManager {
    suspend fun fetchPromotionsMock(context: Context): List<PromotionDto> {
        // Check connectivity, but still return mock data even if offline (for MVP)
        val online = NetworkUtils.isInternetAvailable(context)
        // Simulate network delay
        delay(500)
        return listOf(
            PromotionDto(1, "50% Off Concert", "Get half price on tickets.", null),
            PromotionDto(2, "Buy 1 Get 1", "Limited time staffing offer.", null),
            PromotionDto(3, "Early Bird", "Register early and save.", null)
        )
    }
}
