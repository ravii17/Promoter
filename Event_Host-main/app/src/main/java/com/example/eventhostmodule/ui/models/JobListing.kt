package com.example.eventhostmodule.ui.models

enum class JobType {
    ONE_OFF_EVENT,
    URGENT_FILL,
    MULTI_DAY
}

data class JobListing(
    val id: String,
    val title: String,
    val type: JobType,
    val pay: String,
    val payType: String, // "hr" or "flat"
    val location: String,
    val date: String,
    val time: String,
    val description: String
)
