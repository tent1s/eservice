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
    val scheduleUrl: String?,
    @Json(name = "summary")
    val summary: SumInfoNetwork?,
    @Json(name = "documents")
    val documents: List<DocumentsNetwork>?,
    @Json(name = "sessions")
    val sessions: List<SessionsNetwork>?,
    @Json(name = "addresses")
    val addresses: List<AddressesNetwork>?,
    @Json(name = "decrees")
    val decrees: List<DecreesNetwork>?,
    @Json(name = "semInfo")
    val semInfo: List<SemInfoNetwork>?
)

@JsonClass(generateAdapter = true)
class SumInfoNetwork(
    @Json(name = "planId")
    val planId: String?,
    @Json(name = "group")
    val group: String?,
    @Json(name = "ins")
    val ins: String?,
    @Json(name = "fullName")
    val fullName: String?,
    @Json(name = "birthDay")
    val birthDay: String?,
    @Json(name = "faculty")
    val faculty: String?,
    @Json(name = "specification")
    val specification: String?,
    @Json(name = "studyForm")
    val studyForm: String?,
    @Json(name = "financing")
    val financing: String?,
    @Json(name = "studyLength")
    val studyLength: String?,
    @Json(name = "course")
    val course: String?,
    @Json(name = "dupId")
    val dupId: String?,
    @Json(name = "dupIdExpress")
    val dupIdExpress: String?,
    @Json(name = "week")
    val week: String?
)

@JsonClass(generateAdapter = true)
class DocumentsNetwork(
    @Json(name = "type")
    val type: String?,
    @Json(name = "series")
    val series: String?,
    @Json(name = "number")
    val number: String?,
    @Json(name = "issuedBy")
    val issuedBy: String?
)

@JsonClass(generateAdapter = true)
class SessionsNetwork(
    @Json(name = "number")
    val number: String?,
    @Json(name = "entries")
    val entries: List<EntriesNetwork>?
)

@JsonClass(generateAdapter = true)
class EntriesNetwork(
    @Json(name = "subject")
    val subject: String?,
    @Json(name = "result")
    val result: String?
)

@JsonClass(generateAdapter = true)
class AddressesNetwork(
    @Json(name = "fullPath")
    val fullPath: String?,
    @Json(name = "dateBegin")
    val dateBegin: String?,
    @Json(name = "type")
    val type: String?
)

@JsonClass(generateAdapter = true)
class DecreesNetwork(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "date")
    val date: String?,
    @Json(name = "number")
    val number: String?,
    @Json(name = "about")
    val about: String?
)

@JsonClass(generateAdapter = true)
class SemInfoNetwork(
    @Json(name = "number")
    val number: Int?,
    @Json(name = "entries")
    val entries: List<EntriesSeminarNetwork>?,
)

@JsonClass(generateAdapter = true)
class EntriesSeminarNetwork(
    @Json(name = "discipline")
    val discipline: String?,
    @Json(name = "discId")
    val discId: Int?,
    @Json(name = "planId")
    val planId: Int?,
    @Json(name = "length")
    val length: Int?,
    @Json(name = "rpd")
    val rpd: String?,
    @Json(name = "apx")
    val apx: String?,
    @Json(name = "controlForm")
    val controlForm: String?
)