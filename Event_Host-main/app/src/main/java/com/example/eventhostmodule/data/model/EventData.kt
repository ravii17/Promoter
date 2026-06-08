package com.example.eventhostmodule.data.model

data class EventData(
    var eventName: String = "",
    var occasion: String = "",
    var services: List<String> = emptyList(),
    var budget: String = "",
    var theme: String = "",
    var notes: String = "",
    var date: String = "",
    var location: String = "",
    var venueType: String = "",
    var guests: Int = 0
)