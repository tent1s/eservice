package ru.omsu.eservice.data.remote.login.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class EducationGroupNetwork(
    @Json(name = "planUrl")
    val planUrl: String?,
    @Json(name = "currentControlUrl")
    val currentControlUrl: String?,
    @Json(name = "scheduleUrl")
    val scheduleUrl: String?
)