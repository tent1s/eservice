package ru.omsu.eservice.ui.screen.services.model

import ru.omsu.eservice.R

enum class Services(
    val imageRes: Int,
    val title: Int,
    val description: Int,
    val moreDescription: Int,
) {
    EDUCATION_CARD(
        R.drawable.ic_educational_card,
        R.string.education_card_title,
        R.string.education_card_msg,
        R.string.education_card_msg_more
    )
}