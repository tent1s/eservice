package ru.omsu.eservice.ui.screen.educationcard.model

data class EducationOrderUi(
    val id: Int?,
    val mainText: String?,
    var isShowingMore: Boolean = false,
    var moreInformation: String = ""
)