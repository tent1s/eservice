package ru.omsu.eservice.data.remote.login.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CsrfTokenNetwork(
    @Json(name = "csrf")
    val csrf: String
)