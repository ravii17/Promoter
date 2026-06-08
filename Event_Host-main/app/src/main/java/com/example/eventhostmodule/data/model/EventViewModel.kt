package com.example.eventhostmodule.data.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


class EventViewModel : ViewModel() {

    private val _eventData = MutableStateFlow(EventData())
    val eventData: StateFlow<EventData> = _eventData

    fun updateEventName(name: String) {
        _eventData.value = _eventData.value.copy(eventName = name)
    }
    fun updateStep2Data(
        date: String,
        location: String,
        venueType: String,
        guests: Int
    ) {
        _eventData.value = _eventData.value.copy(
            date = date,
            location = location,
            venueType = venueType,
            guests = guests
        )
    }
    fun updateOccasion(occasion: String) {
        _eventData.value = _eventData.value.copy(occasion = occasion)
    }

    fun updateServices(services: List<String>) {
        _eventData.value = _eventData.value.copy(services = services)
    }

    fun updateBudget(budget: String) {
        _eventData.value = _eventData.value.copy(budget = budget)
    }

    fun updatePreferences(theme: String, notes: String) {
        _eventData.value = _eventData.value.copy(
            theme = theme,
            notes = notes
        )
    }
    var isEventCreated by mutableStateOf(false)

    fun markEventCreated() {
        isEventCreated = true
    }
}