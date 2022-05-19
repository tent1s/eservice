package ru.omsu.eservice.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EducationGroupUi(
    val planUrl: String?,
    val currentControlUrl: String?,
    val scheduleUrl: String?,
    val summary: SumInfo?,
    val documents: List<Documents>,
    val sessions: List<Sessions>,
    val addresses: List<Addresses>,
    val decrees: List<Decrees>,
    val semInfo: List<SemInfo>,
) : Parcelable

@Parcelize
data class SumInfo(
    val planId: String?,
    val group: String?,
    val ins: String?,
    val fullName: String?,
    val birthDay: String?,
    val faculty: String?,
    val specification: String?,
    val studyForm: String?,
    val financing: String?,
    val studyLength: String?,
    val course: String?,
    val dupId: String?,
    val dupIdExpress: String?,
    val week: String?
) : Parcelable

@Parcelize
data class Documents(
    val type: String?,
    val series: String?,
    val number: String?,
    val issuedBy: String?
) : Parcelable

@Parcelize
data class Sessions(
    val number: String?,
    val entries: List<Entries>
) : Parcelable

@Parcelize
data class Entries(
    val subject: String?,
    val result: String?
) : Parcelable

@Parcelize
data class Addresses(
    val fullPath: String?,
    val dateBegin: String?,
    val type: String?
) : Parcelable

@Parcelize
data class Decrees(
    val id: Int?,
    val date: String?,
    val number: String?,
    val about: String?
) : Parcelable

@Parcelize
data class SemInfo(
    val number: Int?,
    val entries: List<EntriesSeminar>
) : Parcelable

@Parcelize
data class EntriesSeminar(
    val discipline: String?,
    val discId: Int?,
    val planId: Int?,
    val length: Int?,
    val rpd: String?,
    val apx: String?,
    val controlForm: String?
) : Parcelable